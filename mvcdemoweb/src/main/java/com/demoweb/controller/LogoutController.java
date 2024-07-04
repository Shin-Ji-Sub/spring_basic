package com.demoweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutController implements Controller {

	@Override
	public ActionResult handleRequest(HttpServletRequest req, HttpServletResponse resp) {

//		String method = req.getMethod().toLowerCase();
//		if (method.equals("get")) {
//			
//		} else {
//			
//		}
		
		return doGet(req, resp);
		
	}
	
	
	
	private ActionResult doGet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		session.removeAttribute("loginuser");
		
		// 서블릿으로 이동 -> redirect
		ActionResult ar = new ActionResult("/mvcdemoweb/home", true);
		return ar;
	}
	
}
