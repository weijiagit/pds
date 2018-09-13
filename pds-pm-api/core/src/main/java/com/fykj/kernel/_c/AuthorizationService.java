package com.fykj.kernel._c;

import com.fykj.kernel._c.model.SessionUser;

public interface AuthorizationService<T> {

	boolean authorise(SessionUser sessionUser, T source);
	
}
