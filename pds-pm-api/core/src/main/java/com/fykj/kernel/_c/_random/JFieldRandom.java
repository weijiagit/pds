package com.fykj.kernel._c._random;

import java.util.Date;

import com.fykj.kernel._c._random.JRandomBinder.JDefaultDateRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultStringRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultTimestampRandom;
import com.fykj.kernel._c.meta.JDefaultFieldMeta;
import com.fykj.util.JDateUtils;

public class JFieldRandom<T> implements JRandom<JRandom<T>> {

	private final JDefaultFieldMeta fieldMeta;
	
	public JFieldRandom(JDefaultFieldMeta fieldMeta) {
		this.fieldMeta = fieldMeta;
	}

	@Override
	public JRandom<T> random() {
		JRandom<?> random = null;
		Class<?> type = fieldMeta.getType();
		if (String.class == type) {
			String fieldName=fieldMeta.getFieldName();
			if(fieldName.contains("Date")){
				random = new JDefaultStringRandom(){
					@Override
					public String random() {
						Date date=new JDefaultDateRandom().random();
						return JDateUtils.format(date);
					}
				};
			}else if(fieldName.contains("Time")){
				random = new JDefaultStringRandom(){
					@Override
					public String random() {
						Date date=new JDefaultTimestampRandom().random();
						return JDateUtils.format(date,"yyyy-MM-dd HH:mm:ss");
					}
				};
			}else {
				random =  new JDefaultStringRandom();
			}
		} else{
			random=new JTypeRandom<>(fieldMeta.getType());
		}
		return (JRandom<T>) random;
	}

}
