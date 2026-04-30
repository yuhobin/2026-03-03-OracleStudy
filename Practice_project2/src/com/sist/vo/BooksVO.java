package com.sist.vo;
/*
 * 이름       널?       유형             
-------- -------- -------------- 
NO       NOT NULL NUMBER         
BOOKNAME NOT NULL VARCHAR2(2000) 
POSTER   NOT NULL VARCHAR2(260)  
AUTHOR   NOT NULL VARCHAR2(1000) 
PRICE    NOT NULL VARCHAR2(100)  
PUBDATE  NOT NULL VARCHAR2(100)  
ISBN     NOT NULL VARCHAR2(100)  
CONTENT           CLOB           
TAG               CLOB           

 */
import java.util.*;

import lombok.Data;
@Data
public class BooksVO {
	private int no;
	private String bookname, poster, author, price, pubdate, isbn, content, tag;
}
