<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP BBS</title>
</head>
<body>
	<%
		session.invalidate(); //설정된 세션의 속성 값을 모두 제거, 
								//접속중인 회원의 session을 없애줌(로그아웃)

	%>
	<script>
		location.href = 'main.jsp';
	</script>

</body>
</html>

