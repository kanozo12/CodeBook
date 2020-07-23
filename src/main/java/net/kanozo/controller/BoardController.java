package net.kanozo.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import net.kanozo.domain.BoardVO;
import net.kanozo.domain.ComVO;
import net.kanozo.domain.Criteria;
import net.kanozo.domain.ExpData;
import net.kanozo.domain.UploadResponse;
import net.kanozo.domain.UserVO;
import net.kanozo.service.BoardService;
import net.kanozo.service.CommentService;
import net.kanozo.service.FileUploadService;
import net.kanozo.service.UserService;
import net.kanozo.util.FileUtil;
import net.kanozo.util.MediaUtil;
import net.kanozo.validator.BoardValidator;

@Controller
@RequestMapping(value = { "/board/", "/free/" })
public class BoardController {
	@Autowired
	private ServletContext context;

	@Autowired
	private BoardService service;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService cService;

	@Autowired
	FileUploadService fileUploadService;

	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String viewWritePage(Model model) {
		model.addAttribute("boardVO", new BoardVO());
		return "board/write.page";
	}

	@RequestMapping(value = "write{id}", method = RequestMethod.GET)
	public String viewWritePage2(Model model, @PathVariable String id) {
		model.addAttribute("boardVO", new BoardVO());
		return "board/write" + id + ".page";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public UploadResponse handleImageUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) {
		String uploadPath = context.getRealPath("/app/images");
		UploadResponse response = new UploadResponse();

		try {
			String originalName = file.getOriginalFilename();
			String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
			if (MediaUtil.getMediaType(ext) == null) {
				throw new Exception("올바르지 않은 파일 형식");
			}
			String upFile = FileUtil.uploadFile(uploadPath, originalName, file.getBytes());

			// 썸네일 경로 셋팅
			response.setThumbImage("/app/images/" + upFile);
			// 실제파일 경로 셋팅
			response.setUploadImage("/app/images/" + upFile.substring(2, upFile.length()));
			response.setMsg("성공적으로 업로드 됨");
			response.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMsg(e.getMessage());
			response.setResult(false);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public String writeProcess(BoardVO board, HttpSession session, Errors errors, RedirectAttributes rttr,
			HttpServletRequest req, MultipartHttpServletRequest mtf) {

		// 올바른 값인지 벨리데이팅
		new BoardValidator().validate(board, errors);
		if (errors.hasErrors()) {
			return "board/write"; // 에러가 존재하면 글쓰기 페이지로 보냄.
		}
		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러써도 된다.
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println(board + "!!!!!!!!!!!!!!!");
		if (board.getId() != null) {
			BoardVO data = service.viewArticle(board.getId());
			if (data == null || !user.getUserid().equals(data.getWriter())) {
				rttr.addFlashAttribute("msg", "권한이 없습니다.");
				return "redirect:/board/list.page";
			}
		}

		// 로그인한 사용자의 아이디를 글쓴이로 등록하고
		board.setWriter(user.getUserid());

		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(board.getContent());
		board.setContent(clean);

		// 실제 DB에 글을 기록함.
		if (board.getId() != null) {
			// 글 수정
			service.updateArticle(board);
		} else {
			// 글 작성
			Iterator<String> itr = mtf.getFileNames();
			String filePath = "C:/upload"; // 설정파일로 뺀다.

			while (itr.hasNext()) { // 받은 파일들을 모두 돌린다.
				MultipartFile mpf = mtf.getFile(itr.next());

				String originalFilename = mpf.getOriginalFilename(); // 파일명
				String fileFullPath = filePath + "/" + System.currentTimeMillis() + originalFilename; // 파일 전체 경로

				String saveFile = originalFilename;
				if (originalFilename.isEmpty()) {
					saveFile = "";
				} else {
					saveFile = System.currentTimeMillis() + originalFilename;
				}

				try {
					// 파일 저장
					mpf.transferTo(new File(fileFullPath)); // 파일저장 실제로는 service에서 처리
					board.setFileName(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			service.writeArticle(board);
			user = userService.appExp(user.getUserid(), ExpData.MEDIUM); // 글을 한번 쓸 때마다 5의 exp를 지급
			session.setAttribute("user", user);
		}
		return "board/list.page";

	}

	@RequestMapping(value = "write2", method = RequestMethod.POST)
	public String writeProcess2(BoardVO board, HttpSession session, Errors errors, RedirectAttributes rttr,
			HttpServletRequest req, Model model) {

		// 올바른 값인지 벨리데이팅
		new BoardValidator().validate(board, errors);
		if (errors.hasErrors()) {
			return "board/write"; // 에러가 존재하면 글쓰기 페이지로 보냄.
		}
		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러써도 된다.
		UserVO user = (UserVO) session.getAttribute("user");
		System.out.println(board + "!!!!!!!!!!!!!!!");
		if (board.getId() != null) {
			BoardVO data = service.viewArticle(board.getId());
			if (data == null || !user.getUserid().equals(data.getWriter())) {
				rttr.addFlashAttribute("msg", "권한이 없습니다.");
				return "redirect:/board/list.page";
			}
		}

		// 로그인한 사용자의 아이디를 글쓴이로 등록하고
		board.setWriter(user.getUserid());

		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(board.getContent());
		board.setContent(clean);

		// 실제 DB에 글을 기록함.
		if (board.getId() != null) {
			// 글 수정
			board.setFileName(""); //아직 파일 업로드 구현 x
			board.setB_type("bd2");
			service.updateArticle2(board);
		} else {
			// 글 작성
			board.setFileName(""); //아직 파일 업로드 구현 x
			board.setB_type("bd2");
			service.writeArticle2(board);
			user = userService.appExp(user.getUserid(), ExpData.MEDIUM); // 글을 한번 쓸 때마다 5의 exp를 지급
			session.setAttribute("user", user);
		}
		return "redirect:/board/list2";
	}

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String viewArticle(@PathVariable Integer id, Model model, Criteria cri, HttpSession session,
			Criteria criteria) {
		BoardVO board = service.viewArticle(id);
		UserVO user = (UserVO) session.getAttribute("user");
		List<ComVO> list = cService.list(id);
		model.addAttribute("board", board);
		model.addAttribute("user", user);
		model.addAttribute("list", list);

		return "board/view.page";
	}

	@RequestMapping(value = "view2/{id}", method = RequestMethod.GET)
	public String viewArticle2(@PathVariable Integer id, Model model, Criteria cri, HttpSession session,
			Criteria criteria) {
		BoardVO board = service.viewArticle2(id);
		UserVO user = (UserVO) session.getAttribute("user");
		List<ComVO> list = cService.list(id);
		model.addAttribute("board", board);
		model.addAttribute("user", user);
		model.addAttribute("list", list);

		return "board/view.page";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String viewList(Criteria criteria, Model model, HttpServletRequest req) {
		List<BoardVO> list = service.getArticleList(criteria);
		model.addAttribute("list", list);

		Integer cnt = service.countArticle(criteria);
		criteria.calculate(cnt);

		return "board/list.page";

	}

	@RequestMapping(value = "{boardPage}", method = RequestMethod.GET)
	public String viewList2(Criteria criteria, Model model, HttpServletRequest req, @PathVariable String boardPage) {
		if (boardPage.equals("list2")) {
			criteria.setB_type("bd2");
			List<BoardVO> list = service.getArticleList2(criteria);
			model.addAttribute("list", list);
			Integer cnt = service.countArticle2(criteria);
			criteria.calculate(cnt);

			return "board/list2.page";

		} else if (boardPage.equals("list3")) {
			criteria.setB_type("bd3");
			List<BoardVO> list = service.getArticleList2(criteria);
			model.addAttribute("list", list);
			Integer cnt = service.countArticle2(criteria);
			criteria.calculate(cnt);

			return "board/list3.page";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/replyInsert", method = RequestMethod.POST)
	public String insert(ComVO comVO, HttpSession session, RedirectAttributes rttr, Criteria criteria) {
		Integer bno = comVO.getBno();

		UserVO user = (UserVO) session.getAttribute("user");

		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러써도 된다.
		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(comVO.getComContent());
		comVO.setComContent(clean);

		cService.insert(comVO);

		user = userService.appExp(user.getUserid(), ExpData.SMALL); // 글을 한번 쓸 때마다 2의 exp를 지급
		session.setAttribute("user", user);

		return "redirect:/board/view/" + bno + criteria.getQuery(criteria.getPage()) + ".page";
	}

	@RequestMapping(value = "write/{id}", method = RequestMethod.GET)
	public String viewModPage(Model model, @PathVariable("id") Integer id, HttpSession session,
			RedirectAttributes rttr) {

		BoardVO data = service.viewArticle2(id);
		UserVO user = (UserVO) session.getAttribute("user");

		if (data == null || !data.getWriter().equals(user.getUserid())) {
			rttr.addFlashAttribute("msg", "수정할 권한이 없습니다.");
			return "redirect:/board/list.page";
		}

		model.addAttribute("boardVO", data);
		return "board/write.page";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteArticle(@PathVariable("id") Integer id, HttpSession session, RedirectAttributes rttr) {
		UserVO user = (UserVO) session.getAttribute("user");
		BoardVO data = service.viewArticle(id);

		if (!user.getUserid().equals(data.getWriter())) {
			rttr.addFlashAttribute("msg", "삭제 권한이 없습니다.");
			return "redirect:/board/view/" + data.getId() + ".page";
		}
		service.deleteArticle(id);
		rttr.addFlashAttribute("msg", "성공적으로 삭제되었습니다.");
		return "redirect:/board/list.page";
	}
}
