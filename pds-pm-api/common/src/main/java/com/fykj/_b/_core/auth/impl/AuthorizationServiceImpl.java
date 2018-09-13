package com.fykj._b._core.auth.impl;

import org.springframework.stereotype.Service;

import com.fykj.kernel._c.AuthorizationService;
import com.fykj.kernel._c.model.SessionUser;

@Service
public class AuthorizationServiceImpl implements AuthorizationService<String> {

	@Override
	public boolean authorise(SessionUser sessionUser, String source) {
		return true;
	}

}
