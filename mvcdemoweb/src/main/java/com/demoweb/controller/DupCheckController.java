package com.demoweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.demoweb.dto.MemberDto;
import com.demoweb.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DupCheckController implements Controller {

	@Override
	public ActionResult handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		// 요청 데이터 읽기
		// 요청 처리
		
		String memberId = req.getParameter("id");
		
		AccountService accountService = new AccountService();
		boolean dup = accountService.checkDuplication(memberId);
		
		
		try {
			resp.setContentType("plain/text;charset=utf-8");
			PrintWriter out;
			out = resp.getWriter();
			out.print(dup);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ActionResult ar = new ActionResult();
		ar.setResponseBody(true);
		return ar;
	}
	
	
	
}
