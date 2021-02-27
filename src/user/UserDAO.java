package user;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.template.JDBCTemplate;

public class UserDAO {
	JDBCTemplate jdt = JDBCTemplate.getInstance();	//db에 접근
	public UserDAO() {}
	
	Connection conn = jdt.getConnection();
	
	PreparedStatement pstm = null; //쿼리 실행용 객체
	
	ResultSet rset = null;//쿼리 결과 담아줄 객테
	public int login(String userID, String userPassword) {
		
		//executeQuery : select구문을 수행할 때 사용되는 함수
		//executeUpdate : select구문을 제외한 다른 구문을 수행할 때 사용되는 함수
		try {
			String query = "select userPassword from TB_USER where userID =?";
			pstm = conn.prepareStatement(query);
			System.out.println("DB 연결요청");
			pstm.setString(1,  userID);
			rset = pstm.executeQuery();
			if(rset.next()) {
				if(rset.getString(1).equals(userPassword)) {
					return 1;	//로그인 성공
				}else {
					return 0;// 비밀번호 불일치
				}
			}
			return -1;	//아이디가 없음
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			jdt.close(rset, pstm);
		}
		
		return -2;//데이터베이스 오류 -> WEB-INF/lib 뒤에 ojdbc6.jar 넣어줘야 db 오류 안남
	}

//아이디가 불일치 오류가 계속떴는데 DB커밋을 안해서 바뀐데이터가 적용이 안돼서였다...

public int join(User user) {
	String query = "INSERT INTO TB_USER (USERID, USERPASSWORD, USERNAME, USERGENDER, USEREMAIL)"
			+ "VALUES (?, ?, ?, ?, ?)";
	try {
		pstm = conn.prepareStatement(query);
		pstm.setString(1, user.getUserID());
		pstm.setString(2, user.getUserPassword());
		pstm.setString(3, user.getUserName());
		pstm.setString(4, user.getUserGender());
		pstm.setString(5, user.getUserEmail());
		
		return pstm.executeUpdate();	//해당 statment를 실행한 결과를 넣어줌
										//insert문 실행시 반드시 0이상의 수가 반환된다.	
	}catch(Exception e){
		e.printStackTrace();
		
	}
	return -1;	//데이터베이스 오류
	
	
	}
}