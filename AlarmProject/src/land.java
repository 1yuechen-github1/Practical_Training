/*设置登录界面
 */
import java.awt.event.*;
import java.awt.FlowLayout;

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
	public void land() {
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		        if(username.equals("admin") && pwd.equals("admin123")) {
		            JOptionPane.showMessageDialog(null, "登录成功");
		            //实现页面跳到闹钟主界面
		            jf.setVisible(false);
		            new AlarmUI();
		        } else {
		            JOptionPane.showMessageDialog(null, "用户名或密码错误");
		        }
		    }
		});
		jf.setVisible (true);
	}

}
