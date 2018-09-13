package com.fykj._b._core.kaptcha;

import com.fykj._b._core.KaptchaRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class _TextGen implements KaptchaRepository.TextGen<Object> {

	@Override
	public String text(Object context) throws Exception {
		String str="";
		str+=RandomUtils.nextInt(1, 10);
		str+=RandomUtils.nextInt(1, 10);
		str+=RandomUtils.nextInt(1, 10);
		str+=RandomUtils.nextInt(1, 10);
		return str;
	}

}
