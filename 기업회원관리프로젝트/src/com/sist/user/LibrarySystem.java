package com.sist.user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class LibrarySystem extends JFrame {

    JTable bookTable, loanTable, feeTable, reportTable;
    DefaultTableModel bookModel, loanModel, feeModel, reportModel;

    public LibrarySystem() {

        setTitle("도서관 관리 시스템");

        JTabbedPane tab = new JTabbedPane();

        tab.add("도서관리", bookPanel());
        tab.add("대출관리", loanPanel());
        tab.add("연체/요금", feePanel());
        tab.add("대출통계", reportPanel());

        add(tab);

        setSize(1100, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // =========================
    // 1. 도서관리
    // =========================
    public JPanel bookPanel() {

        JPanel p = new JPanel(new BorderLayout());

        bookModel = new DefaultTableModel(
                new String[]{"도서ID","제목","저자","수량","상태"}, 0
        );

        bookTable = new JTable(bookModel);

        bookModel.addRow(new Object[]{"B001","자바의 정석","남궁성",5,"대출가능"});
        bookModel.addRow(new Object[]{"B002","Effective Java","Bloch",3,"대출가능"});
        bookModel.addRow(new Object[]{"B003","자료구조","Cormen",2,"대출가능"});

        JButton addBtn = new JButton("도서추가");

        addBtn.addActionListener(e -> {

            String id = JOptionPane.showInputDialog("도서ID");
            String title = JOptionPane.showInputDialog("제목");
            String author = JOptionPane.showInputDialog("저자");
            String qty = JOptionPane.showInputDialog("수량");

            bookModel.addRow(new Object[]{id,title,author,Integer.parseInt(qty),"대출가능"});
        });

        p.add(addBtn, BorderLayout.NORTH);
        p.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        return p;
    }

    // =========================
    // 2. 대출관리
    // =========================
    public JPanel loanPanel() {

        JPanel p = new JPanel(new BorderLayout());

        loanModel = new DefaultTableModel(
                new String[]{"회원ID","도서명","대출일","반납일","상태"}, 0
        );

        loanTable = new JTable(loanModel);

        loanModel.addRow(new Object[]{"user1","자바의 정석", LocalDate.now().toString(),"","대출중"});
        loanModel.addRow(new Object[]{"user2","Effective Java", LocalDate.now().toString(),"","대출중"});

        loanTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int row = loanTable.rowAtPoint(e.getPoint());
                int col = loanTable.columnAtPoint(e.getPoint());

                if (col != 4) return;

                String status = loanTable.getValueAt(row,4).toString();

                // 반납 처리
                if (status.equals("대출중")) {

                    loanTable.setValueAt(LocalDate.now().toString(), row, 3);
                    loanTable.setValueAt("반납완료", row, 4);
                }
            }
        });

        p.add(new JScrollPane(loanTable), BorderLayout.CENTER);

        return p;
    }

    // =========================
    // 3. 연체 / 요금
    // =========================
    public JPanel feePanel() {

        JPanel p = new JPanel(new BorderLayout());

        feeModel = new DefaultTableModel(
                new String[]{"회원ID","연체일수","연체료"}, 0
        );

        feeTable = new JTable(feeModel);

        JButton calc = new JButton("연체 계산");

        calc.addActionListener(e -> calculateFee());

        p.add(calc, BorderLayout.NORTH);
        p.add(new JScrollPane(feeTable), BorderLayout.CENTER);

        return p;
    }

    // =========================
    // 4. 통계
    // =========================
    public JPanel reportPanel() {

        JPanel p = new JPanel(new BorderLayout());

        reportModel = new DefaultTableModel(
                new String[]{"회원ID","대출수","반납수"}, 0
        );

        reportTable = new JTable(reportModel);

        JButton btn = new JButton("통계 생성");

        btn.addActionListener(e -> makeReport());

        p.add(btn, BorderLayout.NORTH);
        p.add(new JScrollPane(reportTable), BorderLayout.CENTER);

        return p;
    }

    // =========================
    // 연체료 계산
    // =========================
    public void calculateFee() {

        feeModel.setRowCount(0);

        for (int i = 0; i < loanModel.getRowCount(); i++) {

            String user = loanModel.getValueAt(i,0).toString();
            String status = loanModel.getValueAt(i,4).toString();

            int lateDay = status.equals("대출중") ? 3 : 0; // 예시

            int fee = lateDay * 500;

            feeModel.addRow(new Object[]{user, lateDay, fee});
        }
    }

    // =========================
    // 통계 생성
    // =========================
    public void makeReport() {

        reportModel.setRowCount(0);

        for (int i = 0; i < loanModel.getRowCount(); i++) {

            String user = loanModel.getValueAt(i,0).toString();
            String status = loanModel.getValueAt(i,4).toString();

            int loan = 1;
            int returnCnt = status.equals("반납완료") ? 1 : 0;

            reportModel.addRow(new Object[]{user, loan, returnCnt});
        }
    }

    // =========================
    public static void main(String[] args) {
    	try
        {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }catch(Exception ex) {}
        new LibrarySystem();
    }
}