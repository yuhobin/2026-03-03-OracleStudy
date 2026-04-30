package com.sist.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
public class Login extends JFrame implements ActionListener{
	JLabel la1,la2;
    JTextField tf;
    JPasswordField pf;
    JButton b1,b2,b3;
    static String myId;
   
    public Login()
    {
    	
    	la1=new JLabel("ID");
        la2=new JLabel("Password");
        tf=new JTextField();
        pf=new JPasswordField();
        b1=new JButton("로그인");
        b2=new JButton("취소");
        b3=new JButton("회원가입");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        // 프레임 어디서든 엔터를 누르면 b1(로그인) 버튼이 눌린 것으로 간주함
        this.getRootPane().setDefaultButton(b1);
        
        // 배치 => 실행과 동시에 실행 명령 => 초기화 => 생성자
        setLayout(null);
        la1.setBounds(10, 15, 80, 30);
        tf.setBounds(95, 15, 200, 30);
        add(la1);add(tf);
        
        la2.setBounds(10, 50, 80, 30);
        pf.setBounds(95, 50, 200, 30);
        add(la2);add(pf);
        
        JPanel p=new JPanel();
        p.setOpaque(false);
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.setBounds(10, 90, 285, 35);
        add(p);
        
        setBounds(400,300,330, 170);
        setVisible(true);// 화면 출력
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == b1) { // '로그인' 버튼을 눌렀을 때
            
            String id = tf.getText();
            String pwd = String.valueOf(pf.getPassword());
            
            
            MemberDAO dao = new MemberDAO(); 
            MemberVO vo = dao.isLogin(id, pwd);
            
            if(vo.getMsg().equals("NOID")) {
                JOptionPane.showMessageDialog(this, "아이디가 존재하지 않습니다.");
                tf.setText("");
                tf.requestFocus();
            } else if(vo.getMsg().equals("NOPWD")) {
                JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.");
                pf.setText("");
                pf.requestFocus();
            } else {
                // 로그인 성공! ("OK")
            	myId=id;
                JOptionPane.showMessageDialog(this, vo.getId() + "님 환영합니다.");	
                UserMainForm.bLogin=true;
                UserMainForm.isAdmin=vo.getIsadmin();
                UserMainForm.myId=id;
                new UserMainForm(); // 메인 창 띄우기
                dispose();          // 현재 로그인 창 끄기
                // 메인 화면을 새로 만들어서 띄웁니다.
                
                
                // 현재 떠 있는 로그인 창은 메모리에서 지워버립니다(닫기).
               
            }
            
        } else if(e.getSource() == b2) { // '취소' 버튼을 눌렀을 때
            System.exit(0); // 프로그램 완전 종료
        }
        else if(e.getSource() == b3) { // '회원가입' 버튼을 눌렀을 때
        	new Join(); // 회원가입 창 띄우기
        	
        }
	}
}