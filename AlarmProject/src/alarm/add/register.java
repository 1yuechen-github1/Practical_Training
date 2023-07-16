package alarm.add;
import alarm.add.land;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alarm.ui.AlarmUI;

public class register {
	public register() {
		JFrame jf2 = new JFrame();
		jf2.setTitle("注册界面");
		jf2.setSize(400, 400);
		jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//FlowLayout flow = new FlowLayout();
		jf2.setLayout(null);
		JLabel nameJla = new JLabel("用户名：");
		nameJla.setBounds(70,80,60,50);
		JLabel pwdJla = new JLabel("密码：");
		pwdJla.setBounds(70,140,60,10);
		JButton btn = new JButton("提交");
		btn.setBounds(50,240,60,30);
		JButton btn1 =new JButton("返回");
		btn1.setBounds(200,240,60,30);
		JLabel jl = new JLabel("重新输入密码：");
		jl.setBounds(70,150,90,80);
		
		JTextField textIn = new JTextField();
		textIn.setBounds(110,85,140,30);
		JPasswordField pwdIn = new JPasswordField();
		pwdIn.setBounds(110,130,140,30);
		JPasswordField jf1 = new JPasswordField();
		jf1.setBounds(110,200,140,30);
		
		jf2.add(btn);
		jf2.add(btn1);
		jf2.add(nameJla);
		jf2.add(textIn);
		jf2.add(pwdJla);
		jf2.add(pwdIn);
		jf2.add(jl);
		jf2.add(jf1);
		ImageIcon img = new ImageIcon("login.png");
		JLabel imgjla = new JLabel();
		imgjla.setIcon(img);
		jf1.add(imgjla);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				jf2.setVisible(false);
				new land ();	
			}
		});
		btn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String username = textIn.getText();
		        String pwd = pwdIn.getText();
		        String pwd1 = jf1.getText();
		        System.out.println(username + " " + pwd);			     
		        	if(pwd.equals(pwd1) && (!username.isEmpty()) && ((!pwd.isEmpty()))) {
			            JOptionPane.showMessageDialog(null, "注册成功");
			            //实现页面跳到登录主界面
			            jf2.setVisible(false);
			            new land();
			            
			            //实现将注册数据提交到数据库
			            new DAOuserdata();
			            
			            try {
							DAOuserdata.addFile(username,pwd);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        } else {
			            JOptionPane.showMessageDialog(null, "不符合规范，请重新输入");
			        }
		    }
		});
		jf2.setVisible (true);

	}
	public static void main(String[] args) {
			new register();
			// TODO Auto-generated method stub
	  }
	}


