package com.fykj._b._core.kaptcha;

import com.fykj._b._core.KaptchaRepository;
import org.springframework.stereotype.Component;

@Component
public class _SimpleKeyGen implements KaptchaRepository.KeyGen {

	@Override
	public String key(KaptchaVO kaptchaVO) throws Exception {
		return kaptchaVO.getKaptchaId();
	}

}
