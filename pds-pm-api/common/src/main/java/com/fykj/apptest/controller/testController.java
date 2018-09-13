/**
 * 
 */
package com.fykj.apptest.controller;

import java.io.OutputStreamWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.apptest.model.testBean;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.util.JJSON;

/**
 * ClassName: testController
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月11日 上午10:18:49
 *
 */

@Controller
@RequestMapping("/c")
public class testController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(path="/getBeanById")
	@ResponseBody
	public InvokeResult getBeanById(String id){
		return InvokeResult.success(new testBean(id, "张三", "12", "sys"));
	}
	
	@RequestMapping(path="/getBeanByIds")
	@ResponseBody
	public void getBeanByIds(String id,String name,HttpServletResponse response) throws Exception{
		
		testBean testBean = new testBean(id, name, "12", "sys");
		toWrite(response, InvokeResult.success(testBean));
	}
	
	@RequestMapping(path="/canLoad",method =RequestMethod.GET)
	@ResponseBody
	public void canLoad(String token ,HttpServletResponse response) throws Exception{
		Boolean type = true;
		if(token !="123456"){
		type=false;
		}
		toWrite(response, InvokeResult.success(token));
	}
	
	/**
	 * 
	 * login:
	 * <pre>
	 *
	 * </pre>
	 * @param userName
	 * @param passWord
	 * @return
	 * @author 张军
	 * @throws Exception 
	 */
	@RequestMapping(path="/login")
	@ResponseBody
	public InvokeResult login(String userName,String passWord,HttpServletResponse response) throws Exception{
	return	InvokeResult.success("1234567890");
	}
	
	@RequestMapping(path="/save",method=RequestMethod.POST)
	@ResponseBody
	public void save(testBean testbean ,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		LOGGER.info("这个实体是{}----》",testbean);
		
		toWrite(response, InvokeResult.success("123456"));
		
	}
	
	@RequestMapping(path="/getPage",method=RequestMethod.POST)
	@ResponseBody
	public void getPage(int pageSize,int page,HttpServletResponse response) throws Exception{
		
		toWrite(response, InvokeResult.success("123456"));
	}
	
	private void toWrite(HttpServletResponse response,InvokeResult ir) throws Exception{
		String formatObject = JJSON.get().formatObject(ir);
		response.addHeader("Access-Control-Allow-Origin", "*");
		ServletOutputStream outputStream = response.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
		outputStreamWriter.write(formatObject);
		outputStreamWriter.flush();
		outputStreamWriter.close();
	}
}
