package net.kanozo.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import net.gondr.util.FileUtil;
import net.gondr.util.MediaUtil;
import net.gondr.validator.BoardValidator;
import net.kanozo.domain.BoardVO;
import net.kanozo.domain.UploadResponse;
import net.kanozo.domain.UserVO;
import net.kanozo.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Autowired
	private ServletContext context;

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String viewWritePage(Model model) {
		model.addAttribute("boardVO", new BoardVO());
		return "board/write";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public UploadResponse handleImageUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		String uploadPath = context.getRealPath("/app/images/");
		UploadResponse res = new UploadResponse();

		try {
			String originName = file.getOriginalFilename();
			String ext = originName.substring(originName.lastIndexOf(".") + 1);
			if (MediaUtil.getMediaType(ext) == null) {
				throw new Exception("올바르지 않은 파일 형식");
			}
			String upFile = FileUtil.uploadFile(uploadPath, originName, file.getBytes());

			// 썸네일 경로 셋팅
			res.setThumbImage("/app/images/" + upFile);

			// 실제파일 경로 셋팅
			res.setUploadImage("/app/images/" + upFile.substring(2, upFile.length()));
			res.setMsg("성공적으로 업로드 됨");
			res.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			res.setMsg(e.getMessage());
			res.setResult(false);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return res;
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeProcess(BoardVO board, HttpSession session, Errors errors) {
		// 올바른 값인지 벨리데이팅
		new BoardValidator().validate(board, errors);
		if (errors.hasErrors()) {
			return "board/write"; // 에러가 존재한다면 글쓰기 페이지로 보냄
		}
		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러 써도 된다.
		UserVO user = (UserVO) session.getAttribute("user");

		// 로그인한 사용자의 아이디를 글쓴이로 등록하고
		board.setWriter(user.getUserid());

		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(board.getContent());
		board.setContent(clean);

		// 실제 DB에 글을 기록함
		boardService.writeArticle(board);
		return "redirect:/board";
	}
}
