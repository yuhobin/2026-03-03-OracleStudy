package com.sist.books;
import java.util.*;
import java.sql.*;
public class BooksDAO {
	 // 전체적으로 사용 
	  private Connection conn; // Socket => 연결 담당 
	  private PreparedStatement ps; // BufferedReader , OutputStream 
	  // 송(SQL문장) 수신(오라클에서 결과값 받기)
	  private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	  
	  // 1. 드라이버 등록 
	  public BooksDAO()
	  {
		  try
		  {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
	  }
	  // 2. 오라클 연결 
	  public void getConnection()
	  {
		  try
		  {
			  conn=DriverManager.getConnection(URL,"hr",
					         "happy");
			  // conn => SQL PLus
		  }catch(Exception ex) {}
	  }
	  // 3. 오라클 연결 해제 
	  public void disConnection()
	  {
		  try
		  {
			  if(ps!=null) ps.close();
			  if(conn!=null) conn.close();
		  }catch(Exception ex) {}
	  }
	  // 4. 기능 => 데이터 추가 
	  public void booksInsert(BooksVO vo)
	  {
		  try
		  {
			  //1 연결
			  getConnection();
			  //2. SQL => 전송 
			  /*
			   *     NO                                        NOT NULL NUMBER
					 BOOKNAME                                  NOT NULL VARCHAR2(2000)
					 POSTER                                    NOT NULL VARCHAR2(260)
					 AUTHOR                                    NOT NULL VARCHAR2(1000)
					 PRICE                                     NOT NULL VARCHAR2(100)
					 PUBDATE                                   NOT NULL VARCHAR2(100)
					 ISBN                                      NOT NULL VARCHAR2(100)
					 CONTENT                                            CLOB
					 TAG    
			   */
			  String sql="INSERT INTO books VALUES("
					    +"books_no_seq.nextval,?,?,?,?,?,?,?,?)";
			  // 1. ; => 자동 추가 
			  // 2. AutoCommit
			  // 3. ? 갯수만큼 => 값을 채운다 
			  ps=conn.prepareStatement(sql);
			  // ?에 값을 채운다 
			  // String name="홍길동 "
			  ps.setString(1, vo.getBookname()); // '홍길동'
			  // setString() setInt() setDate() setDouble()
			  // -----------          --------- 자동으로 ''가 붙는다
			  ps.setString(2, vo.getPoster());
			  ps.setString(3, vo.getAuthor());
			  ps.setString(4, vo.getPrice());
			  ps.setString(5, vo.getPubdate());
			  ps.setString(6, vo.getIsbn());
			  ps.setString(7, vo.getContent());
			  ps.setString(8, vo.getTag());
			  // 실행 
			  ps.executeUpdate(); // commit포함 
			  /*
			   *   실행 
			   *   = executeQuery() ==> ResultSet : 결과값이 저장된 메모리
			   *      SELECT시 사용 => 데이터의 변경이 없다 
			   *   = executeUpdate() ==> int 
			   *      INSERT / UPDATE / DELETE시 사용 
			   *      => CREATE / DROP / ALTER / TRUNCATE 
			   *          | => WEB 3.0 => 개인 사이트 (콘텐츠 관리)
			   *          | => 블록체인 
			   *      
			   */
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
	  }
}