package net.gondr.controller;

import java.io.IOException;

import org.apache.catalina.servlet4preview.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import net.gondr.domain.RegisterDTO;
import net.gondr.util.FileUtil;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private ServletContext context;
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String viewRegisterPage() {
		return "user/registerPage";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String userRegist(RegisterDTO dto) throws Exception {
		String uploadPath = context.getRealPath("/WEB-INF/upload");
		MultipartFile file = dto.getProfileImg();
		String upFile = FileUtil.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
		System.out.println(uploadPath + "에 " + upFile + "로 업로드 됨. ");
		
		return "user/registerPage";
	}
}
