<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<br /><br /><br />
	<div style="padding-left:20px">
		<h2>Spring MVC Controller Demo</h2>
		
		<h3>1. <a href="demo/param?data1=this%20is%20test%20data&data2=100">Process Request Parameter (GET)</a></h3>
		<h3>2. Process Request Parameter (POST)</h3>
		<form action="demo/param" method="post">
			이름 : <input type="text" name="name" value="홍길동" /><br />
			전화 : <input type="text" name="phone" /><br />
			메일 : <input type="text" name="email" /><br />
			나이 : <input type="text" name="age" /><br />
			나이 : <input type="date" name="birthDate" /><br />
			<input type="submit" value="전송" />
		</form>
		<h3>3. <a href="#">Transfer Data from Controller to View (Refer to 1, 2)</a></h3>
		<h3>4. <a href="demo/redirect">Redirect to Another Action</a></h3>
		<h3>5. <a href="demo/forward">Forward to HTML</a></h3>
		<h3>6-1. <a href="hello.html">static resource NOT IN resources</a></h3>
		<h3>6-2. <a href="resources/hello.html">static resource IN resources</a></h3>
		<h3>7. <a href="demo/custom-view">custom view</a></h3>
	</div>
</body>
</html>
