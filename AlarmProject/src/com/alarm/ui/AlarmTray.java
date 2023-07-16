package com.alarm.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.alarm.log.AlarmLogger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
* 系统托盘程序
*
*/
public class AlarmTray {
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu popmenu;
	private MenuItem openMain;
	private MenuItem exitMain;
	private AlarmUI alarmMain;
	private Logger alarmLogger = AlarmLogger.getLogger(AlarmUI.class.getName());

	public AlarmTray(AlarmUI alarmMain) {
		// TODO Auto-generated constructor stub
		this.alarmMain = alarmMain;
		//创建托盘图标
		icon = new ImageIcon(AlarmTray.class.getResource("/images/tray.png")).getImage();
		popmenu = new PopupMenu(); //弹出菜单
		openMain = new MenuItem("Restore");
		openMain.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//还原窗口
				// TODO Auto-generated method stub
				systemTray.remove(trayIcon); //将最小化系统按钮移除
				setMainVisible(true);  //还原主界面窗体
			}
			
		});
		
		exitMain = new MenuItem("Exit");
		exitMain.addActionListener(new ActionListener(){//退出系统
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		if(SystemTray.isSupported()){
			systemTray = SystemTray.getSystemTray();//获取实例化系统托盘
			trayIcon = new TrayIcon(icon, "Open Alarm", popmenu);//实例化最小化图标		
			trayIcon.addMouseListener(new MouseListener(){  //托盘图标的鼠标事件
				@Override
				public void mouseClicked(MouseEvent evt) {
					// TODO Auto-generated method stub
					//鼠标单击或双击，且为左键
					if((evt.getClickCount() == 1 || evt.getClickCount() == 2) && SwingUtilities.isLeftMouseButton(evt)){
						if(!alarmMain.isVisible()){
							setMainVisible(true);
						}else{
							setMainVisible(false);
						}
					}					
				}	

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub					
				}				
			});
			popmenu.add(openMain);
			popmenu.add(exitMain);
			
			try { //捕获异常
				systemTray.add(trayIcon);  //系统托盘加托盘图标
			}catch(Exception e) {
				alarmLogger.log(Level.SEVERE, "图标文件有误", e);
			}
		
		}
	}
	
	//设置闹钟主窗体界面可见性
	public void setMainVisible(boolean isVisible){
		alarmMain.setVisible(isVisible);		
		if(isVisible) alarmMain.setExtendedState(JFrame.NORMAL);  //设置扩展状态为正常
		else alarmMain.setExtendedState(JFrame.ICONIFIED); //设置扩展状态为最小化
	}
}
