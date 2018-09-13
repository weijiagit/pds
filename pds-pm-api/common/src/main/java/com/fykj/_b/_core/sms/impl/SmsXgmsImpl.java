/**
 * 
 */
package com.fykj._b._core.sms.impl;

import com.fykj._b._core.sms.SmsContent;

/**
 * ClassName: SmsXgmsImpl
 * <pre>
 * Function: 修改密码
 * </pre>
 * @author 张军
 * @Date 2017年5月31日 上午11:15:59
 *
 */

public class SmsXgmsImpl implements SmsContent {

	/* (non-Javadoc)
	 * @see SmsContent#getContent(java.lang.String, java.lang.String[])
	 */
	@Override
	public String getContent( String[] ms) {
		String replace;
		if(ms.length>0){
			replace = SmsXgmsImpl.xgmsy.replace("?", ms[0]);
			return replace;
		}
		return null;
	}

}
