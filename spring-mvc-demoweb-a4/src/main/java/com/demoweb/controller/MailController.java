package com.demoweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.mail.internet.MimeMessage;
import lombok.Setter;

@Controller
@RequestMapping(path = { "/mail" })
public class MailController {
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSender mailSender;

	@GetMapping(path = { "/send-mail" })
	public String sendMail(Model model) {
		
		boolean success = true;
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);  // 두번째 인자는 html 로 보낼 것인지, 일반 text 로 보낼 것인지
			
			messageHelper.setFrom("ddslk75@naver.com");
			messageHelper.setTo(new String[] {"ddslk75@naver.com", "olozg@naver.com" });
			message.setSubject("chu~");
			message.setContent(String.format("<html><body><h1>%s</h1></body></html>", "윤아 츄베릅"), "text/html;charset=utf-8");
			
			mailSender.send(message);
			
		} catch (Exception ex) {
			success = false;
		}
		
		model.addAttribute("success", success);
		
		return "mail/send-mail-completion";  // /WEB-INF/views/ + mail/send-mail-completion + .jsp
	}
	
}
