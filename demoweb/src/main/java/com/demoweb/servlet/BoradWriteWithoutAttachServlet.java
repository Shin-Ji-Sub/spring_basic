package com.demoweb.servlet;

import java.io.IOException;

import com.demoweb.dto.BoardDto;
import com.demoweb.dto.MemberDto;
import com.demoweb.service.BoardService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet(urlPatterns = { "/board/write" })
public class BoradWriteWithoutAttachServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 로그인 여부 확인 : Session 확인
		// 로그인 하지 않은 사용자는 로그인 화면으로 이동 (redirect)
		// 서블릿에는 session 내장 객체가 없으므로 request 객체에서 유도
//		HttpSession session = req.getSession();
//		if (session.getAttribute("loginuser") == null) {
//			resp.sendRedirect("/demoweb/account/login");
//			return;
//		}
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// Filter에서 처리
//		HttpSession session = req.getSession();
//		if (session.getAttribute("loginuser") == null) {
//			resp.sendRedirect("/demoweb/account/login");
//			return;
//		}
		
//		HttpSession session = req.getSession();
		
		String title = req.getParameter("title");
		String writer = req.getParameter("writer");
		String content = req.getParameter("content");
		
		BoardDto board = new BoardDto();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		BoardService boardService = new BoardService();
		boardService.writeBoard(board);
		
		resp.sendRedirect("/demoweb/board/list");
		
	}
	
}
