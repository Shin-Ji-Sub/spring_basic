package com.demoweb.servlet;

import java.io.File;
import java.io.IOException;

import com.demoweb.dto.BoardAttachDto;
import com.demoweb.service.BoardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/board/delete-attach" })
public class BoardDeleteAttachServlet extends HttpServlet {
	
	private BoardService boardService = new BoardService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int attachNo = Integer.parseInt(req.getParameter("attachno"));
		
		
		// 파일 삭제(우선 순위)
		BoardAttachDto boardAttach = boardService.findBoardAttachByAttachNo(attachNo);
		String fileName = boardAttach.getSavedFileName();
		String dir = req.getServletContext().getRealPath("/board-attachments");
		File fileToDelete = new File(dir, fileName);
		
		if (fileToDelete.exists()) {
			fileToDelete.delete();
		}
		
		// 데이터 삭제(후 순위)
		boardService.deleteBoardAttach(attachNo);
		
		// 편집화면으로 이동
		String sBoardNo = req.getParameter("boardno");
		resp.sendRedirect("/demoweb/board/edit?boardno=" + sBoardNo);
	
	}
	
}
