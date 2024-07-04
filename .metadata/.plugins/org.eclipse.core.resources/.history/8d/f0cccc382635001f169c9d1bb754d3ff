package com.spring.coffeeorderproject.controller;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.coffeeorderproject.dto.CartDto;
import com.spring.coffeeorderproject.dto.UserDto;
import com.spring.coffeeorderproject.service.AccountService;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Setter;

@Controller
@RequestMapping(path = { "/userAccount" })
public class AccountController {

	// 필드 선언 + Setter 주입
	@Setter(onMethod_ = { @Autowired })
	private AccountService accountService;
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSender mailSender;
	
	@GetMapping(path = { "/register" })
	public String getRegister(@RequestParam(value="id", defaultValue="false") String id, @ModelAttribute("user") UserDto user, Model model) {
		
		// ID 중복 검사 (request로 결과값 register.jsp로 보내기)
		if (!id.equals("false")) {			
			int isHaveId = accountService.checkId(id);
			
			Boolean rs = isHaveId < 1 ? true : false;
			
			model.addAttribute("isHaveId", rs);
		}
		
		return "/userAccount/register";
	}
	
	@PostMapping(path = { "/register" })
	public String postRegister(@Valid @ModelAttribute("user") UserDto user, BindingResult br) {
//		String id = model.getParameter("userId");
//		String pw = req.getParameter("userPw");
//		String name = req.getParameter("userName");
//		String nickName = req.getParameter("userNickname");
//		String phone = req.getParameter("userPhone");
//		String email = req.getParameter("userEmail");
		
		// 회원가입 유효성 검사
		
		if (br.hasErrors()) {
			for (ObjectError error : br.getAllErrors()) {
				System.out.println(error.getDefaultMessage());
			}
			return "userAccount/register";
		}
		
		accountService.inputUser(user);
		
		return "redirect:/userAccount/login";
	}
	
	
	@GetMapping(path = { "/login" })
	public String loginForm() {
		
		return "userAccount/login";
	}
	
	@PostMapping(path = { "/login" })
	public String login(UserDto user, HttpSession session) {
		
		UserDto member = accountService.signInUser(user);
		
		
		if (member != null) {
			session.setAttribute("loginUser", member);
			
			ArrayList<CartDto> userCart = accountService.getUserCart(user.getUserId());
			session.setAttribute("appUserCart", userCart);
			
			return "redirect:/home";
		} else {
			return "redirect:/login?loginfail=true";
		}
		
	}
	
	@GetMapping(path = { "/logout" })
	public String logout(HttpSession session) {
		//로그아웃 처리 -> 세션에서 데이터 제거
		session.removeAttribute("loginUser");
		return "redirect:/home";
	}
	
	
	/* 비밀번호 재설정 */
	
	@GetMapping(path = { "/checkUser" })
	public String checkUser(String id, Model model) {
		// ID 중복 검사 (request로 결과값 register.jsp로 보내기)
		int isHaveId = accountService.checkId(id);
		
		Boolean rs = isHaveId < 1 ? true : false;
		
		model.addAttribute("isHaveId", rs);
		
//		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/userViews/userAccount/checkUser.jsp");
//		rd.forward(req, resp);
		return "userAccount/checkUser";
	}
	
	@PostMapping(path = { "/sendMail" })
	public String sendMail(String userId, HttpServletRequest req) {
		
		UserDto user = accountService.getUserEmail(userId);
		String to = user.getUserEmail();
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);  // 두번째 인자는 html 로 보낼 것인지, 일반 text 로 보낼 것인지
			
			int key = (int)(Math.random() * 1000000000);
			// application
			ServletContext ss = req.getServletContext();
			ss.setAttribute(to, key);
			
			messageHelper.setFrom("ddslk75@naver.com");
			messageHelper.setTo(new String[] {"ddslk75@naver.com"});
			message.setSubject("고슬링");
			message.setContent(String.format("<html><body><h1><a href='http://192.168.0.16:8081/spring-coffeeorderproject/reset-passwd?email=%s&key=%d'>재설정</a></h1></body></html>", to, key), "text/html;charset=utf-8");
			
			mailSender.send(message);
			
			
		} catch (Exception ex) {
			System.out.println("왜안돼");
			ex.printStackTrace();
		}
		
		return "userAccount/emailMessage";
//		// 192.168.0.16
//		// 사용자 이메일 받아오기
//		String id = req.getParameter("userId");
//		
////		AccountService accountService = new AccountService();
//		UserDto user = accountService.getUserEmail(id);
//	
//		String from = "ddslk75@naver.com";
//		String to = user.getUserEmail();
//		String title = "비밀번호 재설정";
//		
//		Session session = null;
//		
//		try {
//			Properties props = new Properties();
//			props.put("mail.transport.protocol", "smtp");
//			props.put("mail.smtp.host", "smtp.naver.com");
//			props.put("mail.smtp.port", "465");
//			props.put("mail.smtp.auth", true);
//			props.put("mail.smtp.ssl.enable", true);
//			props.put("mail.smtp.ssl.trust", "smtp.naver.com");
//			props.put("mail.smtp.starttls.required", true);
//			props.put("mail.smtp.starttls.enable", true);
//			
//			session = Session.getInstance(props, new Authenticator() {
//				@Override
//				protected PasswordAuthentication getPasswordAuthentication() {				
//					return new PasswordAuthentication("ddslk75", "qjrrksskdwk8747!");					
//				}
//			});
//			
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			System.out.println("fail to create session");
//			session = null;
//		}
//		
//		if (session != null) {
//			Message message = new MimeMessage(session);
//			
//			int key = (int)(Math.random() * 1000000000);
//			// application
//			ServletContext ss = req.getServletContext();
//			ss.setAttribute(to, key);
//			
//			
//			try {
//				message.setFrom(new InternetAddress(from, "제임스고슬링", "utf-8"));
//				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//				message.setSubject(title);
//				message.setContent(String.format("<html><body><a href='http://192.168.0.16:8081/spring-coffeeorderproject/reset-passwd?email=%s&key=%d'>비밀번호 재설정 하기</a></body></html>", to, key), "text/html;charset=utf-8");
//				
//				Transport.send(message);
//				System.out.println("succeeded to send mail");
//				
////				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/userViews/userAccount/emailMessage.jsp");
////				rd.forward(req, resp);
//				
//				return "userAccount/emailMessage";
//				
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				System.out.println("fail to send mail");
//				return "redirect:/home";
//			}
//		} else {
//			return "redirect:/home";
//		}
	}
	
}
