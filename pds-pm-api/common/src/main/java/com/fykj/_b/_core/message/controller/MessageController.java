/**
 * 
 */
package com.fykj._b._core.message.controller;

import com.fykj._b._core.message.constant.MessageServiceType;
import com.fykj._b._core.message.model.Message;
import com.fykj._b._core.message.service.MessageService;
import com.fykj._b._core.message.vo.MessageInVO;
import com.fykj._b._core.sysuser.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * ClassName: MessageController
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月18日 下午1:59:27
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
    MessageService messageService;
	
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 获取用户注册验证码短信验证码
	 * getIdentifyCode:
	 * <pre>
	 *
	 * </pre>
	 * @param sysParamAddInVO
	 * @return
	 * @throws Exception
	 * @author 张军
	 */
	@ResponseBody
	@RequestMapping(path="/getIdentifyCode",method=RequestMethod.GET)
	public InvokeResult getIdentifyCode(MessageInVO messageInVo) throws Exception {
		if(StringUtils.isNotEmpty(messageInVo.getPhoneNum()) && !sysUserService.exists(messageInVo.getPhoneNum())){
			
			LOGGER.info("===获取验证码用户--》 {}",messageInVo.getPhoneNum());
			return InvokeResult.success(messageService.getIdentifyCode(messageInVo.getPhoneNum(), messageInVo.getFormSource(), 10, MessageServiceType.saveParty));
		} else{
			return InvokeResult.success(false);
		}
	
}
	
	/**
	 * 获取 找回密码 手机验证码
	 * getPasswordIdentifyCode:
	 * <pre>
	 *
	 * </pre>
	 * @param messageInVo
	 * @return
	 * @throws Exception
	 * @author 张军
	 */
	@ResponseBody
	@RequestMapping(path="/getPasswordIdentifyCode",method=RequestMethod.GET)
	public InvokeResult getPasswordIdentifyCode(MessageInVO messageInVo) throws Exception {
		if(StringUtils.isNotEmpty(messageInVo.getPhoneNum())){
			
			LOGGER.info("===获取修改密码手机验证码--》 {}",messageInVo.getPhoneNum());
			return InvokeResult.success(messageService.getIdentifyCode(messageInVo.getPhoneNum(), messageInVo.getFormSource(), 10, MessageServiceType.getPassWord));
		} else{
			return InvokeResult.success(false);
		}
	}
	
	/**
	 * 获取短信信息
	 * getMessagePages:
	 * <pre>
	 *
	 * </pre>
	 * @param message formSource serveType hasSend
	 * @param pvo
	 * @return
	 * @author 张军
	 */
	@ResponseBody
	@RequestMapping(path="/getMessagePages",method=RequestMethod.GET)
	public InvokeResult getMessagePages(Message message, SimplePageRequestVO pvo){
		
		return InvokeResult.success( messageService.getMessages(message, new SimplePageRequest(pvo.getPage(), pvo.getSize())));
	}
	
	}
