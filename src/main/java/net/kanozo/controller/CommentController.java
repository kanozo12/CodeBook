package net.kanozo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import net.kanozo.domain.ComVO;
import net.kanozo.domain.UserVO;
import net.kanozo.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	CommentService cService;

	// 댓글 입력
	@RequestMapping(value = "/board/view/{bno}/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(@PathVariable Integer bno, ComVO comVO, HttpSession session, HttpServletRequest req) {

		// 여기는 인터셉터에 의해서 로그인하지 않은 사용자는 막히게 될 것이기 때문에 그냥 에러처리 없이 user를 불러써도 된다.
		UserVO user = (UserVO) session.getAttribute("user");
		
		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(comVO.getComContent());
		comVO.setComContent(clean);
		
		String userid = user.getUserid();
		comVO.setBno(bno);
		comVO.setComWriter(userid);
		comVO.setUserName(user.getName());
		
		cService.insert(comVO);
	}

}
