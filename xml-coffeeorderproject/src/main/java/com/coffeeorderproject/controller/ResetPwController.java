package com.coffeeorderproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffeeorderproject.service.AccountService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

@Controller
public class ResetPwController {
	
	// 필드 선언 + Setter 주입
	@Setter(onMethod_ = { @Autowired })
	private AccountService accountService;

	@GetMapping(path = { "/reset-passwd" })
	public String getReset(HttpServletRequest req) {
		String email = req.getParameter("email");
		
		ServletContext session = req.getServletContext();
		int userKey = (int)session.getAttribute(email);
		
		int paramKey = Integer.parseInt(req.getParameter("key"));
		
		if(userKey == paramKey) {
			session.removeAttribute(email);
//			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/userViews/userAccount/resetPw.jsp");
//			rd.forward(req, resp);
			
			return "userAccount/resetPw";
		} else {
			return "userAccount/login";
		}
	}
	
	@PostMapping(path = { "/reset-passwd" })
	public String postReset(HttpServletRequest req) {
		String userId = req.getParameter("user-id");
		String newPw = req.getParameter("new-pw");
		
		/* Service로 보내고 Service에서 비밀번호 암호화한 뒤에
		 *  DAO에서 Update 처리
		 *  처리하고 나서 forward로 home.jsp로 이동 */
		accountService.changeUserPw(userId, newPw);
		
		
		// home.jsp로 이동 (forward)
//		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/userViews/home.jsp");
//		rd.forward(req, resp);
		return "redirect:/userAccount/login";
	}
	
}
