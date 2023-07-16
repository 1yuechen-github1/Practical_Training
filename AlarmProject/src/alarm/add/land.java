package alarm.add;
import alarm.add.DAOuserdata;
/*设置登录界面
 */
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alarm.ui.AlarmUI;

import alarm.add.register;

public class land {
	public  land() {
		JFrame jf = new JFrame();
		jf.setTitle("登录界面");
		jf.setSize(400, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//FlowLayout flow = new FlowLayout();
		jf.setLayout(null);
		JLabel nameJla = new JLabel("账号：");
		nameJla.setBounds(70,80,60,50);
		JLabel pwdJla = new JLabel("密码：");
		pwdJla.setBounds(70,140,60,10);
		JButton btn = new JButton("登录");
		btn.setBounds(50,240,60,30);
		JButton btn1 =new JButton("注册");
		btn1.setBounds(200,240,60,30);
		
		JTextField textIn = new JTextField();
		textIn.setBounds(110,85,140,30);
		JPasswordField pwdIn = new JPasswordField();
		pwdIn.setBounds(110,130,140,30);
		jf.add(btn);
		jf.add(btn1);
		jf.add(nameJla);
		jf.add(textIn);
		jf.add(pwdJla);
		jf.add(pwdIn);
		ImageIcon img = new ImageIcon("login.png");
		JLabel imgjla = new JLabel();
		imgjla.setIcon(img);
		jf.add(imgjla);
		//创建DAO类使用里面方法
		DAOuserdata d=new DAOuserdata();
		
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				jf.setVisible(false);
				new register();
				
			}
		});
		btn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String username = textIn.getText();
		        String pwd = pwdIn.getText();
		        System.out.println(username + " " + pwd);
		        try {
		        	List<TFilees1> file=d.getFile(username,pwd);
		        	if((!file.isEmpty())) {
		        		jf.setVisible(false);
		        		 new AlarmUI();
		        	}else {
		        		JOptionPane.showMessageDialog(null, "账号或者密码错误");
		        	}
//					if(username.equals() && pwd.equals(DAOuserdata.rs.getString("passward"))) {
//					    JOptionPane.showMessageDialog(null, "登录成功");
//					    //实现页面跳到闹钟主界面
//					    jf.setVisible(false);
//					    new AlarmUI();
//					} else {
//					    JOptionPane.showMessageDialog(null, "用户名或密码错误");
//					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		jf.setVisible (true);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new land();
	}

}
