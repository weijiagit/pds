package com.fykj._b._core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel.Codes;
import com.fykj.kernel._c.model.InvokeResult;

@Controller
@RequestMapping("/cfg")
public class ConfigController {

	@Autowired
	private Codes codes;
	
	@RequestMapping(path="/errorCode",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult errorCode() {
		return InvokeResult.success(codes);
	}
	
}
