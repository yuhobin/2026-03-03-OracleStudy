package com.sist.dao;

/*
 * 	자바 
 * 		=> 변수 (VO) , 필요시 => 매개변수 / 지역변수
 * 							  | => 사용자 요청값
 * 		=> 연산자 : 산술연산자, 대입연산자
 * 		=> 제어문 : if / for / while 
 * 		=> 배열 / List
 * 				----- VO를 모아서 전송 
 * 		=> 객체 지향 프로그램 
 * 			=> 캡슐화 : VO
 * 			=> 포함 => Connection / PreparedStatement 
 * 			=> 오버라이딩 
 * 			=> class 클래스 / 메소드
 * 							=> 리턴형 / 매개변수 
 * 		=> 예외처리
 * 			try ~ catch
 * 		=> 라이브러리 
 * 			String / Math(ceil) / StringTokenizer
 * 			Date / FileInputStream / FileOutputStream
 * 			BufferedReader
 * 			Connection / PreparedStatement / ResultSet
 * 			*** List / Map
 * 		------------------------------------------------
 * 		J2EE : 브라우저에서 값 받기 / 브라우저로 값 전송 
 * 		  
 */
import java.util.*; // List

import com.sist.vo.MovieVO;

import java.sql.*; // 오라클 (데이터베이스) 연결
/*
 * 	1. 드라이버 등록
 * 		연결할 준비 
 * 		Class.forName("oracle.jdbc.driver.OracleDriver")
 * 						com.mysql.cj.driver.Driver
 * 						=> mysql, mariadb
 * 	2. 오라클 연결
 * 		Connection conn=Drivermanager.getConnection(URL,username,password)
 * 		=> conn hr/happy
 * 		URL
 * 			jdbc:업체명:드라이버종류:@IP:PORT:데이터베이스명
 * 				 oracle thin  localhost 1521 XE
 * 								=> IP
 * 								=> GIT
 * 								localhost
 * 								=> 127.0.0.1
 * 	3. SQL문장 제작 (*****)
 * 		String sql="SELECT / INSERT / UPDATE / DELETE"
 * 				=> MyBatis : XML
 * 				=> jpa : 메소드로 만든다 
 * 	4. SQL문장을 오라클로 전송
 * 		PreparedStatement ps=conn.preparedStatement(sql)
 * 	5. 오라클 실행 => 결과값 받기(*****)
 * 		ResultSet rs=ps.executeQuery() // SELECT 
 * 		ps.executeUpdate() // INSERT, UPDATE, DELETE
 *  6. List/VO에 값을 채운다(*****) list.add()
 *  7. 닫기 ps.close() / conn.close()
 *  
 *  == 기능 
 *  목록  : 사용자가 페이지 요청 
 *  		-------------- 매개변수
 *  		20개
 *  		1 ROW => VO => while
 *  				객체 저장 => List
 *  		리턴형 List<MovieVo>
 *  		매개변수 (int page)
 *  상세보기 : 사용자가 영화번호 
 *  			   ----- 중복이 없는 데이터 => PRIMARY KEY
 *  		리턴형 : MovieVO
 *  		매개변수 : 영화번호 => int mno
 *  검색 : 리턴형 : List<MovieVO>
 *  	  매개변수 : 2개 
 *  			String fd, String column
 */

public class MovieDAO {
	// 1. 연결 객체
	private Connection conn;
	// 2. 송수신
	private PreparedStatement ps;
	// ResultSet -> SQL문장에 따라서 저장되는 데이터가 다르다 => 지역변수
	// 3. URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	// MySQL / MariaDB => 3306
	// MSSQL => 1433 => pub
	// 단점 : 포트가 다를 수 있다

	// 드라이버 등록 1 => 한번만 설정
	public MovieDAO() {
		// 연결만 : thin 드라이버
		// 오라클에 있는 데이터를 드라이버에 설정 : OCI
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. 오라클 연동 => SQLPlus
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
		}
	}

	// 3. 오라클 닫기
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
		}
	}

	// 공통 사항 => 오라클 반드시 열고 닫기
	// 기능
	// 1. 목록
	public List<MovieVO> movieListData(int page) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장
			String sql="SELECT mno, title, genre, actor, regdate "
						+"FROM movie "
						+"ORDER BY mno "
						+"OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
			// 3. 오라클 전송
			ps=conn.prepareStatement(sql);
			// 4. ?에 값을 채운다
			int start=(page*20)-20; // 0 20 40...
			ps.setInt(1, start);
			
			// 5. 실행 후에 결과값 받기 
			ResultSet rs=ps.executeQuery();
			// new 
			while(rs.next()) {
				// ROW 1개당 => VO ==> 20개
				MovieVO vo=new MovieVO();
				// ROW 단위 => 저장
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setGenre(rs.getString(3));
				vo.setActor(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				// 전체 저장
				list.add(vo);
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	// 1-1. 총페이지
	public int movieTotalpage() {
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
	public MovieVO movieDetailData(int mno) {
		MovieVO vo = new MovieVO();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL 문장
			String sql="SELECT mno, title, actor, genre, grade, regdate, director FROM movie WHERE mno=?";
			// 3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			// 4. ?에 값을 채운다
			ps.setInt(1, mno);
			// 5. 실행 후 결과값 읽기
			ResultSet rs=ps.executeQuery();
			rs.next();
			// 6. 데이터 저장
			vo.setMno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setActor(rs.getString(3));
			vo.setGenre(rs.getString(4));
			vo.setGrade(rs.getString(5));
			vo.setRegdate(rs.getString(6));
			vo.setDirector(rs.getString(7));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}

	// 3. 검색
	/*
	 * 검색이 안됨 SELECT sql="SELECT * FROM movie WHERE "+col+" LIKE '%'||?||'%'"
	 * 
	 * ps.setString(1,col); => '' ps.setString(2,fd); ------------------------ 실제 값만
	 * ? table명 / 컬럼명은 문자열 결합
	 */
	public List<MovieVO> movieFindData(String col, String fd) {
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			// 1. 연결
			getConnection();
			//2. SQL 문장 제작
			String sql="SELECT mno, title, actor, regdate, genre FROM movie WHERE "+col+" LIKE '%'||?||'%'";
			// "WHERE "+col+" LIKE '%"+fd+"%'" 
			// 자바 => 오라클 SQL (LIKE)
			// 3. 오라클로 전송
			ps=conn.prepareStatement(sql);
			// 4. ?에 값 채우기
			ps.setString(1, fd);
			// 5. 실행 후에 결과값 읽기
			ResultSet rs=ps.executeQuery();
			// new 
			while(rs.next()) {
				// ROW 1개당 => VO ==> 20개
				MovieVO vo=new MovieVO();
				// ROW 단위 => 저장
				vo.setMno(rs.getInt(1)); // vo.setMno(rs.getInt("mno")); 
				vo.setTitle(rs.getString(2)); // vo.setTitle(rs.getString("title")); 이런식으로 줘도 됨
				vo.setGenre(rs.getString(5));
				vo.setActor(rs.getString(3));
				vo.setRegdate(rs.getString(4));
				// 전체 저장
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;

	}
	// => 댓글 : CRUD
}
