package com.fykj._b._core.auth.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj.kernel._c.AuthenticationService;
import com.fykj.kernel._c.model.SessionUser;

@Transactional
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public boolean auth(SessionUser sessionUser) {
		return true;
	}

}
