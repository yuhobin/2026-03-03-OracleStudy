package com.sist.dao;
import java.sql.*;
import java.util.*;


import com.sist.commons.DBUtil;
/*
 *   ***아이디 중복 체크 
 *   ***우편번호 검색 
 *   ***회원가입 
 *   ***회원정보 
 *   ***회원탈퇴 
 */
import com.sist.vo.*;
public class MemberDAO {
	// 전체적으로 사용 
	  
	// 1. 멤버 변수 및 DBUtil 객체 생성
    private Connection conn;
    private PreparedStatement ps;
    private DBUtil db = new DBUtil(); // 공통 DB 도구 추가
    
    private static MemberDAO dao;
    
    // 2. 싱글턴 패턴 (유지)
    public static MemberDAO newInstance() {
        if(dao == null)
            dao = new MemberDAO();
        return dao;
    }

    // 3. 오라클 연결 (DBUtil 활용)
    public void getConnection() {
        try {
            conn = db.getConnection();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // 4. 오라클 해제 (DBUtil 활용)
    public void disConnection() {
        try {
            db.disConnection(conn, ps);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
	  
	  // 기능 => 우편번호 검색 
	  public List<ZipcodeVO> postFind(String dong)
	  {
		  List<ZipcodeVO> list=
				  new ArrayList<ZipcodeVO>();
		  try
		  {
			  getConnection();
			  String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
					    +"FROM zipcode "
					    +"WHERE dong LIKE '%'||?||'%'";
			  //       +"WHERE dong LIKE '%"+dong+"%'" 
			  //       => SQL Injection
			  //       CONCAT('%',?,'%')
			  // 전송 
			  ps=conn.prepareStatement(sql);
			  ps.setString(1, dong);
			  
			  // 결과값 받기 
			  ResultSet rs=ps.executeQuery();
			  while(rs.next())
			  {
				  ZipcodeVO vo=new ZipcodeVO();
				  vo.setZipcode(rs.getString(1));
				  vo.setSido(rs.getString(2));
				  vo.setGugun(rs.getString(3));
				  vo.setDong(rs.getString(4));
				  vo.setBunji(rs.getString(5));
				  list.add(vo);
			  }
			  rs.close();
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return list;
	  }
	  public int postFindCount(String dong)
	  {
		  int count=0;
		  try
		  {
			  getConnection();
			  String sql="SELECT COUNT(*) "
					    +"FROM zipcode "
					    +"WHERE dong LIKE '%'||?||'%'";
			  //       +"WHERE dong LIKE '%"+dong+"%'" 
			  //       => SQL Injection
			  //       CONCAT('%',?,'%')
			  // 전송 
			  ps=conn.prepareStatement(sql);
			  ps.setString(1, dong);
			  
			  // 결과값 받기 
			  ResultSet rs=ps.executeQuery();
			  rs.next();
			  count=rs.getInt(1);
			  rs.close();
			  // 검색 결과 
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return count;
	  }
	  // 아이디 중복 
	  public int memberIdCheck(String id)
	  {
		  int count=0;
		  try
		  {
			  getConnection();
			  String sql="SELECT COUNT(*) "
					    +"FROM member "
					    +"WHERE memid=?";
			  // 0 , 1 
			  ps=conn.prepareStatement(sql);
			  // ?에 값을 채운다 
			  ps.setString(1, id);
			  // 결과값 
			  ResultSet rs=ps.executeQuery();
			  rs.next();
			  count=rs.getInt(1);
			  rs.close();
			  
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return count;
	  }
	  // 회원 가입 
	  /*
	   *     ID                                        NOT NULL VARCHAR2(20)
			 PWD                                       NOT NULL VARCHAR2(10)
			 NAME                                      NOT NULL VARCHAR2(51)
			 SEX                                                VARCHAR2(6)
			 POST                                      NOT NULL VARCHAR2(7)
			 ADDR1                                     NOT NULL VARCHAR2(200)
			 ADDR2                                              VARCHAR2(200)
			 PHONE                                              VARCHAR2(14)
			 CONTENT                                            CLOB
			 ISADMIN                                            CHAR(1)
			 REGDATE  
	   */
	  public int memberJoin(MemberVO vo)
	  {
		  int check=0;
		  try
		  {
			  getConnection();
			  // "'"+vo.getName()+"','"...
			
			  String sql="INSERT INTO member VALUES(?,?,?,?,?,"
					    +"?,?,?,?,'n',SYSDATE)";
			  ps=conn.prepareStatement(sql);
			  // ?에 값을 채운다 
			  ps.setString(1, vo.getId());
			  ps.setString(2, vo.getPwd());
			  ps.setString(3, vo.getName());
			  ps.setString(4, vo.getSex());
			  ps.setString(5, vo.getPost());
			  ps.setString(6, vo.getAddr1());
			  ps.setString(7, vo.getAddr2());
			  ps.setString(8, vo.getPhone());
			  ps.setString(9, vo.getContent());
			  // 실행 
			  check=ps.executeUpdate(); // commit포함 
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return check;
	  }
	  // 로그인 => Admin  : 관리자 / user : 마이페이지
	  /*
	   *   경우의 수  : 2개 => boolean
	   *              3개이상 => int / String
	   *   아이디가 없는 경우 
	   *   비밀번호가 틀린 경우 
	   *   로그인 경우  
	   *   
	   */
	  public MemberVO isLogin(String id,String pwd)
	  {
		  
		  MemberVO vo=new MemberVO();
		  try
		  {
			  getConnection();
			  String sql="SELECT COUNT(*) "
					    +"FROM member "
					    +"WHERE memid=?";
			  // ID 존재여부 확인 
			  ps=conn.prepareStatement(sql);
			  ps.setString(1, id);
			  ResultSet rs=ps.executeQuery();
			  rs.next();
			  int count=rs.getInt(1);
			  rs.close();
			  
			  if(count==0) // ID가 없는 상태
			  {
				  vo.setMsg("NOID");
			  }
			  else // ID가 존재 
			  {
				  sql="SELECT pwd,memid,isadmin FROM member "
					 +"WHERE memid=?";
				  ps=conn.prepareStatement(sql);
				  ps.setString(1, id);
				  rs=ps.executeQuery();
				  rs.next();
				  String db_pwd=rs.getString(1);
				  String db_id=rs.getString(2);
				  String isadmin=rs.getString(3);
				  rs.close();
				  
				  if(db_pwd.equals(pwd)) // Login
				  {
					  vo.setMsg("OK");
					  vo.setIsadmin(isadmin);
					  vo.setId(db_id);
				  }
				  else
				  {
					  vo.setMsg("NOPWD");
				  }
				  // 웹에서 동일 
			  }
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		  finally
		  {
			  disConnection();
		  }
		  return vo;
	  }
	  // => 메인 출력 
}