package com.fykj._b._core;

import java.awt.image.BufferedImage;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KaptchaAdapter {

	@Autowired
	private Producer producer;
	

	public BufferedImage iamge(String text){
		return producer.createImage(text);
	}
	
	public String text(){
		return producer.createText();
	}
	
	
}
