package com.sist.client;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sist.client.MyPageForm;
import com.sist.client.Login;
public class UserMainForm extends JFrame
implements ActionListener
{
	MenuPenal mp=new MenuPenal();
	ControllerPanel cp;
    public UserMainForm()
    {
    	cp=new ControllerPanel(this);
    	setLayout(null);
    	mp.setBounds(150, 15, 860, 35);
    	add(mp);
    	cp.setBounds(20, 60, 980, 670);
    	add(cp);
    	setSize(1024, 768);
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	mp.b1.addActionListener(this);
    	mp.b4.addActionListener(this);
    	mp.b6.addActionListener(this);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try
        {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        }catch(Exception ex) {}
        new Login();
       
        
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mp.b4) //사내게시판
		{
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		}
		else if(e.getSource()==mp.b1) //Home
		{
			cp.card.show(cp, "HOME");
		}
		if (e.getSource() == mp.b6) { 
	        // 1. 화면을 "MYPAGE"로 전환
	        cp.card.show(cp, "MYPAGE");
	        
	        // 2. 마이페이지 데이터 갱신 (로그인한 아이디의 대출 목록 불러오기)
	        cp.mp.print(); 
	    }
	}

}