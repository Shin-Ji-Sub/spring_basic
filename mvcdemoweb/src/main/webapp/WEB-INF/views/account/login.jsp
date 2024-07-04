<%@ page language="java" 
		 contentType="text/html; charset=utf-8" 
		 pageEncoding="utf-8"%>

<!DOCTYPE html>

<html>
<head>
	<meta charset='utf-8' />
	<title>Login</title>
	<link rel='Stylesheet' href='/mvcdemoweb/styles/default.css' />
	<link rel='Stylesheet' href='/mvcdemoweb/styles/input.css' />
</head>
<body>

	<div id='pageContainer'>
		
		<!-- <div id="header">    	
            <div class="title">
                <a href="/mvcdemoweb/home">DEMO WEBSITE</a>
            </div>
            <div class="links">
            	<a href="/mvcdemoweb/account/login">로그인</a>
                <a href="/mvcdemoweb/account/register">회원가입</a>
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
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div id="inputcontent">
			<br /><br />
		    <div id="inputmain">
		        <div class="inputsubtitle">로그인정보</div>
		        
		        <form action="login" method="post">
		       
		        <table>
		            <tr>
		                <th>아이디(ID)</th>
		                <td>
		                    <input type="text" name="memberId" style="width:280px" />
		                </td>
		            </tr>
		            <tr>
		                <th>비밀번호</th>
		                <td>
		                	<input type="password" name="passwd" style="width:280px" />
		                </td>
		            </tr>
		        </table>
		        
		        <div class="buttons">
		        	<input type="submit" value="로그인" style="height:25px" />
		        	<input type="button" id="cancel-btn" value="취소" style="height:25px" />
		        	<input type="button" id="reset-pwd-btn" value="비밀번호초기화" style="height:25px" />
		        </div>
		        </form>
		        
		    </div>
		</div>  	
	</div>
	
	<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
	<script type="text/javascript">
		$(() => {
			$('#cancel-btn').on("click", (e) => {
				location.href = "/mvcdemoweb/home";
			});
			
			$('#reset-pwd-btn').on("click", (e) => {
				location.href = "/mvcdemoweb/account/reset-pwd";
			});
			
			<%-- 서블릿에서 forward 이동했을 경우 로그인 실패인 경우 --%>
			<% if (request.getAttribute("loginfail") != null) { %>
			window.alert("로그인 실패 (forward)");
			<% } %>
			
			<%-- 서블릿에서 redirect 이동했을 경우 로그인 실패인 경우 --%>
			<% if (request.getParameter("loginfail") != null) { %>
			window.alert("로그인 실패 (redirect)");
			<% } %>
		});
	</script>

</body>
</html>


