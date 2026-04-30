package com.sist.dao;
import java.util.*;
import com.sist.commons.DBUtil;
import com.sist.vo.BooksVO;
import com.sist.vo.OrdersVO;

import java.sql.*;
public class BooksDAO {
	private DBUtil db=new DBUtil();
	private Connection conn;
	private PreparedStatement ps;
	
	
	// 메인화면 출력
	public List<BooksVO> booksListData(int type,int page)
	  {
		  List<BooksVO> list=
				     new ArrayList<BooksVO>();
		  try
		  {
			  // 1. 연결
			  conn=db.getConnection();
			  
			  // 2. SQL문장 
			  String sql="SELECT bno,poster,bookname,price "
					    +"FROM Book" 
					    +" ORDER BY bno ASC "
					    +"OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
			            // 12c => 페이지 나누기 => 인라인뷰 
			  // 3. 전송 
			  ps=conn.prepareStatement(sql);
			  // 4. ?에 값을 채운다 => ?가 없는 경우 통과 
			  ps.setInt(1, (page*12)-12); // 0번부터 시작 
			  // 결과값 받기 
			  ResultSet rs=ps.executeQuery();
			  // 실행된 결과를 메모리에 저장 => ResultSet 
			  // next() => 처음부터 => 마지막까지 
			  while(rs.next())
			  {
				  BooksVO vo=new BooksVO();
				  vo.setNo(rs.getInt(1));
				  vo.setPoster(rs.getString(2));
				  vo.setBookname(rs.getString(3));
				  vo.setPrice(rs.getString(4));
				  list.add(vo);
			  }
			  rs.close(); // 실행시마다 => 데이터 저장이 다르다 => 지역변수 
			  
		  }catch(Exception ex)
		  {
			  // 오류 출력 
			  ex.printStackTrace();
		  }
		  finally
		  {
			  // 오라클 닫기 => 오류가 있던 없던 상관없이 
			  db.disConnection(conn, ps);
		  }
		  return list;
	  }
	  // 총페이지 
	  public int booksTotalPage(int type)
	  {
		  int total=0;
		  try
		  {
			  conn=db.getConnection();
			  String sql="SELECT CEIL(COUNT(*)/12.0) "
					    +"FROM Book";
			  ps=conn.prepareStatement(sql);
			  ResultSet rs=ps.executeQuery();
			  rs.next();
			  total=rs.getInt(1);
			  rs.close();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  db.disConnection(conn, ps);
		  }
		  return total;
	  }
	// 데이터 추가
	public void booksInsert(BooksVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO books VALUES("
					+ "books_no_seq.nextval,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getBookname());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getAuthor());
			ps.setString(4, vo.getPrice());
			ps.setString(5, vo.getPubdate());
			ps.setString(6, vo.getIsbn());
			ps.setString(7, vo.getContent());
			ps.setString(8, vo.getTag());
			ps.executeUpdate(); //commit 포함
			/*
			 *     실행
			 *     = executeQuery() => ResultSet : 결과값이 저장된 메모리
			 *       SELECT 시 사용 => 데이터 변경 X
			 *     = executeUpdate() => int
			 *       INSERT/UPDATE/DELETE 시 사용 => 데이터 변경
			 *       CREATE/DROP/ALTER/TRUNCATE
			 *       |=> WEB 3.0 => 개인 사이트(컨텐츠 관리)
			 *       |=> 블록체인
			 */
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	//상세 정보
	public BooksVO booksDetailData(int type,int gno)
	  {
		BooksVO vo=new BooksVO();
		  try
		  {
			  conn=db.getConnection();
			  String sql="SELECT bno,bookname,poster,author,price,"
					    +"pubdate,isbn,content,tag "
					    +"FROM Book"
					    +" WHERE bno=?";
			  ps=conn.prepareStatement(sql);
			  ps.setInt(1, gno);
			  ResultSet rs=ps.executeQuery();
			  // no => 중복이 없다 => ROW한개 출력 
			  rs.next();
			  vo.setNo(rs.getInt(1));
			  vo.setBookname(rs.getString(2));
			  vo.setPoster(rs.getString(3));
			  vo.setAuthor(rs.getString(4));
			  vo.setPrice(rs.getString(5));
			  vo.setPubdate(rs.getString(6));
			  vo.setIsbn(rs.getString(7));
			  vo.setContent(rs.getString(8));
			  vo.setTag(rs.getString(9));
			  rs.close();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  db.disConnection(conn, ps);
		  }
		  return vo;
	  }
	public List<BooksVO> orderBooksData(String id) {						// 개인 대출 목록 출력
		List<BooksVO> list=new ArrayList<BooksVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT orderid,bookname,author,isbn,TO_CHAR(ordate,'YYYY-MM-DD'),TO_CHAR(duedate,'YYYY-MM-DD'),a.status,poster "
					+ "FROM orders a JOIN book b "
					+ "ON a.bno=b.bno "
					+ "AND a.memid=? "
					+ "ORDER BY orderid";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				BooksVO vo=new BooksVO();
				vo.getOvo().setOrderid(rs.getInt(1));
				vo.setBookname(rs.getString(2));
				vo.setAuthor(rs.getString(3));
				vo.setIsbn(rs.getString(4));
				vo.getOvo().setOrdateS(rs.getString(5));
				vo.getOvo().setDuedateS(rs.getString(6));
				vo.getOvo().setStatus(rs.getString(7));
				vo.setPoster(rs.getString(8));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	// 도서 반납 시 도서 데이터 변경 코드 주석
	public void OrderReturn(int orderid) {
//	    try {
//	        // 1. DB 연결
//	        conn=db.getConnection();
//	        
//	        // 2. SQL 문장: 데이터를 삭제(DELETE)하지 않고 상태만 '반납완료'로 수정
//	        // 현재 날짜(SYSDATE)를 반납일로 기록합니다.
//	        String sql = "UPDATE orders SET status='반납완료', duedate=SYSDATE "
//	                   + "WHERE orderid=" + orderid;
//	        
//	        // 3. 문장 전송 및 실행
//	        ps = conn.prepareStatement(sql);
//	        ps.executeUpdate();
//	        
//	    } catch (Exception e) {
//	        // 에러 발생 시 출력
//	        e.printStackTrace();
//	    } finally {
//	        // 4. DB 연결 해제 (필수!)
//	        db.disConnection(conn, ps);
//	    }
	}
	
	
	
	
}
