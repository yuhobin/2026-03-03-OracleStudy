package com.sist.client;
import java.util.*;
import java.awt.*;
import javax.swing.*;
// 화면 관리 
public class ControllerPanel extends JPanel{
	CardLayout card=new CardLayout();
	UserMainForm mf;
	BoardList bList;
	BoardInsert bInsert;
	BoardDetail bDetail;
	BoardDelete bDelete;
	OrderControlForm ocf;
	BooksDetailForm bdf;
	SupportForm sf;
	HomePanel hp = new HomePanel(this);
	String myId;

    public ControllerPanel(UserMainForm mf)
    {
    	this.mf=mf;
    	bList=new BoardList(mf);
    	bInsert=new BoardInsert(mf);
    	bDetail=new BoardDetail(mf);
    	bDelete=new BoardDelete(mf);
    	bdf = new BooksDetailForm(this);
    	ocf=new OrderControlForm(this);
    	sf=new SupportForm(this);
       
    	
    	setLayout(card);
    	//setBackground(Color.CYAN);
    	add("HOME",hp);
    	add("BLIST",bList);
    	add("BINSERT",bInsert);
    	add("BDETAIL",bDetail);
    	add("BDELETE",bDelete);
    	add("OLIST",ocf);
    	add("DETAIL",bdf);
    	add("SUPPORT",sf);

       
        
        card.show(this, "HOME");
    }
}
