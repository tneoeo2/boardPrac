<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO" %>
<%@ page import = "java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
 
<jsp:useBean id="user" class="user.User" scope="page"></jsp:useBean>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP BBS</title>
</head>
<body>
    <%
    	/*중복 로그인을 방지하기 위한 코드*/
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
    
        UserDAO userDAO = new UserDAO();
        int result = userDAO.login(user.getUserID(), user.getUserPassword());
        if (result ==1){	//로그인 성공
        	session.setAttribute("userID", user.getUserID());	//로그인한 회원에게 세션아이디를 부여(로그인 유무 확인가능)
        	//session등 다른 내장 객체들은 별도의 생성없이 사용가능(page 디렉티브의 session속성이 'true'로 설정되어있을 때)
        	//
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href = 'main.jsp'");
            script.println("</script>");
        }
        else if (result == 0){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호가 틀립니다.')");
            script.println("history.back()");    // 이전 페이지로 사용자를 보냄
            script.println("</script>");
        }
        else if (result == -1){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('존재하지 않는 아이디입니다.')");
            script.println("history.back()");    // 이전 페이지로 사용자를 보냄
            script.println("</script>");
        }
        else if (result == -2){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('DB 오류가 발생했습니다.')");
            script.println("history.back()");    // 이전 페이지로 사용자를 보냄
            script.println("</script>");
        }
    %>
 
</body>
</html>
 
