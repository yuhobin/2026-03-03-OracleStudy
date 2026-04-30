package com.sist.vo;

import lombok.Data;

@Data
public class BooksVO {
	private int no;
	private String bookname,poster,author,price,pubdate,isbn,content,tag;
	private OrdersVO ovo=new OrdersVO();
}
