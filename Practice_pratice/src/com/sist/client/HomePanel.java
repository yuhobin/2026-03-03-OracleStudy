package com.sist.client;
// 메인 화면 
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.sist.commons.ImageChange;
import com.sist.dao.BooksDAO;
import com.sist.vo.BooksVO;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
public class HomePanel extends JPanel
implements ActionListener,MouseListener
{
   ControllerPanel cp;
   JPanel pan=new JPanel();
   JButton b1,b2,b3,b4,b5,b6;
   JLabel la=new JLabel("0 page / 0 pages");
   JLabel[] imgs=new JLabel[12];
   int curpage=1;
   int totalpage=0;
   BooksDAO dao=new BooksDAO();
   int type=1;
   public HomePanel(ControllerPanel cp)
   {
	   this.cp=cp;
	   setLayout(null);
	   
	   
	   pan.setLayout(new GridLayout(3,4,8,8));
	   pan.setBounds(10,60, 920, 480);
	   //pan.setBackground(Color.pink);
	   add(pan);
	   init();
	   print();
	   
	   
	   b5=new JButton("이전");
	   b6=new JButton("다음");
	   JPanel pp=new JPanel();
	   pp.add(b5);pp.add(la);pp.add(b6);
	   pp.setBounds(10, 550, 920, 35);
	   add(pp);
	   b5.addActionListener(this); // 이전
	   b6.addActionListener(this); // 다음
   }
   public void init()
   {
	   for(int i=0;i<imgs.length;i++)
	   {
		   imgs[i]=new JLabel("");
	   }
	   
	   pan.removeAll(); // JLabel 지우기
	   pan.validate();// Panel 재배치 
   }
   // 화면 출력 
   public void print()
   {
	   List<BooksVO> list=dao.booksListData(type, curpage);
	   
	   totalpage=dao.booksTotalPage(type);
	   
	   for(int i=0;i<list.size();i++)
	   {
		   BooksVO vo=list.get(i);
		   // list에 값을 한개씩 가지고 온다 
		   try
		   {
			   URI uri=new URI(vo.getPoster());
			   URL url=uri.toURL();
			   // new URL() => 경고
			   Image image=
					 ImageChange.getImage(new ImageIcon(url), 250, 180);
			   imgs[i]=new JLabel(new ImageIcon(image));
			   imgs[i].setToolTipText(vo.getBookname()+"^"+vo.getNo());
			   pan.add(imgs[i]);
			   
			   // 이벤트 등록 => 자바스크립트 
			   imgs[i].addMouseListener(this);
		   }catch(Exception ex) {}
	   }
	   la.setText(curpage+" page / "+totalpage+" pages");
   }
   @Override
   public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	   
	   if(e.getSource()==b5)
	   {
		   if(curpage>1)
		   {
			   curpage--;
			   init();
			   print();
			   //revalidate();
		   }
	   }
	   else if(e.getSource()==b6)
	   {
		   if(curpage<totalpage)
		   {
			   curpage++;
			   init();
			   print();
			   //testrevalidate();
		   }
	   }
   }
   @Override
   public void mouseClicked(MouseEvent e) {
	 //TODO Auto-generated method stub
	  for(int i=0;i<imgs.length;i++)
	  {
		  if(e.getSource()==imgs[i])
		  {
			  if(e.getClickCount()==2)// 더블 클릭 
			  {
				  String gno=
						imgs[i].getToolTipText();
				  gno=gno.substring(gno.indexOf("^")+1);
				  //JOptionPane.showMessageDialog(this, "선택번호:"+gno);
				  
				  cp.card.show(cp, "DETAIL");
				  // 메소드 
				  cp.bdf.print(type, Integer.parseInt(gno));
			  }
		  }
	  }
   }
   @Override
   public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
   }
   @Override
   public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
   }
   @Override
   public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	  for(int i=0;i<imgs.length;i++)
	  {
		  if(e.getSource()==imgs[i])
		  {
			  imgs[i].setBorder(new LineBorder(Color.red,3));
		  }
	  }
   }
   @Override
   public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	   for(int i=0;i<imgs.length;i++)
		  {
			  if(e.getSource()==imgs[i])
			  {
				  imgs[i].setBorder(null);
			  }
		  }
   }
}