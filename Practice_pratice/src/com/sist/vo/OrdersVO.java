package com.sist.vo;
import java.util.*;

import lombok.Data;
@Data
public class OrdersVO {
	private int orderid,bno;
	private String memid,status,ordateS,duedateS;
	private Date ordate,duedate;
}
