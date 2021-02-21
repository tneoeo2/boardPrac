package user;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import template.JDBCTemplate;

public class UserDAO {
	JDBCTemplate jdt = JDBCTemplate.getInstance();	//db에 접근
	public UserDAO() {}
	
	public int login(String userID, String userPassword) {
		Connection conn=jdt.getConnection();
		PreparedStatement pstm = null; //쿼리 실행용 객체
		ResultSet rset = null;//쿼리 결과 담아줄 객테
		
		
		try {
			String query = "select userPassword from TB_USER where userID =?";
			pstm = conn.prepareStatement(query);
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
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			jdt.close(rset, pstm, conn);
		}
		
		return -2;//데이터베이스 오류
	}
}
//아이디가 불일치 오류가 계속떴는데 DB커밋을 안해서 바뀐데이터가 적용이 안돼서였다...
