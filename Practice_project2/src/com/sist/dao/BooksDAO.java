package com.sist.dao;

import java.sql.*;
import java.util.*;
import com.sist.vo.*;

public class BooksDAO {
	private Connection conn;
	private PreparedStatement ps;
	
	 // 1. 직접 연결을 위한 설정 정보
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER="hr";
	private final String PWD="happy";
	
	private static BooksDAO dao;
	
	 // 싱글턴 패턴 유지
    public static BooksDAO newInstance() {
        if(dao == null)
            dao = new BooksDAO();
        return dao;
    }
    // 2. 드라이버 등록 (생성자에서 한 번만 실행)
    public BooksDAO() {
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			 System.out.println("드라이버 로딩 실패: " + e.getMessage());
		}
    }
    // 3. 직접 오라클 연결
    public void getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // 4. 직접 연결 해제
    public void disConnection() {
        try {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
