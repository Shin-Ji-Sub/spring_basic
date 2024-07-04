package com.demoweb.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/account/logout" })
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃 처리 -> 세션에서 데이터 제거
		// 서블릿에서는 session이 내장 객체가 아니므로 request 객체로부터 session 객체 유도
		HttpSession session = req.getSession();
		session.removeAttribute("loginuser");
		
		// 세션 전체 초기화 (모든 데이터 제거 + 세션 제거)
		//session.invalidate();
		
		// 서블릿으로 이동 -> redirect
		resp.sendRedirect("/demoweb/home");
	}
}
