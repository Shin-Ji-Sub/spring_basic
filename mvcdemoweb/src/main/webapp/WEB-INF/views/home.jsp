<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset='utf-8' />
	<title>Home</title>
	<link rel='Stylesheet' href='/mvcdemoweb/styles/default.css' />
</head>
<body>

	<div id='pageContainer'>
		
		<!-- <div id="header">    	
            <div class="title">
                <a href="/mvcdemoweb/home">DEMO WEBSITE</a>
            </div>
            <div class="links">
            	<a href="/mvcdemoweb/account/login">로그인</a>
                <a href="/mvcdemoweb/register-process">회원가입</a>
            </div>
        </div>
                
        <div id="menu">
            <div>
                <ul>
                    <li><a href="#">사용자관리</a></li>
					<li><a href="#">메일보내기</a></li>
					<li><a href="#">자료실</a></li>
					<li><a href="#">게시판</a></li>
                </ul>
            </div>
		</div> -->
		<%-- 방법 1. 두 jsp 파일을 각각 순차적으로 실행해서 결과를 병합 --%>
		<%-- <% pageContext.include("/WEB-INF/views/modules/header.jsp"); %> --%>
		
		<%-- 방법 2. 두 jsp 파일을 병합 (기능이 다름) --%>
		<%-- <%@include file="/WEB-INF/views/modules/header.jsp" %> --%>
		
		<%-- 방법 3. 두 jsp 파일을 각각 순차적으로 실행해서 결과를 병합 (제일 많이 사용) --%>
		<jsp:include page="/WEB-INF/views/modules/header.jsp">
			<jsp:param name="bgcolor" value="purple"/>
			<jsp:param name="color" value="black"/>
		</jsp:include>
		
		<div id='content'>
			<br /><br /><br />
			<h2 style='text-align:center'>Welcome to Demo WebSite !!!</h2>
		</div>
	</div>

</body>
</html>


