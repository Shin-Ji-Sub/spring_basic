package com.example.spring.mvc.view;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.springframework.web.servlet.View;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// JSTL VIEW
public class MyView implements View {

	@Override
	public void render(Map<String, ?> model,  // Controller에서 전달한 데이터 
					   HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("content from custom view");
		out.println("Data from controller");
		for (Object key : model.keySet()) {
			out.println(model.get(key));
		}
		out.println(new Date().toString());
		
	}

	
	
}
