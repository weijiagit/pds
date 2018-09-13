/**
 * 
 */
package com.fykj._b._core.message.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fykj._b._core.message.model.Message;
import com.fykj._b._core.message.service.MessageService;
import com.fykj._b._core.sms.SmsContent;
import com.fykj._b._core.sms.model.SmsParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.message.constant.MessageServiceType;
import com.fykj._b._core.message.constant.SendMessageSingle;
import com.fykj._b._core.message.vo.MessageOutVO;
import com.fykj._b._core.sms.SendSmsService;
import com.fykj._b._core.sms.impl.SmsDxyzmImpl;
import com.fykj._b._core.sms.impl.SmsXgmsImpl;
import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.JPageUtil;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;

/**
 * ClassName: MessageServiceImpl
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月18日 下午4:15:41
 *
 */
@Service
@Transactional
public class MessageServiceImpl extends ServiceSupport implements MessageService {

	
	private SingleEntityManager<Message> internalMessageServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(Message.class);

	/* (non-Javadoc)
	 * @see MessageService#getIdentifyCode(java.lang.String, java.lang.String, int, MessageServiceType)
	 */
	@Override
	public boolean getIdentifyCode(String phoneNum, String formSource, int modifyNum, MessageServiceType serveType) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONDAY), calendar.get(calendar.DATE),0,0,0);
		Date time = calendar.getTime();
		List<Message> models = internalMessageServiceImpl.singleEntityQuery2().conditionDefault().equals("phoneNum", phoneNum).equals("formSource",formSource)
			.equals("serveType", serveType.toString()).largerAndEquals("createDate", time).ready().models();
			if(models.size() > 5){
				throw new BusinessException("获取验证码次数过多");//获取验证码次数过多
			}
		List<Message> _models = internalMessageServiceImpl.singleEntityQuery2().conditionDefault().equals("phoneNum", phoneNum).equals("formSource",formSource)
					.equals("serveType", serveType.toString()).equals("hasValidity",true).ready().models();
			
					for (Message model : _models) {
						model.setHasValidity(false);
						internalMessageServiceImpl.updateOnly(model);
					}
		Message dbSource = new Message();
				dbSource.setCode(getRandomString(5));
				dbSource.setPhoneNum(phoneNum);
				dbSource.setFormSource(formSource);
		Calendar ca = Calendar.getInstance();
						ca.add(Calendar.MINUTE, modifyNum);
				dbSource.setExpireDate(ca.getTime());
				dbSource.setServeType(serveType.toString());
				internalMessageServiceImpl.saveOnly(dbSource);
			return true;
		}
	

	/**
	 * 获取随机字符串
	 * getRandomString:
	 * <pre>
	 *
	 * </pre>
	 * @param num
	 * @return
	 * @author 张军
	 */
	private String getRandomString(int num){
		Random ra = new Random();
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',      
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',      
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };    
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			
			sb.append(codeSequence[ra.nextInt(codeSequence.length)]);
		}
		return sb.toString();
	}

	
	
	@Override
	public List<Message> getSysSendMessage() {

		List<Message> _models = internalMessageServiceImpl.singleEntityQuery2().conditionDefault().equals("hasSend",false).ready().models();
		
		return _models;
	}


	/* (non-Javadoc)
	 * @see MessageService#getMessage(java.lang.String, MessageServiceType)
	 */
	@Override
	public Message getMessage(String phoneNum, MessageServiceType serveType) {
		Message model = internalMessageServiceImpl.singleEntityQuery2().conditionDefault().equals("phoneNum", phoneNum)
		.equals("serveType", serveType.toString()).equals("hasValidity",true).ready().model();
		return model;
	}

	/* (non-Javadoc)
	 * @see MessageService#checkCode(java.lang.String, java.lang.String, MessageServiceType)
	 */
	@Override
	public boolean checkCode(String phoneNum, String Code, MessageServiceType serveType) {
		Message message = getMessage(phoneNum, serveType);
		if(null==message){ //--手机号获取不到验证码
			throw new BusinessException("验证码错误");//验证码错误
		}
		if(!Code.equalsIgnoreCase(message.getCode())){
			throw new BusinessException("验证码错误");//验证码错误
		}
		Date expireDate = message.getExpireDate();
		if(expireDate.getTime() < new Date().getTime()){
			throw new BusinessException("验证码过期");//验证码过期
		}
		message.setHasValidity(false);
		internalMessageServiceImpl.updateOnly(message);
		return true;
	}


	/* (non-Javadoc)
	 * @see MessageService#sysSendMessage()
	 */
	@Override
	public void sysSendMessage() {
		if(SendMessageSingle.get().canSend()){
			List<Message> sysSendMessage = getSysSendMessage();
			//MessageServiceType;
			for (Message message : sysSendMessage) {
				SmsContent smsc ;
				switch (message.getServeType()) { 
				case "getPassWord":
					smsc = new SmsXgmsImpl();
					
					break;
				case "saveParty":
					smsc = new SmsDxyzmImpl();
					break;
				default:
					smsc = new SmsDxyzmImpl();
					break;
				}
				   SmsParams sps = new SmsParams();
		    	   String[] array = {message.getCode()};
		    	   sps.setMobile(message.getPhoneNum());
		    	   sps.setContent(array);
		    	   String sendMessage = SendSmsService.sendMessage(sps, smsc);
		    	   upDateHasSend(message.getId(), sendMessage);
			}
			
			SendMessageSingle.get().stopSend();
		}else{
			LOGGER.error("待处理数据量过大！！！");
			
		}
		
		
	}


	public SingleEntityManager<Message> getInternalMessageServiceImpl() {
		return internalMessageServiceImpl;
	}


	public void setInternalMessageServiceImpl(SingleEntityManager<Message> internalMessageServiceImpl) {
		this.internalMessageServiceImpl = internalMessageServiceImpl;
	}


	/* (non-Javadoc)
	 * @see MessageService#upDateHasSend(java.lang.String)
	 */
	@Override
	public void upDateHasSend(String id,String sendResult) {

		Message byId = internalMessageServiceImpl.getById(id, Message.class);
		byId.setHasSend(true);
		byId.setSendResult(sendResult);
		internalMessageServiceImpl.updateOnly(byId);
		
	}


	@Override
	public JPage<MessageOutVO>  getMessages(Message message, SimplePageRequest pages) {
		
		JPage<?> modelPage = internalMessageServiceImpl.singleEntityQuery2().conditionDefault().equals( "formSource" , message.getFormSource())
		.equals("serveType", message.getServeType()).equals("hasSend", message.isHasSend()).likes("phoneNum", message.getPhoneNum()).ready()
		.modelPage(pages);
		List<Message> content = (List<Message>) modelPage.getContent();
		List<MessageOutVO> c2 = new ArrayList<MessageOutVO>();
			for (Message message2 : content) {
				MessageOutVO simpleCopy = Copy.simpleCopy(message2, MessageOutVO.class);
				simpleCopy.setExpireDateForMat(JDateUtils.format(simpleCopy.getExpireDate(), JDateUtils.parseDate));
				c2.add(simpleCopy);
			}
			JPageUtil.replaceConent(modelPage, c2);
		return (JPage<MessageOutVO>) modelPage;
		
	}

}
