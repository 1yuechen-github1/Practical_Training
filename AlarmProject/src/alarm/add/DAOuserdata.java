package alarm.add;

//package com.alarm.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPasswordField;

import com.alarm.DAO.TFiles;
import com.alarm.utility.PropertiesUtility;
/**
* 注册成功数据添加到数据库，
* 后续对user表的增删该查。
*/
public class DAOuserdata {
	 static ResultSet rs;
	 static TFilees1 file;
	PreparedStatement pstat;
	List<TFilees1>list;
	static Connection con;	
	//驱动程序名
	private static	String driver ;
	//URL指向要访问的数据库名mydata
	private static	String url;
	//MySQL配置时的用户名
	private static String user;
	//MySQL配置时的密码
	private static String password;
	private Map<String, String> dbMap;
	/**
	 * 初始化数据库连接的参数
	 * 构造方法
	 */
	public DAOuserdata(){	
		//调用配置文件读取方法，返回Map类对象
		dbMap = PropertiesUtility.getInstance().readProperties();
		if(dbMap != null) {
			driver = dbMap.get("jdbc.driver");	//读取driver对应的值	
			url =  dbMap.get("jdbc.url");	//读取url对应的值		
			user =  dbMap.get("jdbc.username");	//读取username对应的值		
			password =  dbMap.get("jdbc.password");	//读取password对应的值

		}
	
		driver =  "com.mysql.cj.jdbc.Driver";		
		url =  "jdbc:mysql://localhost:3306/alarmdb";		
		user =  "root";		
		password =  "Cy15992389642";	
				
	}

	/**
	 * 保存文件数据
	 * @param file：文件信息
	 * @throws SQLException
	 */
	public static boolean addFile(String username,String passward) throws SQLException{
		boolean flag = false;
		try{
			Class.forName(driver);//装载驱动
			con = DriverManager.getConnection(url, user, password); //建立链接
			if(!con.isClosed()){
				System.out.println("连接数据库成功");
			}
			String sql = "insert into `user`(name,passward) values(?,?)";
			PreparedStatement pstat = con.prepareStatement(sql);
			//SQL语句参数
			//pstat.setString(1, null);
			pstat.setString(1, username);
			//pstat.setInt(3,file.getid());
			pstat.setString(2, passward);
			//pstat.setString(5, file.getphoto());
			//pstat.setInt(6, file.getid());
			//pstat.setString(7, file.getreserved());
			int rs = pstat.executeUpdate(); //执行更新语句
			if(rs != 0){
				System.out.println("上传文件数据保存成功");
				flag = true;
			
			}				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null) con.close();
		}
		return flag;
	}
	/**
	 * 读取文件数据
	 * @return 文件信息对象集合
	 */	
	public List<TFilees1> getFile(String username,String passward){
		List<TFilees1> li = new ArrayList<TFilees1>();
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			Statement stat = con.createStatement();
			String sql = "select * from user where name=? and passward =?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1,username);
			pstat.setString(2,passward);
			//pstat.setString(2, file.getpassward());
			rs = pstat.executeQuery();
			while(rs.next()){
				file = new TFilees1();
				file.setname(rs.getString("name"));
				file.setpassward(rs.getString("passward"));
				//file.setFileContent(rs.getBlob("filecontent").getBytes(1, (int)rs.getBlob("filecontent").length()));//设置文件属性
				//file.setFileSize(rs.getString("filesize"));
				li.add(file);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return li;
	}
	
	public List<TFilees1> updateFile(String username,String passward ){
		List<TFilees1> li = new ArrayList<TFilees1>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			Statement stat = con.createStatement();
			String sql = "UPDATE `user` SET passward=? WHERE name=?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1,passward);
			pstat.setString(2,username);
			//pstat.setString(2, file.getpassward());
			int rs = pstat.executeUpdate();
			if(rs!=0)
			{
				System.out.println("更新数据成功");
			}
			li.add(file);
//			while(rs.next()){
//				file = new TFilees1();
//				file.setname(rs.getString("name"));
//				file.setpassward(rs.getString("passward"));
				//file.setFileContent(rs.getBlob("filecontent").getBytes(1, (int)rs.getBlob("filecontent").length()));//设置文件属性
				//file.setFileSize(rs.getString("filesize"));
//				li.add(file);
//		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return li;
	
	}
}

