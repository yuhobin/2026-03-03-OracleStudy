package com.sist.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import java.util.List;
import javax.swing.table.*;
import com.sist.dao.BooksDAO;
import com.sist.vo.*;
import com.sist.client.Login;
import com.sist.commons.ImageChange;
public class OrderControlForm extends JPanel implements MouseListener {
	JTable table;
	DefaultTableModel model;
	ControllerPanel cp;
	String myId=Login.myId;
	BooksDAO dao=new BooksDAO();
	public OrderControlForm(ControllerPanel cp) {
		this.cp=cp;
		String[] col= {"대출번호","표지","제목","저자","ISBN","대출날짜","반납기한","상태"};
		String[][] row=new String[0][8];
		model=new DefaultTableModel(row,col) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if(getRowCount()>0) {
					Object obj=getValueAt(0, columnIndex);
					if(obj!=null) {
						return obj.getClass();
					}
				}
				return Object.class;
			}
		};
		table=new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(35);
		table.getTableHeader().setResizingAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);  // 대출번호
		table.getColumnModel().getColumn(1).setPreferredWidth(50);  // 표지
		table.getColumnModel().getColumn(2).setPreferredWidth(250); // 제목
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // 저자
		table.getColumnModel().getColumn(4).setPreferredWidth(120); // ISBN
		table.getColumnModel().getColumn(5).setPreferredWidth(100); // 대출날짜
		table.getColumnModel().getColumn(6).setPreferredWidth(100); // 반납기한
		table.getColumnModel().getColumn(7).setPreferredWidth(80); // 상태
		table.addMouseListener(this); // 더블 클릭시 반납버튼
		JScrollPane js=new JScrollPane(table);
		setLayout(new BorderLayout());
		js.setBounds(50, 50, 800, 450);

		add("Center",js);
	}
	
	
	
	public void print() {
		for(int i=model.getRowCount()-1;i>=0;i--) {
			model.removeRow(i);
		}
		
		List<BooksVO> list=dao.orderBooksData(myId);
		for(BooksVO vo:list) {
			try {
				URI uri=new URI(vo.getPoster());
				URL url=uri.toURL();
				Image image=ImageChange.getImage(new ImageIcon(url), 30, 30);
				Object[] data= {
					vo.getOvo().getOrderid(),
					new ImageIcon(image),
					vo.getBookname(),
					vo.getAuthor(),
					vo.getIsbn(),
					vo.getOvo().getOrdateS(),
					vo.getOvo().getDuedateS(),
					vo.getOvo().getStatus()
				};
				model.addRow(data);
			}catch(Exception ex) {}
		}
		
	}


// 마이페이지에서 대출목록 도서 더블클릭시 반납 코드 주석처리
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == table) {
	        // 1. 더블 클릭 확인
	        if (e.getClickCount() == 2) {
	            // 2. 선택된 행 번호 가져오기
	            int row = table.getSelectedRow();
	            
	            // 3. 0번째 칸에서 대출 번호 추출
	            String orderid = model.getValueAt(row, 0).toString();
	            
	            // 4. 반납 확인창 띄우기 (이미지 구조와 동일)
	            int a = JOptionPane.showConfirmDialog(this, 
	                    "반납하시겠습니까?", "반납", 
	                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	            
	            // 5. '예'를 눌렀을 경우 처리
	            if (a == JOptionPane.YES_OPTION) {
	                // 데이터 연동: DAO의 반납 메서드 호출
	                dao.OrderReturn(Integer.parseInt(orderid)); 
	                
	                // 6. 목록 새로고침 (이미지 구조와 동일)
	                print(); 
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
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}