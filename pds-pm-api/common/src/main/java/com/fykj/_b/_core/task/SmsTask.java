/**
 * 
 */
package com.fykj._b._core.task;

import com.fykj._b._core.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName: SmsTask
 * <pre>
 * Function: 短信发送task
 * </pre>
 * @author 张军
 * @Date 2017年5月31日 下午2:48:17
 *
 */

@Component("taskJob")
public class SmsTask {

	@Autowired
    MessageService messageService;
	
	/**
	 * 短信发送 10S 跑一批
	 * jobSms:
	 * <pre>
	 *
	 * </pre>
	 * @author 张军
	 */
	   //@Scheduled(cron = "0/30 * * * * ?")
	    public void jobSms() {
		 /*  SimpleDateFormat sd = new SimpleDateFormat("yyyy MM dd HH mm ss");

	        System.out.println("认证执行中。。。"+sd.format(new Date()));  */
		   messageService.sysSendMessage();

	    }
}
