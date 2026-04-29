package com.sist.vo;

import java.sql.*;

import lombok.Data;

/*
 *  이름                                      널?      유형
 ----------------------------------------- -------- ----------------------------
 ID                                        NOT NULL VARCHAR2(20)
 PWD                                       NOT NULL VARCHAR2(10)
 NAME                                      NOT NULL VARCHAR2(51)
 SEX                                                VARCHAR2(6)
 POST                                      NOT NULL VARCHAR2(7)
 ADDR1                                     NOT NULL VARCHAR2(200)
 ADDR2                                              VARCHAR2(200)
 PHONE                                              VARCHAR2(14)
 CONTENET                                           CLOB
 ISADMIN                                            CHAR(1)
 REGDATE                                            DATE
 */
@Data
public class MemberVO {
	private String id,pwd,sex,name, post,addr1,addr2,phone,content, isadmin, dbday, msg, grade;
	private Date regdate;
}
