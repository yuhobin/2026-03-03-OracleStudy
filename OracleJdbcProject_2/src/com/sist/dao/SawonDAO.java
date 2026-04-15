package com.sist.dao;

// 오라클 연결 => 요청 데이터 검색 / 수정 / 삭제 / 추가
import java.util.*;
import java.sql.*;

public class SawonDAO {
	// 연결
	private Connection conn;

	// 송수신
	private PreparedStatement ps;

	// 싱글턴
	private static SawonDAO dao;

	// 오라클 주소
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	// 1. 드라이버 등록 => 한번 수행
	public SawonDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 메모리 할당
			// 리플렉션 => 클래스이름으로 제어 (메모리 저장, 변수값 제어, 메소드 호출)
			// ojdbc8.jar
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. 싱글턴 => 사용자 한명 => Connection을 한개만 사용
	public static SawonDAO newInstance() {
		if (dao == null)
			dao = new SawonDAO();
		return dao;
	}

	// 3. 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			// conn hr / happy => SQLPlus
		} catch (Exception e) {

		}
	}

	// 4. 오라클 닫기
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {}
		/////////////// ==>  모든 DAO 의 공통
		/// 로그인 => COUNT
		/// 사원목록 => 페이징 => ROWNUM
		/// 상세보기 => 사번
		/// 통계처리 => GROUP BY
	}
}
