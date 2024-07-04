<%@page import="com.demoweb.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<% String bgColor = request.getParameter("bgcolor"); %>
	<% if (bgColor != null && bgColor.length() > 0) { %>
	<div id="header" style="background-color:<%= bgColor %>;">
	<% } else { %>
	<div id="header">
	<% } %>
		<div class="title">
			<a href="/demoweb/home">DEMO WEBSITE</a>
		</div>
		<div class="links">
			<%
				MemberDto member = (MemberDto)session.getAttribute("loginuser");
			%>
			<% if (member == null) { %>
			<a href="/demoweb/account/login">로그인</a> 
			<a href="/demoweb/register-process">회원가입</a>
			<% } else { %>
			Hello, <%= member.getMemberid() %>
			<a href="/demoweb/account/logout">로그아웃</a>
			<% } %>
		</div>
	</div>

	<div id="menu">
		<div>
			<ul>
				<li><a href="#">사용자관리</a></li>
				<li><a href="#">메일보내기</a></li>
				<li><a href="#">자료실</a></li>
				<li><a href="/demoweb/board/list">게시판</a></li>
			</ul>
		</div>
	</div>
	
	<div id="counter" style="text-align: right; padding: 5px; margin-top: 1px; font-size: 1.2rem; border-bottom: 1px double black;">
		[TOTAL : <%= application.getAttribute("total") == null ? 0 : application.getAttribute("total") %>]
		[CURRENT : <%= application.getAttribute("current") == null ? 0 : application.getAttribute("current") %>]
	</div>
	