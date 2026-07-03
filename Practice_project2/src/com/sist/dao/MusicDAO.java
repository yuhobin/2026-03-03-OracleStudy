package com.sist.dao;

import java.util.*;

import com.sist.vo.MusicVO;

import java.sql.*;

public class MusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	
	// 직접 연결을 위한 설정 정보
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER="hr";
	private final String PWD="happy";
	
	private static MusicDAO dao;
	
	// 싱글턴 
	public static MusicDAO newInstance() {
		if(dao==null) 
			dao=new MusicDAO();
		return dao;
	}
	// 드라이버 등록
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패:"+e.getMessage());
		}
	}
	// 직접 오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,USER,PWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 직접 연결 해제
	public void disConnection () {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// String SQL="SELECT no,cno,title,singer,album,poster,state,idcrement "
	//			+"FROM genie_music ";
	// 기능 음악목록 불러오기
	public List<MusicVO> list(int no) {
		List<MusicVO> list=new ArrayList<MusicVO>();
		try {
			getConnection();
			String sql="SELECT no,cno,title,singer,album,poster,state,idcrement "
					+"FROM genie_music "
					+"ORDER BY no ASC "
					+"OFF SET 0ROWS FETCH NEXT 20ROWS ONLY";
			// 전송
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			// 결과값 받기
			ResultSet rs=ps.executeQuery();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
		
	}
	
}
