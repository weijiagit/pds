package com.fykj.kernel._c;

import com.fykj.kernel._c.model.SessionUser;

public interface AuthenticationService {

	boolean auth(SessionUser sessionUser);
	
}
