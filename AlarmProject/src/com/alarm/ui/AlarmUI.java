package com.alarm.ui;
import alarm.add.Perpage;
import alarm.add.Perpage1;
import alarm.add.RingPlayer;
import com.alarm.DAO.DAOTFiles;
import com.alarm.DAO.TFiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.management.timer.Timer;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.io.IOException;

import com.alarm.DAO.DAOTFiles;
import com.alarm.log.AlarmLogger;
import com.alarm.utility.TranslateFiles;

import alarm.add.RingPlayer;

public class AlarmUI extends JFrame {
	private JPanel alarmPanel_0; //主面板
	private JTabbedPane tbPane;	 //tab面板
	private AlarmPanel alarmPanel_1; //闹钟1面板，声明AlarmPanel类
	private JButton btnStart_1,btnStart_2;  //开启闹钟按钮
	private JButton btnListen_1,btnListen_2; //试听按钮
	private JLabel lblDateTip,lblDateTip2;   //当前日期时间标签
	private JLabel lblDateTime,lblDateTime2;  //当前日期时间信息
	private JLabel lblMsg,lblMsg2;	     //提示标签
	private JLabel lblMsgContent,lblMsgContent2;//提示信息
	private JLabel lblRingsetup_1,lblRingsetup_2;//提醒闹钟
	private JLabel lblRing_1,lblRing_2;    //闹钟铃声标签
	private JLabel lblRepeat_1,lblRepeat_2;  //重复提醒标签
	private JLabel lblHour_1,lblHour_2;    //时标签
	private JLabel lblMinute_1,lblMinute_2;  //分标签
	private JLabel lblSecond_1,lblSecond_2;  //秒标签
	private JComboBox ckbHour_1,ckbHour_2; //时设置组合框
	private JComboBox ckbMinute_1,ckbMinute_2;//分设置组合框
	private JComboBox ckbSecond_1,ckbSecond_2;//秒设置组合框
	private JComboBox ckbFile_1,ckbFile_2;  //铃声选择组合框
	private JLabel lblTip_1,lblTip_2;      //提示文字标签
	private JTextField txtTip_1,txtTip_2;  //提示文字信息
	private JRadioButton rbRepeat_1,rbRepeat_2;//每日重复提醒单选按钮
	private JRadioButton rbNorepeat_1,rbNorepeat_2;//不重复提醒单选按钮
		
	private AlarmPanel alarmPanel_2;  //闹钟2面板，声明AlarmPanel类
	private AlarmPanel alarmPanel_3;  //用户个人主页资料
	
	private JMenuBar menuBar; //菜单栏
	private JMenu menu_file;  //文件菜单
	private JMenu menu_tools; //工具菜单
	private JMenu menu_about; //帮助菜单
	private JMenuItem menuItem_ring; //上传铃声文件菜单项
	private JMenuItem menuItem_down_rings; 
	private JMenuItem menuItem_about; //关于菜单项
	private JMenuItem menuItem_exit; //退出菜单项
	private JMenuItem menuItem_help; //帮助菜单项
	private JMenuItem menuItem_ppage;//个人主页
	
	private Image image_1; //闹钟1界面背景图片
	private Image image_2; //闹钟2界面背景图片
	
	private int hour;   //保存时
	private int minute; //保存分
	private int second; //保存秒
	RingPlayer ringTh1;
	int ringIndex;
	
	private Logger alarmLogger = AlarmLogger.getLogger(AlarmUI.class.getName());
	
	//铃声文件下载路径
	String ringsDownloadPath;
	//铃声文件路径
	private ArrayList<String> alarmPaths;
	private boolean isDown;
	//保存铃声选项集合
	private Vector<String> rings;
	
	private String ring_str; //保存选择的铃声
	
	//任务十一
	private int intervalSecond;
	public int getIntervalSecond() {
		return intervalSecond;
	}
	public void setIntervalSecond(int intervalSecond) {
		this.intervalSecond = intervalSecond;
	}
	
	
	@SuppressWarnings("unchecked")
	public AlarmUI(){
		//铃声文件下载路径
		ringsDownloadPath = "RingsDownload";
		//初始化铃声文件路径
		initFilePath();
		
		//创建图像对象
		try {
			image_1 = new ImageIcon(AlarmUI.class.getResource("/images/bg1.jpg")).getImage();
			image_2 = new ImageIcon(AlarmUI.class.getResource("/images/bg2.jpg")).getImage();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			alarmLogger.log(Level.SEVERE, "读文件错误", e);
		}
		//创建字体对象
		Font font_1 = new Font("宋体", Font.PLAIN, 24);
		Font font_2 = new Font("宋体", Font.PLAIN, 14);
		Font font_3 = new Font("宋体", Font.PLAIN, 18);
		//当前时间、tab和最下方的提示部分的x,y坐标是相对于jframe的
		lblDateTip = new JLabel("当前时间:");
		lblDateTip.setFont(font_1);
		lblDateTip.setBounds(40, 5, 150, 60); //设置lblDateTip标签的位置
		//实现闹钟标签动态显示
		Date date= new Date();
		lblDateTime = new JLabel();
		//new Date().toString()
		Timer timer =new Timer(1000, new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
		        Date date = new Date();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String currentTime = sdf.format(date);
		        lblDateTime.setText(currentTime);
		    }
		});
		timer.start();
		//----动态标签结束----
		lblDateTime.setFont(font_1);
		lblDateTime.setBounds(180, 5, 350, 60); //设置lblDateTime标签的位置

		
		//闹钟1面板实例化，设置大小
		alarmPanel_1 = new AlarmPanel(image_1);
		alarmPanel_1.setPreferredSize(new Dimension(500, 400));
		alarmPanel_1.setLayout(null); //设置tabpane 为null布局
		
		//闹钟1面板实例化，设置大小
		alarmPanel_2 = new AlarmPanel(image_2);
		alarmPanel_2.setPreferredSize(new Dimension(500, 400));
		alarmPanel_2.setLayout(null); //设置tabpane 为null布局

		//设置提醒时间：时，分，秒  
		lblRingsetup_1 = new JLabel("提醒时间");
		lblRingsetup_2 = new JLabel("提醒时间");
		lblRingsetup_1.setFont(font_2);
		lblRingsetup_2.setFont(font_2);
		lblRingsetup_1.setBounds(40, 10, 100, 30); //设置lblRingsetup_1标签的位置（x坐标，y坐标，组件长度，组件高度）
		lblRingsetup_2.setBounds(40, 10, 100, 30); //设置lblRingsetup_2标签的位置（x坐标，y坐标，组件长度，组件高度）

		String[] h = new String[]{"关闭", "00", "01", "02", "03", "04", "05", "06",
				"07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23" };
		Vector<String> hours = new Vector<String>();
		for(int i = 0; i < h.length; i++){
			hours.add(h[i]);
		}
		ckbHour_1 = new JComboBox(hours);
		ckbHour_2 = new JComboBox(hours);
		ckbHour_1.setBounds(120, 10, 60, 30); //设置ckbHour_1标签的位置
		ckbHour_2.setBounds(120, 10, 60, 30); //设置ckbHour_1标签的位置
		ckbHour_1.setFont(font_2);
		ckbHour_2.setFont(font_2);

		
		ckbHour_1.addItemListener(new ItemListener(){  //实现监听器
			//选择时，则分选项可用
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbHour_1.getSelectedIndex() > 0){
					ckbMinute_1.setEnabled(true);
					hour = Integer.parseInt((String)ckbHour_1.getSelectedItem()); //保存时
				}
			}			
		});
		
		ckbHour_2.addItemListener(new ItemListener(){  //实现监听器
			//选择时，则分选项可用
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbHour_2.getSelectedIndex() > 0){
					ckbMinute_2.setEnabled(true);
					hour = Integer.parseInt((String)ckbHour_2.getSelectedItem()); //保存时
				}
			}			
		});
		
		lblHour_1 = new JLabel("时");
		lblHour_1.setFont(font_2);
		lblHour_1.setBounds(185, 10, 30, 30); //设置lblHour_1标签的位置
		
		lblHour_2 = new JLabel("时");
		lblHour_2.setFont(font_2);
		lblHour_2.setBounds(185, 10, 30, 30); //设置lblHour_2标签的位置
		
		String[] m = new String[]{"关闭", "00", "01", "02", "03", "04", "05", "06",
				"07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31", "32", "33",
				"34", "35", "36", "37", "38", "39", "40", "41", "42",
				"43", "44", "45", "46", "47", "48", "49", "50", "51",
				"52", "53", "54", "55", "56", "57", "58", "59"}; 
		Vector<String> minutes = new Vector<String>();
		for(int i = 0; i < m.length; i++){
			minutes.add(m[i]);
		}
		ckbMinute_1 = new JComboBox(minutes);
		ckbMinute_1.setFont(font_2);
		ckbMinute_1.setBounds(210, 10, 60, 30); //设置ckbMinute_1标签的位置
		ckbMinute_1.setEnabled(false);
		ckbMinute_2 = new JComboBox(minutes);
		ckbMinute_2.setFont(font_2);
		ckbMinute_2.setBounds(210, 10, 60, 30); //设置ckbMinute_1标签的位置
		ckbMinute_2.setEnabled(false);
		
		ckbMinute_1.addItemListener(new ItemListener(){ //实现监听器
			//选择分，则秒选项可用
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbMinute_1.getSelectedIndex() > 0){
					ckbSecond_1.setEnabled(true);
					minute = Integer.parseInt((String)ckbMinute_1.getSelectedItem());
				}
			}			
		});
		
		ckbMinute_2.addItemListener(new ItemListener(){ //实现监听器
			//选择分，则秒选项可用
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbMinute_2.getSelectedIndex() > 0){
					ckbSecond_2.setEnabled(true);
					minute = Integer.parseInt((String)ckbMinute_2.getSelectedItem());
				}
			}			
		});
		
		lblMinute_1 = new JLabel("分");
		lblMinute_1.setFont(font_2);
		lblMinute_1.setBounds(275, 10, 30, 30); //设置lblMinute_1标签的位置
		lblMinute_2 = new JLabel("分");
		lblMinute_2.setFont(font_2);
		lblMinute_2.setBounds(275, 10, 30, 30); //设置lblMinute_1标签的位置
		
		
		Vector<String> seconds = new Vector<String>();
		for(int i = 0; i < m.length; i++){
			seconds.add(m[i]);
		}
		ckbSecond_1 = new JComboBox(seconds);
		ckbSecond_1.setFont(font_2);
		ckbSecond_1.setBounds(300, 10, 60, 30); //设置ckbSecond_1标签的位置
		ckbSecond_1.setEnabled(false);
		ckbSecond_2 = new JComboBox(seconds);
		ckbSecond_2.setFont(font_2);
		ckbSecond_2.setBounds(300, 10, 60, 30); //设置ckbSecond_1标签的位置
		ckbSecond_2.setEnabled(false);
		
		ckbSecond_1.addItemListener(new ItemListener(){ //实现监听器
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbSecond_1.getSelectedIndex() > 0){
					second = Integer.parseInt((String)ckbSecond_1.getSelectedItem());
				}
			}			
		});
		
		ckbSecond_2.addItemListener(new ItemListener(){ //实现监听器
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// TODO Auto-generated method stub
				if(evt.getStateChange() == ItemEvent.SELECTED && ckbSecond_2.getSelectedIndex() > 0){
					second = Integer.parseInt((String)ckbSecond_2.getSelectedItem());
				}
			}			
		});
		
		lblSecond_1 = new JLabel("秒");
		lblSecond_1.setFont(font_2);
		lblSecond_1.setBounds(365, 10, 30, 30); //设置lblSecond_1标签的位置
		lblSecond_2 = new JLabel("秒");
		lblSecond_2.setFont(font_2);
		lblSecond_2.setBounds(365, 10, 30, 30); //设置lblSecond_1标签的位置
		
		//设置铃声
		lblRing_1 = new JLabel("闹钟铃声");
		lblRing_1.setFont(font_2);
		lblRing_1.setBounds(40, 50, 100, 30); //设置lblRing_1标签的位置
		lblRing_2 = new JLabel("闹钟铃声");
		lblRing_2.setFont(font_2);
		lblRing_2.setBounds(40, 50, 100, 30); //设置lblRing_1标签的位置
		
		//DAOTFiles f = new DAOTFiles();
		Vector<String> rings = new Vector<String>();
		String[] r = new String[11]; 
		//List<TFiles> rings = new ArrayList<TFiles>();
		
		for(int i = 0; i < r.length; i++){
			if(i == 0) r[i] = "请选择铃声";
//			//实现铃声与已经下载的铃声对应
			else r[i] = "alarm" + (i - 1)+".wav";
			//else r[i] =
		}
//		for(int i = 0; i < r.length; i++){
//			rings.addAll(alarmPaths);
//		}
		rings.addAll(alarmPaths);

		//DAOTFiles f = new DAOTFiles();
		//rings.addAll(f.getFile2());
		ckbFile_1 = new JComboBox(rings);
		ckbFile_1.setFont(font_2);
		ckbFile_1.setBounds(120, 50, 100, 30); //设置ckbFile_1标签的位置
		ckbFile_2 = new JComboBox(rings);
		ckbFile_2.setFont(font_2);
		ckbFile_2.setBounds(120, 50, 100, 30); //设置ckbFile_2标签的位置
		//事件监听器，保存铃声
		
		btnListen_1 = new JButton("试听");
		btnListen_1.setBounds(225, 50, 70, 30); //设置btnListen_1标签的位置
		btnListen_2 = new JButton("试听");
		btnListen_2.setBounds(225, 50, 70, 30); //设置btnListen_2标签的位置
		
		btnListen_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(ckbFile_1.getItemCount()<=1) {  //铃声数量
					JOptionPane.showMessageDialog(null, "暂无铃声，无法试听", "提示信息", JOptionPane.OK_OPTION);
					return;
				}
				if("试听".equals(btnListen_1.getText())){
					//播放选中的铃声。ringIndex：铃声序号
					int ringIndex = ckbFile_1.getSelectedIndex()-1;
					System.out.println(ringIndex);
					//音乐播放
					if(ringIndex == 0) {
						JOptionPane.showMessageDialog(null, "请选择铃声", "提示信息", JOptionPane.OK_OPTION);
					}else{
						//音乐播放（待实现）
				      ringTh1 =executeRing(alarmPaths.get(ringIndex));
						
					}
					//将按钮显示更改为“停止”
					btnListen_1.setText("停止");					
				}else if("停止".equals(btnListen_1.getText())){
					//如果铃声正在播放，则停止闹钟铃声播放（待实现）
					if(ringTh1 != null) {
						if(ringTh1.isAlive()) 
							ringTh1.close();//运行状态停止
					}
					if(ringTh1 != null)
					
					btnListen_1.setText("试听");				
					//重置时，分，秒选项
					ckbHour_1.setSelectedIndex(0);
					ckbMinute_1.setEnabled(false);
					ckbSecond_1.setEnabled(false);
					//下拉列表可用
					ckbFile_1.setEnabled(true);
				}				
			}			
		});
		
		btnListen_2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(ckbFile_2.getItemCount()<=1) {  //铃声数量
					JOptionPane.showMessageDialog(null, "暂无铃声，无法试听", "提示信息", JOptionPane.OK_OPTION);
					return;
				}
				if("试听".equals(btnListen_2.getText())){
					//播放选中的铃声。ringIndex：铃声序号
					int ringIndex = ckbFile_2.getSelectedIndex()-1;
					System.out.println(ringIndex);
					//音乐播放
					if(ringIndex == 0) {
						JOptionPane.showMessageDialog(null, "请选择铃声", "提示信息", JOptionPane.OK_OPTION);
					}else{
						//音乐播放（待实现）
				      ringTh1 =executeRing(alarmPaths.get(ringIndex));
						
					}
					//将按钮显示更改为“停止”
					btnListen_2.setText("停止");					
				}else if("停止".equals(btnListen_2.getText())){
					//如果铃声正在播放，则停止闹钟铃声播放（待实现）
					if(ringTh1 != null) {
						if(ringTh1.isAlive()) 
							ringTh1.close();//运行状态停止
					}
					if(ringTh1 != null)
					
					btnListen_2.setText("试听");				
					//重置时，分，秒选项
					ckbHour_2.setSelectedIndex(0);
					ckbMinute_2.setEnabled(false);
					ckbSecond_2.setEnabled(false);
					//下拉列表可用
					ckbFile_2.setEnabled(true);
				}				
			}			
		});
		
		//设置提示行文字
		lblTip_1 = new JLabel("提示文字");
		lblTip_1.setFont(font_2);
		lblTip_1.setBounds(40, 90, 100, 30); //设置lblTip_1标签的位置
		txtTip_1 = new JTextField("休息，休息一下吧");
		txtTip_1.setFont(font_2);
		txtTip_1.setBounds(120, 90, 175, 30); //设置txtTip_1标签的位置
		lblTip_2 = new JLabel("提示文字");
		lblTip_2.setFont(font_2);
		lblTip_2.setBounds(40, 90, 100, 30); //设置lblTip_1标签的位置
		txtTip_2 = new JTextField("休息，休息一下吧");
		txtTip_2.setFont(font_2);
		txtTip_2.setBounds(120, 90, 175, 30); //设置txtTip_1标签的位置
		
		//设置铃声提醒方式
		lblRepeat_1 = new JLabel("重复提醒");
		lblRepeat_1.setFont(font_2);
		lblRepeat_1.setBounds(40, 130, 100, 30); //设置lblRepeat_1标签的位置
		rbNorepeat_1 = new JRadioButton("不重复");
		rbNorepeat_1.setFont(font_2);
		rbNorepeat_1.setSelected(true);
		rbNorepeat_1.setBounds(120, 130, 80, 30); //设置rbNorepeat_1标签的位置
		rbRepeat_1 = new JRadioButton("每天提醒");
		rbRepeat_1.setFont(font_2);
		rbRepeat_1.setBounds(200, 130, 80, 30); //设置rbRepeat_1标签的位置
		lblRepeat_2 = new JLabel("重复提醒");
		lblRepeat_2.setFont(font_2);
		lblRepeat_2.setBounds(40, 130, 100, 30); //设置lblRepeat_1标签的位置
		rbNorepeat_2 = new JRadioButton("不重复");
		rbNorepeat_2.setFont(font_2);
		rbNorepeat_2.setSelected(true);
		rbNorepeat_2.setBounds(120, 130, 80, 30); //设置rbNorepeat_1标签的位置
		rbRepeat_2 = new JRadioButton("每天提醒");
		rbRepeat_2.setFont(font_2);
		rbRepeat_2.setBounds(200, 130, 80, 30); //设置rbRepeat_1标签的位置
		
		
		ButtonGroup buttongrp_1=new ButtonGroup();
     	buttongrp_1.add(rbRepeat_1);
     	buttongrp_1.add(rbNorepeat_1);
     	ButtonGroup buttongrp_2=new ButtonGroup();
     	buttongrp_2.add(rbRepeat_1);
     	buttongrp_2.add(rbNorepeat_1);
     	
     	//启动铃声按钮
     	btnStart_1 = new JButton("开启定时闹钟");
     	btnStart_1.setForeground(new Color(255,0,0));//按钮前景色
     	btnStart_1.setFont(font_2);
     	btnStart_1.setBounds(120, 170, 120, 30); //设置btnStart_1标签的位置
     	btnStart_2 = new JButton("开启定时闹钟");
     	btnStart_2.setForeground(new Color(255,0,0));//按钮前景色
     	btnStart_2.setFont(font_2);
     	btnStart_2.setBounds(120, 170, 120, 30); //设置btnStart_1标签的位置
     	
     	btnStart_1.addActionListener(new ActionListener(){ //闹钟1的启动按钮
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				String h = ckbHour_1.getSelectedItem().toString();
				String m = ckbMinute_1.getSelectedItem().toString();
				String s = ckbSecond_1.getSelectedItem().toString();
				if(!"关闭".equals(h) && !"关闭".equals(m) && !"关闭".equals(s) && ckbFile_1.getSelectedIndex() > 0){
					btnListen_1.setText("停止");					
					//ringIndex：铃声序号
					int ringIndex = ckbFile_1.getSelectedIndex() - 1;
//					System.out.println(ringIndex);
					//启动TimeListen线程，每隔1秒获取一次系统当前时间，与设定的闹钟时间进行比较，如相等则播放铃声
					new Thread(new TimeListen(1, alarmPaths.get(ringIndex),hour,minute,second)).start(); //内部类：TimeListen
				}else{
					JOptionPane.showMessageDialog(null, "时间未设置，或是未选铃声", "温馨提示：", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//获取选择的信息，闹钟设置
				boolean isRepeat = false;
				if(rbNorepeat_1.isSelected()) isRepeat = false;
				if(rbRepeat_1.isSelected()) isRepeat= true;
				String tipContent = txtTip_1.getText();
				System.out.println(hour+" "+minute+" "+second+" "+ ringIndex +" "+isRepeat+" "+ tipContent);
				//时，分，秒，铃声，是否重置（后续补充）
				
			}			
		});
     	
     	btnStart_2.addActionListener(new ActionListener(){ //闹钟1的启动按钮
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				String h = ckbHour_2.getSelectedItem().toString();
				String m = ckbMinute_2.getSelectedItem().toString();
				String s = ckbSecond_2.getSelectedItem().toString();
				if(!"关闭".equals(h) && !"关闭".equals(m) && !"关闭".equals(s) && ckbFile_2.getSelectedIndex() > 0){
					btnListen_2.setText("停止");					
					//ringIndex：铃声序号
					 ringIndex = ckbFile_2.getSelectedIndex() - 1;
//					System.out.println(ringIndex);
					//启动TimeListen线程，每隔1秒获取一次系统当前时间，与设定的闹钟时间进行比较，如相等则播放铃声
					new Thread(new TimeListen(1, alarmPaths.get(ringIndex),hour,minute,second)).start(); //内部类：TimeListen
				}else{
					JOptionPane.showMessageDialog(null, "时间未设置，或是未选铃声", "温馨提示：", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//获取选择的信息，闹钟设置
				boolean isRepeat2 = false;
				if(rbNorepeat_1.isSelected()) isRepeat2 = false;
				if(rbRepeat_1.isSelected()) isRepeat2= true;
				String tipContent = txtTip_2.getText();
				System.out.println(hour+" "+minute+" "+second+" "+ ringIndex +" "+isRepeat2+" "+ tipContent);
				//时，分，秒，铃声，是否重置（后续补充）
			}			
		});

     	
     	
     	//闹钟1面板加入相应的组件
		alarmPanel_1.add(lblRingsetup_1);
		alarmPanel_1.add(ckbHour_1);
		alarmPanel_1.add(lblHour_1);
		alarmPanel_1.add(ckbMinute_1);
		alarmPanel_1.add(lblMinute_1);
		alarmPanel_1.add(ckbSecond_1);
		alarmPanel_1.add(lblSecond_1);
		alarmPanel_1.add(lblRing_1);
		alarmPanel_1.add(ckbFile_1);
		alarmPanel_1.add(btnListen_1);
		alarmPanel_1.add(lblTip_1);
		alarmPanel_1.add(txtTip_1);
		alarmPanel_1.add(lblRepeat_1);
		alarmPanel_1.add(rbNorepeat_1);
		alarmPanel_1.add(rbRepeat_1);		
		alarmPanel_1.add(btnStart_1);	
		//闹钟二加入相应的组件
		alarmPanel_2.add(lblRingsetup_2);
		alarmPanel_2.add(ckbHour_2);
		alarmPanel_2.add(lblHour_2);
		alarmPanel_2.add(ckbMinute_2);
		alarmPanel_2.add(lblMinute_2);
		alarmPanel_2.add(ckbSecond_2);
		alarmPanel_2.add(lblSecond_2);
		alarmPanel_2.add(lblRing_2);
		alarmPanel_2.add(ckbFile_2);
		alarmPanel_2.add(btnListen_2);
		alarmPanel_2.add(lblTip_2);
		alarmPanel_2.add(txtTip_2);
		alarmPanel_2.add(lblRepeat_2);
		alarmPanel_2.add(rbNorepeat_2);
		alarmPanel_2.add(rbRepeat_2);		
		alarmPanel_2.add(btnStart_2);	
		//个人主页面板
		alarmPanel_3 =new AlarmPanel(image_2);
		alarmPanel_3.setPreferredSize(new Dimension(500, 400));	
		alarmPanel_3.setLayout(null); //设置tabpane 为null布局
		
		//闹钟2面板。作业：闹钟2面板的组件跟闹钟1一样，请补充完整
//		alarmPanel_2 = new AlarmPanel(image_2);
//		alarmPanel_2.setPreferredSize(new Dimension(500, 400));			
//		alarmPanel_2.setLayout(null); //设置tabpane 为null布局
		//补充闹钟2面板的组件
		
		//定义tab面板
		tbPane = new JTabbedPane();
		String[] tabNames = {"闹钟1", "闹钟二", "个人主页"};  
		//tab面板上加入闹钟1面板和闹钟2面板
		tbPane.addTab(tabNames[0], alarmPanel_1);
		tbPane.setMnemonicAt(0, KeyEvent.VK_0);// 设置第一个位置的快捷键为0
		tbPane.addTab(tabNames[1], alarmPanel_2);
		tbPane.setFont(font_3);
		tbPane.setMnemonicAt(1, KeyEvent.VK_1);// 设置第一个位置的快捷键为0
		tbPane.setBounds(40, 70, 500, 250); //设置btnStart_1标签的位置
		tbPane.addTab(tabNames[2], alarmPanel_3);
		tbPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		//设置提醒信息
 	    lblMsg = new JLabel("提示");	
 	    lblMsg.setFont(font_2);
 	    lblMsg.setBounds(30, 340, 30, 25); //设置lblMsg标签的位置
		lblMsgContent = new JLabel("如果关闭程序闹钟将无法响铃，每次启动程序需要重新设置闹钟才能生效");
		lblMsgContent.setFont(font_2);
		lblMsgContent.setBounds(30, 360, 500, 60); //设置lblMsgContent标签的位置
		
		//将上述组件加入主面板
		alarmPanel_0 = new JPanel();
		alarmPanel_0.setLayout(null); //设置主面板为null布局
		alarmPanel_0.add(lblDateTip);
		alarmPanel_0.add(lblDateTime);
		alarmPanel_0.add(tbPane);
		alarmPanel_0.add(lblMsg);
		alarmPanel_0.add(lblMsgContent);
		
		//设置菜单栏及菜单选项
		menuBar = new JMenuBar();  //菜单栏
		menu_file = new JMenu("文件");  //菜单	
		menu_tools = new JMenu("工具"); //菜单
		menu_about = new JMenu("关于"); //菜单
		menuItem_ring = new JMenuItem("上传铃声");  //菜单选项
		menuItem_ring.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//获取本地文件
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.setMultiSelectionEnabled(true); //文件可多选
				fileChoose.showOpenDialog(AlarmUI.this); //打开文件选择对话框
				File[] files = fileChoose.getSelectedFiles();
				//I/O处理：上传多个音乐文件
				boolean rs = TranslateFiles.UploadHandle(files);
				if(rs) {
					JOptionPane.showMessageDialog(null, "上传成功");
				}else {
					JOptionPane.showMessageDialog(null, "上传失败");
				}
			}
			
		});
		menuItem_down_rings = new JMenuItem("下载铃声");
		menuItem_down_rings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//数据库表中读取文件（音乐）：写到本地文件夹
				isDown = TranslateFiles.dowFiles(0,"无铃声可下载", ringsDownloadPath);//
				if(isDown) AlarmUI.this.initFilePath();//下载铃声后，更新alarmPath
			}
			
		});
		menuItem_about = new JMenuItem("版本说明");
		menuItem_about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//弹出窗体
				new AboutUI(AlarmUI.this);
			}
			
		});
		menuItem_exit = new JMenuItem("退出");
		menuItem_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0); //退出系统
			}			
		});
		
		menuItem_help = new JMenuItem("帮助");
		menuItem_ppage= new JMenuItem("个人主页");
		menuItem_ppage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//打开个人主页
				new Perpage(AlarmUI.this);
			}			
		});
		//将菜单选项加入菜单
		menu_file.add(menuItem_ring);
		menu_file.add(menuItem_exit);
		menu_tools.add(menuItem_down_rings);
		menu_about.add(menuItem_about);
		menu_about.add(menuItem_help);
		menu_about.add(menuItem_ppage);
		
		//将菜单加入菜单栏
		menuBar.add(menu_file);
		menuBar.add(menu_tools);
		menuBar.add(menu_about);
		
		//窗体默认边界布局
		this.add(alarmPanel_0);	//窗体上加面板
		this.setJMenuBar(menuBar); //窗体上设置菜单栏
		this.addWindowListener(new WindowListener(){ //窗体事件监听

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {				
				// 当点击"X"关闭窗口按钮时，会询问用户是否要最小化到托盘
				// 是，表示最小化到托盘，否，表示退出
				int option = JOptionPane.showConfirmDialog(AlarmUI.this, "是否最小化到托盘?" , "提示：", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION){
					new AlarmTray(AlarmUI.this); //创建托盘对象
				}else{
					System.exit(0);
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			
			}

		

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//设置窗体大小和可见性
		this.setVisible(true);
		this.setPreferredSize(new Dimension(600, 500));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //关闭窗体时是隐藏
	    this.pack(); //调整窗体大小
	    this.setLocationRelativeTo(null); //设置窗体剧中
	    this.setResizable(false); //设置窗体大小不可变
	}
	
	/*
	 * 铃声文件路径初始化
	 */
	public void initFilePath(){
		alarmPaths = new ArrayList<String>(); //创建集合类ArrayList对象
		//创建目录对象
		File file_dir = new File(ringsDownloadPath);
		if(!file_dir .exists()) return; //如果目录不存在，返回
		File[] file_list = file_dir.listFiles();
		for(int i =0 ; i < file_list.length; i++){
			String fname = file_list[i].getName(); //获取每个文件的文件名
			String[] str = fname.split("\\."); //分割文件名字符串
			//将wav类型加入ArrayList对象中
			if(str!=null && str.length==2 && "wav".equals(str[1].toLowerCase())) {
				alarmPaths.add(file_list[i].getPath());
			}
		}
	}
	
	/*
	 * 计算剩余时间
	 */
	public String getRemaintime(int clockIndex, int target_h, int target_m, int target_s){
		String msg = null;

		Calendar cal = Calendar.getInstance(); //获取当前时间
		int current_h = cal.get(Calendar.HOUR_OF_DAY);
		int current_m = cal.get(Calendar.MINUTE);
		int current_s = cal.get(Calendar.SECOND);
	
		//将目标和当前系统时间转为秒
		int tar = target_h * 3600 + target_m * 60 + target_s;
		int cur = current_h * 3600 + current_m * 60 + current_s;
		
		//当前时间与设置时间相差30秒时，设置IntervalSecond属性值
		if(tar-cur == 30) {
			this.setIntervalSecond(30);
		}
		
		//当前时间与设置时间吻合时，消息内容更新
		if(tar-cur == 0) {
			msg = "time is up";
			System.out.println(msg);
		}
		
		return msg; 
	}
	
	//播放铃声线程创建的方法（待实现）
	public RingPlayer executeRing(String path) {
		RingPlayer th = new RingPlayer(path,true);//创新音乐线程类播放对象
		th.start();
		return th;
	}
	
	/*
	 * 内部类：时间监听线程，即每隔1秒获取一次剩余时间，且一旦到闹铃时间，则播放铃声（在下一任务中实现）
	 */
	class TimeListen implements Runnable{
		String filepath;
		int clockIndex;
		int target_h;
		int target_m;
		int target_s;
		
		//参数：clockIndex；filepath：铃声文件路径；target_h：时；target_m：分；target_s：秒
		public TimeListen(int clockIndex, String filepath, int target_h, int target_m, int target_s) {
			this.filepath = filepath;
			this.clockIndex = clockIndex;
			this.target_h = target_h;
			this.target_m = target_m;
			this.target_s = target_s;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try{
					Thread.sleep(1000); //休眠1000ms，即1秒
				}catch(Exception e){
					e.printStackTrace();
				}
				//调用方法getRemaintime获得剩余时间
				String msg = getRemaintime(clockIndex, this.target_h, this.target_m, this.target_s);
				if(msg != null) {//定时时间到
					//闹钟播放音乐的处理代码
					//选择每天提醒，则重复计算剩余时间，否则不再重复计算
					if(this.clockIndex == 1 && rbRepeat_1.isSelected()){
						//音乐播放（待实现）
						ringTh1 =  executeRing(this.filepath);
					}else if(this.clockIndex == 1 && rbNorepeat_1.isSelected()) {
						//音乐播放（待实现）
						ringTh1 =  executeRing(this.filepath);
						break;
					}
				}
			}
		}
		
	}
	
	//获取当前窗体的x、y坐标值
	public int getPointX() {
		return (int)this.getLocation().getX();
	}
	public int getPointY() {
		return (int)this.getLocation().getY();
	}
	
	public static void main(String[] args){
		new AlarmUI(); //AlarmUI实例化
		
	}

	
}


