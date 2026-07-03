package com.sist.dao;
import java.util.*;

import com.sist.vo.FoodVO;

import java.sql.*;
public class DataDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static DataDAO dao;
	public DataDAO() {
		// ssf=new SqlSessionFactoryBuilder.build(reader)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 싱글턴
	public static DataDAO newInstance() {
		if(dao==null)
			dao=new DataDAO();
		return dao;
	}
	// session=ssf.openSession()
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// session.close()
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// session.insert(id명, vo)
	/*
	 *  NO         NOT NULL NUMBER        
		CNO                 NUMBER        
		NAME       NOT NULL VARCHAR2(100) 
		TYPE       NOT NULL VARCHAR2(200) 
		PHONE      NOT NULL VARCHAR2(20)  
		ADDRESS    NOT NULL VARCHAR2(500) 
		PRICE      NOT NULL VARCHAR2(30)  
		SCORE               NUMBER(2,1)   
		THEME      NOT NULL CLOB          
		TIME                VARCHAR2(50)  
		RESERVE             VARCHAR2(100) 
		PARKING    NOT NULL VARCHAR2(50)  
		CONTENT    NOT NULL CLOB          
		POSTER     NOT NULL VARCHAR2(260) 
		IMAGES              CLOB          
		LIKECOUNT           NUMBER        
		REPLYCOUNT          NUMBER        
		JJIMCOUNT           NUMBER        
		HIT                 NUMBER     
	 */
	public void foodInsert(FoodVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO food2(no, cno, name, type, phone, address, price, score,"
					+"theme, time, reserve, parking, content, poster, images) "
					+"VALUES(food2_no_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getType());
			ps.setString(4, vo.getPhone());
			ps.setString(5, vo.getAddress());
			ps.setString(6, vo.getPrice());
			ps.setDouble(7, vo.getScore());
			
			ps.setString(8, vo.getTheme());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getReserve());
			ps.setString(11, vo.getParking());
			ps.setString(12, vo.getContent());
			ps.setString(13, "https://www.menupan.com"+vo.getPoster());
			ps.setString(14, vo.getImages());
			
			ps.executeUpdate();
			
			/*
			 * 	SqlSession session=ssf.openSession(true)		=> MyBatis
			 * 	session.insert ("foodInsert",vo);
			 * 	session.close();
			 * 
			 * 
			 * 	save(VO)		=> JPA
			 */
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
}
