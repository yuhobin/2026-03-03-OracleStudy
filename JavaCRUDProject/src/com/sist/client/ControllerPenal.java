package com.sist.client;
import java.util.*;
import java.awt.*;
import javax.swing.*;
public class ControllerPenal extends JPanel{
	CardLayout card=new CardLayout();
	UserMainForm mf;
	BoardList bList;
	HomePanel hp=new HomePanel();
	public ControllerPenal(UserMainForm mf) {
		this.mf=mf;
		bList=new BoardList(mf);
		setLayout(card);
		//setBackground(Color.CYAN);
		add("HOME",hp);
		add("BLIST",bList);
	}
}
