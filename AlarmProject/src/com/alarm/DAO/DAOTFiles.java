package com.alarm.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alarm.ui.AlarmUI;
import com.alarm.utility.PropertiesUtility;
/**
* 数据库中铃声文件的增删改查（CRUD）操作
*/
public class DAOTFiles {
	AlarmUI alarmui =new AlarmUI();
	List<TFiles> list;
	Connection con;	
	//驱动程序名
	private	String driver ;
	//URL指向要访问的数据库名mydata
	private	String url;
	//MySQL配置时的用户名
	private String user;
	//MySQL配置时的密码
	private String password;
	private Map<String, String> dbMap;
	/**
	 * 初始化数据库连接的参数
	 * 构造方法
	 */
	public DAOTFiles(){	
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
	public boolean addFile(TFiles file) throws SQLException{
		boolean flag = false;
		try{
			Class.forName(driver);//装载驱动
			con = DriverManager.getConnection(url, user, password); //建立链接
			if(!con.isClosed()){
				System.out.println("连接数据库成功");
			}
			String sql = "insert into tfiles values(?,?,?,?,?)";
			PreparedStatement pstat = con.prepareStatement(sql);
			//SQL语句参数
			pstat.setString(1, null);
			pstat.setString(2, file.getFileName());
			pstat.setBytes(3, file.getFileContent());
			pstat.setString(4, file.getFileSize());
			pstat.setInt(5, file.getFileType());
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
	public List<TFiles> getFile(int ftype){
		List<TFiles> li = new ArrayList<TFiles>();
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			Statement stat = con.createStatement();
			String sql = "select * from TFiles where filetype=?";
			PreparedStatement pstat = con.prepareStatement(sql);
			pstat.setInt(1, ftype);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				TFiles file = new TFiles();
				file.setFileName(rs.getString("filename"));
				file.setFileContent(rs.getBlob("filecontent").getBytes(1, (int)rs.getBlob("filecontent").length()));//设置文件属性
				file.setFileSize(rs.getString("filesize"));
				li.add(file);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return li;
	}



public List<TFiles> getFile2(){
	List<TFiles> li = new ArrayList<TFiles>();
	try{
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, password);
		Statement stat = con.createStatement();
		String sql = "select filename from TFiles";
		PreparedStatement pstat = con.prepareStatement(sql);
		ResultSet rs = pstat.executeQuery();
		while(rs.next()){
			TFiles file = new TFiles();
			file.setFileName(rs.getString("filename"));
//			file.setFileContent(rs.getBlob("filecontent").getBytes(1, (int)rs.getBlob("filecontent").length()));//设置文件属性
//			file.setFileSize(rs.getString("filesize"));
			li.add(file);
		}			
	}catch(Exception e){
		e.printStackTrace();
	}		
	return li;
}


}

