package com.sist.client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sist.client.UserMainForm;
public class MenuPenal extends JPanel{
      JButton b1,b2,b3,b4,b5,b6,b7,b8;
      public MenuPenal()
      {
    	  b1=new JButton("홈");
    	  b2=new JButton("도서검색");
    	  //b3=new JButton("회원가입");
    	  b4=new JButton("도서게시판");
//    	  b5=new JButton("대출반납");
    	  b6=new JButton("마이페이지");
    	  b7=new JButton("문의하기");
    	  b8=new JButton("관리자페이지");
    	  setLayout(new GridLayout(1,8,5,5));
    	  init();
//    	  add(b1);add(b2);add(b3);add(b4);
//   	  add(b5);
//    	  add(b6);add(b7);add(b8);
      }
      
      public void init() {
  		removeAll();
  		add(b1); // 홈
  		add(b2); // 도서검색
		add(b4); // 도서게시판
  		if(UserMainForm.bLogin) {
  			if(UserMainForm.isAdmin.equals("y")) {
  				add(b8);
  			}
  			else {
  				add(b6); add(b7);
  			}
  		}
  	}
}