/**
 * 
 */
package com.fykj._b._core.message.constant;


/**
 *  单例  让系统只能单独 一条线程执行 发送信息
 * ClassName: SendMessageSingle
 * <pre>
 * Function:
 * 
 * </pre>
 * @author 张军
 * @Date 2017年5月31日 下午3:21:56
 *
 */

public class SendMessageSingle {

	private boolean canSend = true;
	
	private static SendMessageSingle entity;
	
	public static SendMessageSingle  get(){
		if (null == entity ){
			synchronized (SendMessageSingle.class) {
				if(null == entity){
					entity = new SendMessageSingle();
				}
			}
		}
		return entity;
	}
	
	public synchronized boolean canSend(){
		if(canSend){
			canSend = false;
			return true;
		}
		return false;
	}
	
	public synchronized void stopSend(){
		canSend = true;
	}
	
	
}
