package com.alarm.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
* 闹钟工具软件日志类
*
*/
public class AlarmLogger{
	private static Logger logger;
	private static String fileName = "系统日志";
	
	public static Logger getLogger(String className) {
		logger = Logger.getLogger(className);
		setLogProperties(logger, Level.ALL);//设置为自定义 的属性
		return logger;
	}


	//自定义Logger属性
	private static void setLogProperties(Logger logger,Level level){
		FileHandler fh = null;
		try{
			fh = new FileHandler(getFilePath(), true);			 
			fh.setLevel(level);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);			
		}catch(SecurityException e){
			logger.log(Level.SEVERE, "安全性错误", e);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "读文件错误", e);
		}
	}
	
	//设置日志文件保存目录和文件
	private static String getFilePath(){
		StringBuffer filePath = new StringBuffer();
		filePath.append("log/" + fileName);
		File file = new File(filePath.toString());
		if(!file.exists()){
			file.mkdirs();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		filePath.append("/" + sdf.format(date) + ".log");
		return filePath.toString();
	}	
}
