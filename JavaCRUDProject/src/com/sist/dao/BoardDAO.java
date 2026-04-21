package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.commons.DBUtil;
import com.sist.vo.BoardVO;
public class BoardDAO {
	private DBUtil db=new DBUtil();
	private Connection conn;
	private PreparedStatement ps;
	private static BoardDAO dao; // 싱글턴
	public static BoardDAO newInstance() {
		if(dao==null)
			dao=new BoardDAO();
		return dao;
	}
	// 기능 => 목록 출력
	public List<BoardVO> board_list(int page) {
		List<BoardVO> list=new ArrayList<BoardVO>();
		try {
			conn=db.getConnection();
			// SQL문장 오라클 전송
			String sql="SELECT /*+ INDEX_DESC(board board_no_pk)*/ no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit FROM board OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
			int start = (page*10)-10;
			ps=conn.prepareStatement(sql);
			// ? 값을 채운다
			ps.setInt(1, start);
			// 실행 => 결과 값
			ResultSet rs=ps.executeQuery();
			while(rs.next()) { // 출력된 첫번째 위치로 커서 이동
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnction(conn, ps);
		}
		return list;
	}
	public static void main(String[] args) {
		BoardDAO dao=BoardDAO.newInstance();
		List<BoardVO> list=dao.board_list(1);
		for(BoardVO vo:list) {
			System.out.println(
					vo.getNo()+" "
					+vo.getSubject()+" "
					+vo.getName()+" "
					+vo.getDbday()+" "
					+vo.getHit()
			);
		}
	}
	
}
