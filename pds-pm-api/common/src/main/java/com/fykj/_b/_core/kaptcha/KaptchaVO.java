package com.fykj._b._core.kaptcha;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fykj.kernel._c.model.JModel;

public class KaptchaVO implements JModel {

	/**
	 * need send back the id to server
	 */
	private String kaptchaId;  
	
	/**
	 * the image format
	 */
	private String format;
	
	private String base64;
	
	@JsonIgnore
	private String text;
	
	/**
	 * send back to server for searching the value on this key
	 */
	private String key;

	public String getKaptchaId() {
		return kaptchaId;
	}

	public void setKaptchaId(String kaptchaId) {
		this.kaptchaId = kaptchaId;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@JsonIgnore
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		KaptchaVO _obj=(KaptchaVO) obj;
		return this.text.equals(_obj.text);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
