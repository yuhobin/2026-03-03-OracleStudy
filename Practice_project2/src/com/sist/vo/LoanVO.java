package com.sist.vo;
/*
 * 이름          널?       유형           
----------- -------- ------------ 
LNO         NOT NULL NUMBER       
ID          NOT NULL VARCHAR2(20) 
BOOKNO      NOT NULL NUMBER       
LOAN_DATE   NOT NULL DATE         
DUE_DATE    NOT NULL DATE         
RETURN_DATE          DATE         
STATUS               VARCHAR2(20) 
 */
import java.util.*;

import lombok.Data;
@Data
public class LoanVO {
	private int lno, bookid; 
	private String bookname, id, status, dbday; 
	private Date loan_date, due_date, return_date;
}
