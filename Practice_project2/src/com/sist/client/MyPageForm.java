package com.sist.client;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// [TODO] 팀원이 완성하면 아래 VO와 DAO의 import 경로를 확인
// import com.sist.vo.OrdersVO; 
// import com.sist.dao.BookDAO; 

public class MyPageForm extends JPanel implements ActionListener, MouseListener {
    JTable table;
    DefaultTableModel model;
    JButton btnBack; // 목록(홈)으로 돌아가는 버튼
    ControllerPanel cp;

    // [TODO] 팀원의 DAO가 완성되면 아래 주석을 해제하고 연결
    // BookDAO dao = new BookDAO(); 

    public MyPageForm(ControllerPanel cp) {
        this.cp = cp;
        
        // 1. UI 컴포넌트 초기화
        btnBack = new JButton("홈으로");
        
        // 도서 대출 현황에 맞는 컬럼명 설정
        String[] col = {"대출번호", "표지", "도서명", "저자", "대출일", "반납예정일"};
        Object[][] row = new Object[0][6];

        // 2. 테이블 모델 설정 (셀 수정 불가, 이미지 출력 설정 유지)
        model = new DefaultTableModel(row, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 수정 불가
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // 이미지가 들어가는 1번 컬럼(표지) 처리
                if (columnIndex == 1) {
                    return ImageIcon.class;
                }
                return Object.class;
            }
        };

        // 3. 테이블 및 스크롤팬 설정
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 금지
        table.setRowHeight(40); // 도서 표지를 위해 행 높이 조절
        JScrollPane js = new JScrollPane(table);

        // 4. 레이아웃 배치
        setLayout(null);
        btnBack.setBounds(50, 15, 100, 30);
        js.setBounds(50, 60, 800, 450);
        
        add(btnBack);
        add(js);

        // 5. 리스너 등록
        btnBack.addActionListener(this);
        table.addMouseListener(this);
    }

    /*
     * DB에서 대출 데이터를 읽어와서 테이블에 출력하는 메서드
     */
    public void print() {
        // 1. 기존 테이블 데이터 초기화
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        // -----------------------------------------------------------
        // [TODO] 팀원의 DAO와 VO가 준비되면 아래 로직을 작성
        // 1. List<OrdersVO> list = dao.myOrdersListData(cp.myId); 호출
        // 2. for(BorrowVO vo : list) 반복문 실행
        // 3. ImageChange를 이용해 표지 이미지 생성
        // 4. model.addRow(new Object[]{ ... })로 데이터 추가
        // -----------------------------------------------------------
        
        System.out.println("로그인 아이디 [" + cp.myId + "]의 대출 목록을 갱신합니다.");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) {
            // 더블 클릭 시 반납 로직 (예시)
            if (e.getClickCount() == 2) {
                int row = table.getSelectedRow();
                String orderId = model.getValueAt(row, 0).toString();
                
                int opt = JOptionPane.showConfirmDialog(this, 
                    "해당 도서를 반납하시겠습니까?", "반납 확인",
                    JOptionPane.YES_NO_OPTION);
                
                if (opt == JOptionPane.YES_OPTION) {
                    // [TODO] dao.ordersReturn(orderId) 호출 및 print() 재실행
                    JOptionPane.showMessageDialog(this, "반납 처리가 요청되었습니다. (DAO 연결 필요)");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            cp.card.show(cp, "HOME"); // 홈 화면으로 이동
        }
    }

    // 사용하지 않는 MouseListener 메서드들
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}