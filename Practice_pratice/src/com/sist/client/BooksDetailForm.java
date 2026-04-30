package com.sist.client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;

import javax.swing.*;

import com.sist.commons.ImageChange;
import com.sist.dao.BooksDAO;
import com.sist.client.UserMainForm;
import com.sist.vo.BooksVO;
import com.sist.vo.BooksVO;

public class BooksDetailForm extends JPanel
implements ActionListener
{
	ControllerPanel cp; // 화면 이동 cp.card.show()
	// => HTML에서 작업 => <a>
	BooksDAO dao=new BooksDAO();
	JButton b1=new JButton("목록");
	int type=0;
	int gno=0;
	JLabel poster;
    JLabel nameLa, authorLa, contentLa, isbnLa, priceLa;
    JLabel name,author,content,isbn,price;
    JButton b2;
    JComboBox<Integer> box;
    public BooksDetailForm(ControllerPanel cp)
    {
    	 this.cp=cp;
    	 setLayout(null);
    	 poster=new JLabel("");
         poster.setBounds(20, 20, 300, 200);
         add(poster);
        	 
         nameLa=new JLabel("도서명");
         nameLa.setBackground(Color.gray);
         nameLa.setOpaque(true);
         
         name=new JLabel();
         nameLa.setBounds(330, 20, 80, 30);
         name.setBounds(415, 20, 250, 30);
         add(nameLa);add(name);
         
         authorLa = new JLabel("저자");
         authorLa.setBackground(Color.gray);
         authorLa.setOpaque(true);

         author = new JLabel();
         authorLa.setBounds(330, 55, 80, 30);
         author.setBounds(415, 55, 250, 30);
         add(authorLa); add(author);
         
      // 내용
         contentLa = new JLabel("설명");
         contentLa.setBackground(Color.gray);
         contentLa.setOpaque(true);

         content = new JLabel();
         contentLa.setBounds(330, 90, 80, 30);
         content.setBounds(415, 90, 350, 30);
         add(contentLa); add(content);
        	 
      // ISBN
         isbnLa = new JLabel("ISBN");
         isbnLa.setBackground(Color.gray);
         isbnLa.setOpaque(true);

         isbn = new JLabel();
         isbnLa.setBounds(330, 125, 80, 30);
         isbn.setBounds(415, 125, 250, 30);
         add(isbnLa); add(isbn);
         
         // 가격
         priceLa = new JLabel("가격");
         priceLa.setBackground(Color.gray);
         priceLa.setOpaque(true);

         price = new JLabel();
         priceLa.setBounds(330, 160, 80, 30);
         price.setBounds(415, 160, 250, 30);
         add(priceLa); add(price);
         box=new JComboBox<Integer>();
         box.addItem(1);
         box.addItem(2);
         box.addItem(3);
         box.addItem(4);
         box.addItem(5);
         b1=new JButton("장바구니 추가");
         b1.setEnabled(false);
         b2=new JButton("목록");
        	 
        
         JPanel p=new JPanel();
         p.add(box);p.add(b1);p.add(b2);
         p.setBounds(330, 200, 435, 35);
         add(p);
         
         b2.addActionListener(this);
    }
    public void print(int type,int gno)
    {
    	this.type=type;
    	this.gno=gno;
    	BooksVO vo=dao.booksDetailData(type, gno);
    	// 데이터 주입 
    	 name.setText(vo.getBookname());
    	    author.setText(vo.getAuthor());
    	    content.setText(vo.getContent());
    	    isbn.setText(vo.getIsbn());
    	    price.setText(vo.getPrice());
    	try
    	{
    		URI uri=new URI(vo.getPoster());
    		URL url=uri.toURL();
    		Image img=
    		  ImageChange.getImage(new ImageIcon(url),
    				   300, 200);
    		poster.setIcon(new ImageIcon(img));
    	}catch(Exception ex) {}
    	
    	b1.setEnabled(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)
		{
			cp.card.show(cp, "HOME");
		}
	}
	
}