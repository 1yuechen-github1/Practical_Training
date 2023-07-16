package com.alarm.DAO;
/**
* 铃声文件类TFiles，属性有：id、文件名、文件内容、文件大小、文件类型
* 与数据表tfiles的表字段一一对应
*/
public class TFiles {
	private long id;
	private String fileName;
	private byte[] fileContent;
//	private String filePath;
	private String fileSize;
	private int fileType;
	
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
//	public String getFilePath() {
//		return filePath;
//	}
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	
}
