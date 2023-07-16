package com.alarm.ui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
/**
* 版本说明
*
*/
public class AboutUI extends JFrame{
	private JLabel appTitleLabel; //应用程序名称
	private JLabel appVersionLabel; //版本号
	private JLabel appHomepageLabel; //HomePage
	private JLabel appDescLabel; //说明
	private JLabel imageLabel; //图片
	AlarmUI alarmUI;
	JPanel panel;
	
	public AboutUI(AlarmUI alarmUI) {
		this.alarmUI = alarmUI;
		panel = new JPanel();
		panel.setLayout(null);
		
		appTitleLabel = new JLabel("应用程序名称：闹钟工具软件");
		appTitleLabel.setBounds(20, 30, 200, 30);
		appVersionLabel = new JLabel("版本号：1.0");
		appVersionLabel.setBounds(20, 60, 200, 30);
		appHomepageLabel = new JLabel("http://www.gdqy.edu.cn");
		appHomepageLabel.setBounds(20, 90, 200, 30);
		appDescLabel = new JLabel("这是一个小应用程序--闹钟");
		appDescLabel.setBounds(20, 120, 200, 30);
		ImageIcon icon = new ImageIcon(AboutUI.class.getResource("/images/bg2.jpg"));
		imageLabel = new JLabel();
		imageLabel.setBounds(220, 30, 100, 200);
		Border border = LineBorder.createGrayLineBorder(); //创建边框对象
		imageLabel.setBorder(border); //给label加上边框
		imageLabel.setIcon(icon);
		
		panel.add(appTitleLabel);
		panel.add(appVersionLabel);
		panel.add(appHomepageLabel);
		panel.add(appDescLabel);
		panel.add(imageLabel);
		this.add(panel);
		this.setPreferredSize(new Dimension(350, 300));
		//窗体相对于主界面窗体的位置
		this.setLocation(this.alarmUI.getPointX()+100, this.alarmUI.getPointY()+100);
		//设置为JFrame.HIDE_ON_CLOSE，即关闭窗体时是隐藏而不是退出程序
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
}
