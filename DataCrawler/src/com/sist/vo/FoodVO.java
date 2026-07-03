package com.sist.vo;

import lombok.Data;

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
@Data
public class FoodVO {
	private int no, cno, likecount, replycount, jjimcount, hit;
	private double score;
	private String name, type, phone, address, theme, time, price, reserve, parking, content, poster, images;
}
