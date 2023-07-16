package com.alarm.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.alarm.DAO.DAOTFiles;
import com.alarm.DAO.TFiles;
import com.alarm.log.AlarmLogger;
import com.alarm.ui.AlarmUI;
/**
* 实现上传下载功能
* 
*/
public class TranslateFiles {
	private static Logger alarmLogger = AlarmLogger.getLogger(AlarmUI.class.getName()); 
	
	//多文件上传处理
	public static boolean UploadHandle(File[] files) {
		boolean rs = false;
		int count = 0;
		for(int i = 0; i<files.length; i++) {
			rs = sendFile(files[i]);   //上传文件
			if(rs) count++;
		}
		return (count == files.length)?true:false;
	}
	
	//上传铃声
	private static boolean sendFile(File file) {
		FileInputStream fis = null;
		BufferedInputStream buf_is = null;
		DAOTFiles daot = new DAOTFiles();
		TFiles tfile = new TFiles();
		boolean flag = false;
		
		try {
			//创建文件对象，读入流，用于读取本地文件
			file = new File(file.getPath());
			fis = new FileInputStream(file);
			buf_is = new BufferedInputStream(fis);
			byte[] i_byte = new byte[fis.available()];
			//将文件读取到字节数组
			buf_is.read(i_byte, 0 , i_byte.length);
			tfile.setFileContent(i_byte);
			tfile.setFileName(file.getName());
			String fileSize = getFileSize(file.length());
			tfile.setFileSize(fileSize);
			String[] str = file.getName().split("\\.");//以.分割文件名字符串
			if(str.length>=2) {
				int index = str.length-1;
				if("wav".equals(str[index].toLowerCase())) {
					tfile.setFileType(0);
				}else if("jpg".equals(str[index].toLowerCase()) || "gif".equals(str[index].toLowerCase()) || "png".equals(str[index].toLowerCase())) {
					tfile.setFileType(1);
				}
			}
			flag = daot.addFile(tfile);//写入数据库
		}catch(Exception e) {
			alarmLogger.log(Level.SEVERE, "文件读取出错");
			e.printStackTrace();
		}finally {
			try {
				if(buf_is != null) buf_is.close();
				if(fis != null) fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				alarmLogger.log(Level.SEVERE, "输入流关闭出错");
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	//下载铃声(功能详见教材P221)
	public static boolean dowFiles(int ftype, String msg, String subDirectory) {
		DAOTFiles daot = new DAOTFiles();
		List<TFiles> li = null;
		li = daot.getFile(ftype);//从数据库表中读取文件对象
		if(li == null || li.size() == 0) {
			JOptionPane.showMessageDialog(null, msg, "提示信息", JOptionPane.OK_OPTION);
			return false;
		}
		//创建保存铃声的目录对象
		File directory = new File(subDirectory);
		if(!directory.exists()) { //如果目录不存在，则创建
			directory.mkdir();
		}
		for(int i=0; i<li.size(); i++) { //将文件逐个写到指定目录
			TFiles tfile = li.get(i);
			File ring_file = new File(directory.getAbsolutePath() + File.separatorChar + tfile.getFileName());
			FileOutputStream fos = null;
			BufferedOutputStream buf_os = null;
			try {//利用字节流将fileContent字段中的铃声文件内容输出到系统目录
				fos = new FileOutputStream(ring_file);
				buf_os = new BufferedOutputStream(fos);
				buf_os.write(tfile.getFileContent());
				buf_os.flush();
			}catch(FileNotFoundException e1) {
				e1.printStackTrace();
				alarmLogger.log(Level.SEVERE, "文件不存在");
			}catch(IOException e1) {
				e1.printStackTrace();
				alarmLogger.log(Level.SEVERE, "文件读取出错");
			}finally {
				try{
					if(buf_os != null) buf_os.close();
					if(fos != null) fos.close();
				}catch(Exception e1) {
					e1.printStackTrace();
					alarmLogger.log(Level.SEVERE, "输出流关闭出错");
				}
			}
		}
		JOptionPane.showMessageDialog(null, "已下载"+li.size()+"个文件", "提示信息", JOptionPane.OK_OPTION);
		return (li.size()>0)?true:false;
	}
	
	//格式化文件长度
	private static String getFileSize(long bytes) {
		//设置数字格式，保留一位有效小数
		DecimalFormat df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
		double size = ((double)bytes) / (1<<30); //1024*1024*1024
		if(size >= 1) return df.format(size) + "GB";
		size = ((double)bytes) / (1<<20);
		if(size >= 1) return df.format(size) + "MB";
		size = ((double)bytes) / (1<<10); //1024
		if(size >= 1) return df.format(size) + "KB";
		return df.format(size) + "B";
	}
}