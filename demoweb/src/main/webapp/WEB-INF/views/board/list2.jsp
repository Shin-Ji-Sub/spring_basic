<%@page import="com.demoweb.dto.BoardDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>    

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>게시글 목록</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<style>
	a { text-decoration: none }
	</style>
</head>
<body>

	<div id="pageContainer">
	
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" >
			<jsp:param value="black" name="bgcolor"/>
		</jsp:include>
		
		<div style="padding-top:25px;text-align:center">
		
			[ <a href="write">게시글 쓰기</a> ]
			<br /><br />
			
			<table border="1" style="width:600px;margin:0 auto">
				<tr style="background-color:orange;height:30px">
					<th style="width:100px">번호</th>
					<th style="width:300px">제목</th>
					<th style="width:125px">작성자</th>
					<th style="width:50px">조회수</th>
					<th style="width:125px">작성일</th>
					<th style="width:125px">수정일</th>					
				</tr>
				
				<%-- 게시물 목록 --%>
				<% ArrayList<BoardDto> dtoArr = (ArrayList<BoardDto>)request.getAttribute("boardList"); %>
				<% for (int i = 0; i < dtoArr.size(); i++) { %>
					<tr style="background-color:skyblue; color:black; height:30px">
						<th style="width:125px"><%= dtoArr.get(i).getBoardno() %></th>
						<% if (dtoArr.get(i).isDeleted()) { %>
							<th style="width:125px"><span style='color:gray'><%= dtoArr.get(i).getTitle() %> [삭제된 글입니다]</span></th>
						<% } else { %>
							<th style="width:125px"><a href="detail?boardno=<%= dtoArr.get(i).getBoardno() %>"><%= dtoArr.get(i).getTitle() %></a></th>
						<% } %>
						<th style="width:125px"><%= dtoArr.get(i).getWriter() %></th>
						<th style="width:50px"><%= dtoArr.get(i).getReadcount() %></th>
						<th style="width:125px"><%= dtoArr.get(i).getWritedate() %></th>
						<th style="width:125px"><%= dtoArr.get(i).getModifydate() %></th>
					</tr>
				<% } %>
								
			</table>
						
			<br /><br /><br /><br />
		
		</div>
		
	</div>
		

</body>
</html>











