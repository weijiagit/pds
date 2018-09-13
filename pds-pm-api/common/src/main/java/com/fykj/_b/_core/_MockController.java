package com.fykj._b._core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel.mock.Mock;

@Controller
@RequestMapping("/_mock_")
public class _MockController {

	
	@RequestMapping(path="/one",method=RequestMethod.GET)
	@ResponseBody
	@Mock(type=SessionUser.class)
	public InvokeResult one() {
		SessionUser sessionUser = sessionUser();
		return InvokeResult.success(sessionUser);
	}
	
	@RequestMapping(path="/array",method=RequestMethod.GET)
	@ResponseBody
	@Mock(type=SessionUser[].class)
	public InvokeResult array() {
		SessionUser sessionUser = sessionUser();
		return InvokeResult.success(sessionUser);
	}
	
	@RequestMapping(path="/page",method=RequestMethod.GET)
	@ResponseBody
	@Mock(type=SessionUser[].class , pageable=true)
	public InvokeResult page() {
		SessionUser sessionUser = sessionUser();
		return InvokeResult.success(sessionUser);
	}

	private SessionUser sessionUser() {
		SessionUser sessionUser=new SessionUser();
		sessionUser.setId("REAL-ID");
		sessionUser.setNatureName("REAL-NATURE-NAME");
		sessionUser.setPassword("REAL-PASSWORD");
		sessionUser.setUserName("REAL-USER-NAME");
		return sessionUser;
	}
	
	
	
}
