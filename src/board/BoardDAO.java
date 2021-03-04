package board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.template.JDBCTemplate;

public class BoardDAO {
	JDBCTemplate jdt = JDBCTemplate.getInstance(); // db에 접근

	public BoardDAO() {
	}

	Connection conn = jdt.getConnection();

	ResultSet rset = null;// 쿼리 결과 담아줄 객테

	public Date getDate() { // 게시글 작성시 현재 날짜를 넣어주는 함수

		String query = "select to_char(sysdate, 'yyyy-mm-dd') from dual";
		try {
			PreparedStatement pstm = conn.prepareStatement(query); // 함수끼리 충돌?이일어나지 않게 내부에 넣어줌
			rset = pstm.executeQuery();
			if (rset.next()) {
				return rset.getDate(1); // 현재 날짜를 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;// 데이터베이스 오류

	}

	public int getNext() { // 게시글 작성시 현재 날짜를 넣어주는 함수

		String query = "select boardID from tb_board order by boardID DESC"; // 가장 최근 게시물의 게시글번호를 가져옴
		try {
			PreparedStatement pstm = conn.prepareStatement(query); // 함수끼리 충돌?이일어나지 않게 내부에 넣어줌
			rset = pstm.executeQuery();
			if (rset.next()) {
				return rset.getInt(1) + 1; // 현재 날짜를 반환
			}
			return 1; // 현재가 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// 데이터베이스 오류

	}

	public int write(String boardTitle, String userID, String boardContent) {
		String query = "insert into " + "tb_board(boardID, boardTitle, userID, boardDate, boardContent, boardAvailable)"
				+ " values(?,?,?,?,?,?)"; // 가장 최근 게시물의 게시글번호를 가져옴
		try {
			PreparedStatement pstm = conn.prepareStatement(query); // 함수끼리 충돌?이일어나지 않게 내부에 넣어줌
			pstm.setInt(1, getNext());
			pstm.setString(2, boardTitle);
			pstm.setString(3, userID);
			pstm.setDate(4, getDate());
			pstm.setString(5, boardContent);
			pstm.setInt(6, 1);
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// 데이터베이스 오류

	}

	public ArrayList<Board> getList(int pageNumber) {	//특정페이지에 맞는 게시글 리스트를 뽑아주는 함수

		String query = "select *from "
				+"(select * from tb_board where boardAvailable = 1 and boardID < ? "
				+ "order by boardID desc) "
				+"where rownum <=10"; // 가장 최근 게시물의 게시글번호를 가져옴(쿼리문 띄어쓰기 주의)
		ArrayList<Board> list = new ArrayList<Board>();
		try {
			PreparedStatement pstm = conn.prepareStatement(query); // 함수끼리 충돌?이일어나지 않게 내부에 넣어줌
			
			pstm.setInt(1, getNext()-(pageNumber-1) *10);	///???
			rset = pstm.executeQuery();
			
			while (rset.next()) {
				Board board = new Board();
				board.setBoardID(rset.getInt(1));
				board.setBoardTitle(rset.getString(2));
				board.setUserID(rset.getString(3));
				board.setBoardDate(rset.getDate(4));
				board.setBoardContent(rset.getString(5));
				board.setBoardAvailable(rset.getInt(6));
				
				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; //뽑아온 데이터를 list에 담아서 리턴
	}
	
	public boolean nextPage(int pageNumber) {	//페이징처리를 위한 함수
		String query = "select *from "
				+"(select * from tb_board where boardAvailable = 1 and boardID < ? "
				+ "order by  boardID desc) "
				+"where rownum <=10"; // 가장 최근 게시물의 게시글번호를 가져옴
		try {
			PreparedStatement pstm = conn.prepareStatement(query); // 함수끼리 충돌?이일어나지 않게 내부에 넣어줌
			
			pstm.setInt(1, getNext()-(pageNumber-1) *10);	///???
			rset = pstm.executeQuery();
			
			if (rset.next()) {
				return true;	//해당페이지에 값이 하나라도 있다면 true를 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; //
		
	}
}
