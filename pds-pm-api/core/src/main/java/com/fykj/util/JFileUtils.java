package com.fykj.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class JFileUtils {
	
	/**
	 * return file name without extension . i.e
	 * file name "JFileUtils" for "JFileUtils.java"
	 * @param file
	 * @return
	 */
	public static String getFileNameNoExtension(File file){
		if(file==null) return null ;
		String name=file.getName();
		int dotIndex=name.lastIndexOf('.');
		return name.substring(0,dotIndex!=-1?dotIndex:name.length());
	}
	
	/**
	 * return file extension . i.e
	 * extension name "java" for "JFileUtils.java"
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file){
		if(file==null) return null ;
		String name=file.getName();
		int dotIndex=name.lastIndexOf('.');
		if(dotIndex==-1){
			return "";
		}
		return name.substring(dotIndex+1);
	}
	
	/**
	 * copy file.
	 * @param file
	 * @param fullPath
	 */
	public static void copyFile(File file, String fullPath) {
		try {
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(fullPath);
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	/**
	 * get file bytes. 
	 * @param file
	 * @return
	 */
	public static byte[] getBytes(File file) {
		try {

			if (file == null || !file.isFile())
				return null;

			ByteArrayOutputStream bos = null;
			FileInputStream fis = null;
			try {
				bos = new ByteArrayOutputStream();
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	/**
	 * get file name according to the file path.
	 * @param filePath
	 * @return file name with extension.
	 */
	public static final String getFileName(String filePath){
		
		if(JStringUtils.isNullOrEmpty(filePath)) {
			throw new IllegalArgumentException(filePath+" is null or empty.");
		}
		
		int lastSlashIndex=filePath.lastIndexOf("/");
		
		if(lastSlashIndex==-1){
			return filePath;
		}
		
		return filePath.substring(lastSlashIndex+1);
		
	}
	
	public static void write(File file,byte[] bytes){
		FileOutputStream fileOutputStream=null;
		try{
			fileOutputStream=new FileOutputStream(file);
			fileOutputStream.getChannel().write(ByteBuffer.wrap(bytes));
			fileOutputStream.flush();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}
