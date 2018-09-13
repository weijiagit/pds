package com.fykj.kernel._c.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


/**
 * transfering file in the network .  
 * Or copy the file to the target location.
 * @author J
 *
 */
public class JFile implements Serializable{
	
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
	 * actual content with byte style. 
	 */
	private byte[] fileContent;
	
	/**
	 * end with slash.
	 */
	private String relativePath;
	
	/**
	 * ftp path
	 */
	private String ftpPath;

	public String getFileNameNoExtension() {
		return fileNameNoExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getExpectedFullFileName() {
		return expectedFullFileName;
	}
	
	/**
	 * get actual content byte style. 
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}
	
	/**
	 * call to populate the actual byte[] from the inputstram . 
	 * <p>the file does not exist , but we already hold the bytes. 
	 * the case occurs when file is getting via HTTP.
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	
	/**
	 * if the file exists, return the content length of the file, otherwise return the byte array length of attribute {@link #fileContent}
	 */
	public long contentLength() throws IOException {
		return fileContent.length;
	}
	
	/**
	 * if the file exists, return the input stream of the file, otherwise return the byte array wrapper of attribute attribute {@link #fileContent}
	 * @see ByteArrayInputStream
	 * @return ByteArrayInputStream of the {@link #fileContent}
	 */
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fileContent);
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public void setFileNameNoExtension(String fileNameNoExtension) {
		this.fileNameNoExtension = fileNameNoExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setExpectedFullFileName(String expectedFullFileName) {
		this.expectedFullFileName = expectedFullFileName;
	}

	/**
	 * @return the ftpPath
	 */
	public String getFtpPath() {
		return ftpPath;
	}

	/**
	 * @param ftpPath the ftpPath to set
	 */
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	
}
