package com.fykj._b._core.kaptcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.fykj._b._core.KaptchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.KaptchaAdapter;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.util.JUniqueUtils;

@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {

	@Autowired
	private KaptchaAdapter kaptcha;
	
	@Autowired
	private KaptchaRepository<String,String> kaptchaRepository;
	
	@Autowired
	private KaptchaRepository.KeyGen keyGen;
	
	@Autowired
	private KaptchaRepository.TextGen<Object> textGen;
	
	
	/**
	 * get one kaptcha
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path="/one",method=RequestMethod.GET)
	public InvokeResult one() throws Exception {
		String format="jpg";
		String rn=textGen.text(null);
		BufferedImage image= kaptcha.iamge(rn);
		byte[] bytes=null;
		try(ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			){
			ImageIO.write(image, format, byteArrayOutputStream);
			bytes=byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.flush();
		}
		String base64=Base64.getEncoder().encodeToString(bytes);
		KaptchaVO kaptchaVO=new KaptchaVO();
		kaptchaVO.setBase64(base64);
		kaptchaVO.setFormat(format);
		kaptchaVO.setKaptchaId(JUniqueUtils.unique());
		kaptchaVO.setText(rn);
		String key=keyGen.key(kaptchaVO);
		kaptchaVO.setKey(key);
		kaptchaRepository.store(key,kaptchaVO.getText());
		return InvokeResult.success(kaptchaVO);
	}
	
	/**
	 * validate whether the kaptcha is valid or not
	 * @param kaptchaVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path="/validate",method=RequestMethod.POST)
	public InvokeResult validate(KaptchaVO kaptchaVO) throws Exception {
		boolean exists=kaptchaRepository.exists(keyGen.key(kaptchaVO), kaptchaVO.getText());
		return InvokeResult.success(exists);
	}
	
	
}
