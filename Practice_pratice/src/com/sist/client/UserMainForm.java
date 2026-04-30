package com.sist.client;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sist.client.Login;
public class UserMainForm extends JFrame
implements ActionListener
{
	// 
	static boolean bLogin=false; // false : 로그인 전, true : 로그인 성공
	static String myId;
	static String isAdmin="n"; // n : 일반 회원, y : 관리자
	
	
	MenuPenal mp=new MenuPenal();
	ControllerPanel cp;
	OrderControlForm ocf;
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

		String s = isAdmin.equals("y")? "관리자" : "사용자";
		String title = myId+"(" + s + ")님 환영합니다";
		setTitle(title);
		
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	mp.b1.addActionListener(this);
    	mp.b4.addActionListener(this);
    	mp.b6.addActionListener(this);
    	mp.b7.addActionListener(this);
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
		else if(e.getSource()==mp.b6) {
			cp.card.show(cp, "OLIST");
			cp.ocf.print();
		}
		else if(e.getSource()==mp.b7) {
			cp.card.show(cp, "SUPPORT");
			cp.sf.print();
		}
	}

}