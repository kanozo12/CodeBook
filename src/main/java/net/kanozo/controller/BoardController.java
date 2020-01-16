package net.kanozo.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.gondr.util.FileUtil;
import net.gondr.util.MediaUtil;
import net.kanozo.domain.BoardVO;
import net.kanozo.domain.UploadResponse;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Autowired
	private ServletContext context;

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
}
