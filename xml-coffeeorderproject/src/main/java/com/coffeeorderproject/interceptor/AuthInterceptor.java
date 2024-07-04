package com.coffeeorderproject.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.coffeeorderproject.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		UserDto user = (UserDto)request.getSession().getAttribute("loginUser");
		String uri = request.getRequestURI();
		// Admin / Payment / MyPage / Board
		if (uri.contains("reviewboardwrite")) {
			if (user == null) {
				
				response.sendRedirect("/xml-coffeeorderproject/userAccount/login");
				return false;
			}
		}
		
		// Admin
		if (uri.contains("/admin-home") || uri.contains("/orders-management") || uri.contains("/user-management") || uri.contains("/product-management") || uri.contains("/revenue-management")
			|| uri.contains("/board-management") || uri.contains("/review-management") || uri.contains("/inquiry-management") || uri.contains("/notice-management")
			|| uri.contains("announcementwrite")) {
			if (user.getUserAdmin() == false) {
				response.sendRedirect("/xml-coffeeorderproject/userAccount/login");
				return false;
			}
		}
		
		
		// Cart
		if (uri.contains("/hollysProduct/")) {
			if (uri.contains("cart-product")) {
				if (user == null) {
					response.sendRedirect("/xml-coffeeorderproject/userAccount/login");
					return false;
				}
			}
		}
		
		return true;
	
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
