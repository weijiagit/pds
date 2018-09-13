/**
 * 
 */
package com.fykj._b._core.sms.impl;

import com.fykj._b._core.sms.SmsContent;

/**
 * 短信验证
 * ClassName: SmsContentImpl
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月31日 上午11:13:09
 *
 */

public class SmsDxyzmImpl  implements SmsContent {

	/* (non-Javadoc)
	 * @see SmsContent#getContent(java.lang.String, java.lang.String[])
	 */
	@Override
	public String getContent(String[] ms) {
		String replace;
		if(ms.length>0){
			replace = SmsDxyzmImpl.dxyzm.replace("?", ms[0]);
			return replace;
		}
		return null;
	}

}
