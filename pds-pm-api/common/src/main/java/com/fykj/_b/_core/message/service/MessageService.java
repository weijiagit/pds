/**
 * 
 */
package com.fykj._b._core.message.service;

import java.util.List;

import com.fykj._b._core.message.constant.MessageServiceType;
import com.fykj._b._core.message.model.Message;
import com.fykj._b._core.message.vo.MessageOutVO;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;

/**
 * 系统发送短信 接口
 * ClassName: MessageService
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月18日 下午3:53:34
 *
 */

public interface MessageService {
	
	/**
	 * 获取系统业务手机验证码
	 * getIdentifyCode:
	 * <pre>
	 *
	 * </pre>
	 * @param PhoneNum
	 * @param formSource
	 * @param modifyNum
	 * @param serveType
	 * @return
	 * @author 张军
	 */
	public boolean getIdentifyCode(String phoneNum, String formSource,int  modifyNum,MessageServiceType serveType);
	
	/**
	 * 根据用户类型获取最新message
	 * getMessage:
	 * <pre>
	 *
	 * </pre>
	 * @param phoneNum
	 * @param serveType
	 * @return
	 * @author 张军
	 */
	public Message getMessage(String phoneNum,MessageServiceType serveType);
	
	/**
	 * 校验验证码
	 * 
	 *  成功返回 true 失败  throw 异常
	 * checkCode:
	 * <pre>
	 *
	 * </pre>
	 * @param phoneNum
	 * @param Code
	 * @param serveType
	 * @return
	 * @author 张军
	 */
	public boolean checkCode(String phoneNum,String Code, MessageServiceType serveType);
	
	/**
	 * 获取未发送的数据
	 * getSysSendMessage:
	 * <pre>
	 *
	 * </pre>
	 * @return
	 * @author 张军
	 */
	public List<Message> getSysSendMessage();
	
	/**
	 * 把未发送的短信修改成发送后的
	 * upDateHasSend:
	 * <pre>
	 *
	 * </pre>
	 * @param id
	 * @author 张军
	 */
	public void upDateHasSend(String id,String sendResult);
	
	
	/**
	 * 系统发送短信方法
	 * 单线程处理
	 * sysSendMessage:
	 * <pre>
	 *
	 * </pre>
	 * @author 张军
	 */
	public void sysSendMessage();
	
	/**
	 * 管理后台获取短信接口
	 * getMessages:
	 * <pre>
	 *
	 * </pre>
	 * @param message  formSource serveType hasSend
	 * @param pages
	 * @author 张军
	 */
	public JPage<MessageOutVO>  getMessages(Message message,SimplePageRequest pages);

}
