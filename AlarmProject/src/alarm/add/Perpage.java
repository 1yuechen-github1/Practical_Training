package alarm.add;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.alarm.ui.AlarmUI;
/**
* 修改个人主页信息
*
*/
public class Perpage extends JFrame{
	private JLabel appTitleLabel,appTitleLabel2; //应用程序名称
	private JLabel appVersionLabel; //版本号
	private JLabel appHomepageLabel; //HomePage
	private JLabel appDescLabel; //说明
	private JLabel imageLabel; //图片
	AlarmUI alarmUI;
	JPanel panel,panel2;
	JButton button,button2;
	private JTextField txt;//用户名
	private JPasswordField pwd1;//修改用户密码
	private JPasswordField pwd2;//确认用户密码
	
	public Perpage(AlarmUI alarmUI) {
		this.alarmUI = alarmUI;
		panel = new JPanel();
		panel.setLayout(null);
		panel2 = new JPanel();
		panel2.setLayout(null);
		
		appTitleLabel = new JLabel("用户姓名");
		appTitleLabel.setBounds(20, 20, 200, 30);
		appTitleLabel2 = new JLabel("用户姓名");
		appTitleLabel2.setBounds(20, 20, 200, 30);
		//appVersionLabel = new JLabel("版本号：1.0");
		txt = new JTextField();//
		txt.setBounds(20,50,200,30);
		pwd1 = new JPasswordField();
		pwd1.setBounds(20, 110, 200, 30);
		pwd2 = new JPasswordField();
		pwd2.setBounds(20, 140, 200, 30);
		appHomepageLabel = new JLabel("用户密码");
		appHomepageLabel.setBounds(20, 80, 200, 30);
		//appDescLabel = new JLabel("这是一个小应用程序--闹钟");
		//appDescLabel.setBounds(20, 120, 200, 30);
		button = new JButton("修改信息");
		button.setBounds(20, 180, 150, 30);
		ImageIcon icon = new ImageIcon(Perpage.class.getResource("/images/bg2.jpg"));
		imageLabel = new JLabel();
		imageLabel.setBounds(220, 30, 100, 200);
		Border border = LineBorder.createGrayLineBorder(); //创建边框对象
		imageLabel.setBorder(border); //给label加上边框
		imageLabel.setIcon(icon);
		
		panel.add(appTitleLabel);
		//panel.add(appVersionLabel);
		panel.add(appHomepageLabel);
		//panel.add(appDescLabel);
		panel.add(txt);
		panel.add(button);
		panel.add(imageLabel);
		panel.add(pwd1);
		panel.add(pwd2);
		this.add(panel);
		this.setPreferredSize(new Dimension(350, 300));
		//窗体相对于主界面窗体的位置
		this.setLocation(this.alarmUI.getPointX()+100, this.alarmUI.getPointY()+100);
		//设置为JFrame.HIDE_ON_CLOSE，即关闭窗体时是隐藏而不是退出程序
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		DAOuserdata d=new DAOuserdata();

		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//打开个人主页
				String username3 = txt.getText();
		        String pwd3 = pwd2.getText();
		        String pwd4 = pwd1.getText();
				if(pwd2.getText() == null && pwd1.getText() == null) {
					button.setText("返回个人信息");
					
				}else if(!pwd4.equals(pwd3) && (txt != null)){
					try {
		        		List<TFilees1> file=d.updateFile(username3,pwd3);
		        		if(file != null)
		        		{
			        		JOptionPane.showMessageDialog(null, "修改成功");
			        		
		        		}
		        	}catch(HeadlessException e1) {}
				}
		        System.out.println("用户名:"+username3+ "\r\n原先密码" + pwd4+"\r\n修改后的密码"+pwd3);
//		        if(!pwd4.equals(pwd3) && (txt != null))
//		        {
//		        	
//		        }
			}			
		});
		
		
	}
}
