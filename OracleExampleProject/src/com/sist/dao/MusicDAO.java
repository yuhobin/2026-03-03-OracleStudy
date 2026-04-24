package com.sist.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.MusicVO;

public class MusicDAO {
	// 1. 드라이버 등록
	// 2. 오라클 연결
	// 3. SQL 문장 제작
	// 4. SQL 문장 오라클로 전달 
	// 5. 오라클 실행 => 결과 값 받기
	// 6. List / VO 값 채우기
	
	// 1. 연결 객체
	private Connection conn;
	// 2. 송수신
	private PreparedStatement ps;
	// 3. URL
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// 4. 드라이버 등록
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	// 5. 오라클 연동 
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) { 
		
		}
		
	}
	// 6. 오라클 닫기 
	public void disConnection() {
		try {
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (Exception e) {
		
		}
	}
	// 여기까지 오라클 열고 닫는 공통 사항
	
	// 기능 
	// 1. 목록 
	public List<MusicVO> musicListData(int page) {
		List<MusicVO> list=new ArrayList<MusicVO>();
		try {
				
			// 1. 연결
			getConnection();
			// 2. SQL 문장
			String sql="SELECT no, cno, title, singer, album FROM genie_music ORDER BY no OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
			// 3. 오라클 전송 => 결과 값 받기
			ps=conn.prepareStatement(sql);
			// 4. ? 값 채우기
			int start=(page*20)-20; // 0 20 40...
			ps.setInt(1, start);
			// 5. 실행 후 결과 값 받기
			ResultSet rs=ps.executeQuery();
			// new
			while(rs.next()) {
				// ROW 1개당 => VO ==> 20개
				MusicVO vo=new MusicVO();
				// ROW 단위 => 저장
				vo.setNo(rs.getInt(1));
				vo.setCno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				// 전체 저장
				list.add(vo);
			}
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
		
	}
	// 1.1 총페이지 
	public int musicTotalpage() {
		int total=0;
		try {
			// 1. 연결
			getConnection();
			// 2. SQL
			String sql="SELECT CEIL(COUNT(*)/20.0) FROM movie";
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. 실행 후에 결과 값을 가지고 온다
			ResultSet rs=ps.executeQuery();
			// 5. 데이터가 출력된 위치에 커서를 올려둔다
			// 첫번째 줄에
			rs.next();
			total=rs.getInt(1);
			rs.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return total;
	}
	// 2. 상세보기
	public MusicVO musicDetailData (int no) {
		MusicVO vo=new MusicVO();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장
			String sql="SELECT no, cno, title, singer, album, state, idcrement FROM genie_music WHERE no=?";
			// 3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			// 4. ?에 값을 채운다
			ps.setInt(1, no);
			// 5. 실행 후 결과값 읽기
			ResultSet rs=ps.executeQuery();
			rs.next();
			// 6. 데이터 저장 
			vo.setNo(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setTitle(rs.getString(3));
			vo.setSinger(rs.getString(4));
			vo.setAlbum(rs.getString(5));
			vo.setState(rs.getString(6));
			vo.setIdcrement(rs.getInt(7));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}

	// 3. 검색
	public List<MusicVO> musicFindData(String column, String fd) {
		List<MusicVO> list=new ArrayList<MusicVO>();
		try {
				
			// 1. 연결
			getConnection();
			// 2. SQL 문장
			String sql="SELECT no, cno, title, singer, album FROM genie_music WHERE "+column+" LIKE '%'||?||'%'";
			// 3. 오라클 전송 => 결과 값 받기
			ps=conn.prepareStatement(sql);
			// 4. ? 값 채우기
			ps.setString(1, fd);
			// 5. 실행 후 결과 값 읽기
			ResultSet rs=ps.executeQuery();
			// new
			while(rs.next()) {
				// ROW 1개당 => VO ==> 20개
				MusicVO vo=new MusicVO();
				// ROW 단위 => 저장
				vo.setNo(rs.getInt(1)); // vo.setMno(rs.getInt("no")); 
				vo.setCno(rs.getInt(2)); // vo.setTitle(rs.getInt("cno")); 이런식으로 줘도 됨
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				// 전체 저장
				list.add(vo);
			}
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
		
	}
	
}
