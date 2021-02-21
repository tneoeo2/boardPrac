<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty property="userID" name="user" />
<jsp:setProperty property="userPassword" name="user" />
<%-- 
 현재 페이지 안에서만 user페이지를 자바 Bean으로 사용한다 
 JSP : 데이터를 보여주는 기능
 화면출력과 데이터 보여주는 부분이 하나의 jsp에 섞이면 문제 발생
 => 기능확장이나 코드 재사용이 힘들어진다. 따라서 데이터를 자바빈이라는 클래스에 담아서 보내주는 것
--%>
<%-- 
	name : 프로퍼티 값을 변경할 자바빈 객체의 이름. 
			<jsp:useBean> 액션 태그의 id 속성에서 지정한 값을 사용
	property : 값을 지정할 프로퍼티의 이름
	value : 프로퍼티 값. 표현식 사용가능
--%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" , initial-scale="1">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
		UserDAO userDAO = new UserDAO();
	int result = userDAO.login(user.getUserID(), user.getUserPassword());
	if (result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'main.jsp'");
		script.println("</script>");
	} else if (result == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호가 틀립니다.')");
		script.println("history.back()");
		script.println("</script>");
	} else if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('존재하지 않는 아이디입니다.')");
		script.println("history.back()");
		script.println("</script>");
	} else if (result == -2) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터베이스 오류가 발생했습니다.')");
		script.println("history.back()");
		script.println("</script>");
	}
	%>
</body>
</html>