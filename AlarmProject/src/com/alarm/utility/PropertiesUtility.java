package com.alarm.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * 读写配置文件的工具类
 *
 */
public class PropertiesUtility {	
	Properties prop;
	Map<String, String> kvMap;
	
	public PropertiesUtility(){
		prop = new Properties();//创建Properties类对象
		kvMap = new HashMap<String, String>();//创建Map接口对象
	}
	
	//获得PropertiesUtil类的实例对象
	public static PropertiesUtility getInstance(){
		return new PropertiesUtility();
	}
	
	//读写配置文件
	public Map<String, String> readProperties(){
		try{
			//创建输入流对象
			InputStream in = PropertiesUtility.class.getResourceAsStream("/config/jdbc.properties");					
			prop.load(in); //加载输入流对象
			Iterator<String> iter = prop.stringPropertyNames().iterator();//返回迭代器
			while(iter.hasNext()){
				String key = iter.next();  //获取下一个元素
				String val = prop.getProperty(key); //读取属性值
				//val = new String(val.getBytes("ISO-8859-1"),"UTF-8");//如果配置文件中有中文，要设置编码格式
				kvMap.put(key, val);//将键值对存入map
				System.out.println(key+":"+val);
			}
			in.close();	//关闭输入流		
		}catch(IOException e){
			e.printStackTrace();
		}	
		return kvMap;//返回Map接口对象
	}
	
	//写配置文件
	public void writeProperties(String key, String val){
		try{
			//创建文件
			File file = new File((PropertiesUtility.class.getResource("/config/user.properties")).toURI());
			//创建输出流对象
			OutputStream out = new FileOutputStream(file,true);					
			prop.setProperty(key, val);//设置属性值
			prop.store(out, "Copyright (c) Boxcode Studio"); //保存到配置文件中
			out.close();	//关闭输出流		
		}catch(IOException e){
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub		
		PropertiesUtility.getInstance().readProperties();
		PropertiesUtility.getInstance().writeProperties("年龄", "20");//修改后，bin目录下的属性文件会改变，src目录下的属性文件未体现		
	}
}
