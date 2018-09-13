package com.fykj.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * serialize - deserialize
 * @author J
 *
 */
public abstract class JObjectSerializableUtils {

	public static byte[] serialize(Object obj){
		try {
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			ObjectOutputStream outputStream=new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(obj);
			byte[] bytes=byteArrayOutputStream.toByteArray();
			return bytes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T deserialize(byte[] bytes,Class<T> clazz){
		try {
			ObjectInputStream inputStream=new ObjectInputStream(new ByteArrayInputStream(bytes));
			return clazz.cast(inputStream.readObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
}
