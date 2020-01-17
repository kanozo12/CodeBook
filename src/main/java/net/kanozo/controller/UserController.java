package net.kanozo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.gondr.util.FileUtil;
import net.gondr.validator.RegisterValidator;
import net.kanozo.domain.LoginDTO;
import net.kanozo.domain.RegisterDTO;
import net.kanozo.domain.UserVO;
import net.kanozo.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private ServletContext context;

	@Autowired
	private UserService service;

	private RegisterValidator validator = new RegisterValidator();

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String viewRegisterPage(Model model) {
		model.addAttribute("registerDTO", new RegisterDTO());
		return "user/registerPage";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String userRegist(RegisterDTO dto, Errors errors) throws Exception {
		validator.validate(dto, errors);
		if (errors.hasErrors()) {
			return "user/registerPage";
		}

		String uploadPath = context.getRealPath("/WEB-INF/upload");
		MultipartFile file = dto.getProfileImg();
		String upFile = FileUtil.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());

		// System.out.println(uploadPath + "에 " + upFile + "로 업로드 됨.");

		UserVO user = new UserVO();
		user.setImg(upFile);
		user.setName(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setUserid(dto.getUserid());

		service.register(user);

		return "redirect:/"; // 회원가입완료후 메인 페이지로 이동
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String viewLoginPage(Model model) {
		model.addAttribute("loginDTO", new LoginDTO());
		return "user/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String userLogin(LoginDTO loginDTO, HttpSession session, Model model) {
		String msg = "로그인 실패, 아이디와 비밀번호를 확인하세요";

		if (loginDTO.getUserid() == "" || loginDTO.getPassword() == "") {
			model.addAttribute("msg", msg);
			return "user/login";
		}
		UserVO user = service.login(loginDTO.getUserid(), loginDTO.getPassword());

		if (user == null) {
			return "user/login";
		}

		session.setAttribute("user", user);
		return "redirect:/"; // 메인화면으로 리다이렉트 (차후 이부분 수정가능)
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String userLogout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}

	@RequestMapping(value = "profile/{file:.+}") // 모든 문자가 1개 이상 있어야 함
	@ResponseBody // 몸통 그대로 반환, 뷰를 거치지 않는
	public byte[] getUserProfile(@PathVariable String file) throws IOException {
		String uploadPath = context.getRealPath("/WEB-INF/upload");
		File profile = new File(uploadPath + File.separator + file);

		FileInputStream fis = new FileInputStream(profile);

		return IOUtils.toByteArray(fis);
	}
}
