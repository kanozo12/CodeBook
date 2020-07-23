package net.kanozo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import net.kanozo.domain.ComVO;
import net.kanozo.domain.Criteria;
import net.kanozo.domain.ExpData;
import net.kanozo.domain.NoticeVO;
import net.kanozo.domain.UserVO;
import net.kanozo.service.CommentServiceImpl;
import net.kanozo.service.FileUploadService;
import net.kanozo.service.UserService;
import net.kanozo.service.WriteNoticeService;
import net.kanozo.validator.BoardValidator;

@Controller
@RequestMapping("/notice/")

public class WriteNoticeController {

	@Autowired
	private WriteNoticeService noticeService;

	@Autowired
	private CommentServiceImpl cService;

	@Autowired
	private UserService userService;

	@Autowired
	FileUploadService fileUploadService;

	private static final String UPLOAD_PATH = "C:\\upload";

	// *************** 공지사랑 게시판 만들기 ***********************
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String viewWritePage(Model model) {
		model.addAttribute("noticeVO", new NoticeVO());
		return "board/noticeWrite.page";
	}

	@RequestMapping(value = "write/{id}", method = RequestMethod.GET)
	public String viewModPage(Model model, @PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes rttr) {

		NoticeVO data = noticeService.viewArticle(id);
		UserVO user = (UserVO) session.getAttribute("user");

		if (data == null || !data.getWriter().equals(user.getUserid())) {
			rttr.addFlashAttribute("msg", "수정할 권한이 없습니다.");
			return "redirect:/board/list";
		}

		model.addAttribute("noticeVO", data);
		return "board/noticeWrite.page";
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String noticeWriteProcess(NoticeVO noticeVO, HttpSession session, Errors errors, RedirectAttributes rttr,
			HttpServletRequest req, Model model, @RequestParam("file") MultipartFile multi) {

		// 올바른 값인지 벨리데이팅
		new BoardValidator().validate(noticeVO, errors);
		if (errors.hasErrors()) {
			return "board/writeNotice.page"; // 에러가 존재하면 글쓰기 페이지로 보냄.
		}
		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러써도 된다.
		UserVO user = (UserVO) session.getAttribute("user");

		if (noticeVO.getId() != null) {
			NoticeVO data = noticeService.viewArticle(noticeVO.getId());

			if (data == null || !user.getUserid().equals(data.getWriter())) {
				rttr.addFlashAttribute("msg", "권한이 없습니다.");
				return "redirect:/board/noticeList";
			}
		}

		// 로그인한 사용자의 아이디를 글쓴이로 등록하고
		noticeVO.setWriter(user.getUserid());

		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(noticeVO.getContent());
		noticeVO.setContent(clean);

		// 실제 DB에 글을 기록함.
		if (noticeVO.getId() != null) {
			// 글 수정
			if (multi.isEmpty()) {
				noticeVO.setFileName("");
			} else {
				noticeVO.setFileName(saveFile(multi));
			}

			noticeService.updateArticle(noticeVO);

		} else {
			// 글 작성
			try {
				if (multi.isEmpty()) {
					noticeVO.setFileName("");
				} else {
					noticeVO.setFileName(saveFile(multi));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			noticeService.writeArticle(noticeVO);
			user = userService.appExp(user.getUserid(), ExpData.MEDIUM); // 글을 한번 쓸 때마다 5의 exp를 지급
			session.setAttribute("user", user);
		}

		return "redirect:/board/noticeList";
	}

	@RequestMapping(value = "noticeView/{id}", method = RequestMethod.GET)
	public String viewArticle(@PathVariable Integer id, Model model, Criteria cri, HttpSession session,
			Criteria criteria) {
		NoticeVO noticeVO = noticeService.viewArticle(id);
		UserVO user = (UserVO) session.getAttribute("user");
		List<ComVO> list = cService.list(id);
		model.addAttribute("board", noticeVO);
		model.addAttribute("user", user);
		model.addAttribute("list", list);

		return "board/noticeView.page";
	}

	@RequestMapping(value = "noticeList", method = RequestMethod.GET)
	public String viewList(Criteria criteria, Model model, HttpServletRequest req) {
		List<NoticeVO> list = noticeService.getArticleList(criteria);
		model.addAttribute("list", list);

		Integer cnt = noticeService.countArticle(criteria);
		criteria.calculate(cnt);

		return "board/noticeList.page";

	}

	@RequestMapping(value = "noticeDelete/{id}", method = RequestMethod.GET)
	public String deleteArticle(@PathVariable("id") Integer id, HttpSession session, RedirectAttributes rttr) {
		UserVO user = (UserVO) session.getAttribute("user");
		NoticeVO data = noticeService.viewArticle(id);

		if (!user.getUserid().equals(data.getWriter())) {
			rttr.addFlashAttribute("msg", "삭제 권한이 없습니다.");
			return "redirect:/board/view/" + data.getId() + ".page";
		}
		noticeService.deleteArticle(id);
		rttr.addFlashAttribute("msg", "성공적으로 삭제되었습니다.");
		return "redirect:/board/noticeList.page";
	}

	private String saveFile(MultipartFile file) {
		// 파일 이름 변경
		UUID uuid = UUID.randomUUID();
		String saveName = uuid + "_" + file.getOriginalFilename();

		// 저장할 File 객체를 생성(껍데기 파일)
		File saveFile = new File(UPLOAD_PATH, saveName); // 저장할 폴더 이름, 저장할 파일 이름

		try {
			file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return saveName;
	}
	// *************** 공지사랑 게시판 만들기 ***********************
}
