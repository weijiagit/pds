package com.fykj.web.model;

import java.io.Serializable;

import com.fykj.kernel._c.model.JFile;

public class UploadFileVO  implements Serializable,Cloneable{
	/**
	 * file name without extension . 
	 */
	private String fileNameNoExtension;
	
	/**
	 * file extension , without dot(.)
	 */
	private String fileExtension;
	
	/**
	 * the modified full file name user changed on the prototype one. 
	 */
	private String expectedFullFileName;

	/**
	 * the html Dome input name  
	 */
	private String fileName;
	
	/**
	 * end with slash.
	 */
	private String relativePath;



	public String getFileNameNoExtension() {
		return fileNameNoExtension;
	}



	public void setFileNameNoExtension(String fileNameNoExtension) {
		this.fileNameNoExtension = fileNameNoExtension;
	}



	public String getFileExtension() {
		return fileExtension;
	}



	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}



	public String getExpectedFullFileName() {
		return expectedFullFileName;
	}



	public void setExpectedFullFileName(String expectedFullFileName) {
		this.expectedFullFileName = expectedFullFileName;
	}



	public String getRelativePath() {
		return relativePath;
	}



	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static UploadFileVO buileFileVo(JFile jFile,String fileName){
		UploadFileVO fileVO=new UploadFileVO();
		fileVO.setExpectedFullFileName(jFile.getExpectedFullFileName());
		fileVO.setFileExtension(jFile.getFileExtension());
		fileVO.setFileNameNoExtension(jFile.getFileNameNoExtension());
		fileVO.setRelativePath(jFile.getRelativePath());
		fileVO.setFileName(fileName);
		return fileVO;
	}
	
	
}
