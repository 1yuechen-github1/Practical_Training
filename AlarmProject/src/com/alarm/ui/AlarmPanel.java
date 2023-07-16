package com.alarm.ui;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
* 有背景图的JPanel
*
*/
public class AlarmPanel extends JPanel{  //有背景图的JPanel类
	/**
	 * 
	 */
	private static final long serialVersionUID = 8201943865979535024L;
	private Image picImage;
	
	public AlarmPanel(Image picImage){
		this.picImage = picImage;
	}
    //重写paintComponent绘制jpanel背景图片
	public void paintComponent(Graphics g){
		g.drawImage(this.picImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	

}
