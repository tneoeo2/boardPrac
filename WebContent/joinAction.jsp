<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="user" class="user.User" scope="page"></jsp:useBean>
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP BBS</title>
</head>
<body>
	<%
	/*로그인상태 회원의 회원가입 페이지 접속을 방지하기 위한 코드*/
	String userID = null;
    if(session.getAttribute("userID") !=null){ //로그인 상태인 경우
    	userID = (String)session.getAttribute("userID"); //userID 변수에 sessionID 할당
    }
	if(userID != null){	
	    PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이미 로그인 되어있습니다.')");
        script.println("location.href = 'main.jsp'");
        script.println("</script>");
	}

	
		//모든 항목이 입력되었는지 확인한다.
	if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
			|| user.getUserGender() == null || user.getUserEmail() == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안된 사항이 있습니다.')");
		script.println("history.back()");
		script.println("</script>");
	} else {
		UserDAO userDAO = new UserDAO(); //데이터베이스에 접근할 수 있는 객체 생성
		int result = userDAO.join(user);
		if (result == -1) { //회원가입 실패(아이디 중복)
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 존재하는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else { //회원가입 성공
			session.setAttribute("userID", user.getUserID());	
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'"); //회원가입시 로그인처리 후 메인페이지로 이동
			script.println("</script>");
		}

	}
	%>

</body>
</html>

