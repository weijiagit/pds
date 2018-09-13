package com.fykj.kernel._c._random;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.fykj.kernel._c._random.JRandomBinder.JDefaultBigDecimalRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultBooleanRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultDateRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultDoubleRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultFieldTypeRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultFloatRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultIntRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultLongRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultStringRandom;
import com.fykj.kernel._c._random.JRandomBinder.JDefaultTimestampRandom;

public class JTypeRandom<T> implements JRandom<JRandom<T>> {

	private final Class<?> clazz;

	public JTypeRandom(Class<?> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JRandom<T> random() {
		// use default.
		JRandom<?> random = null;
		Class<?> type = clazz;
		if (String.class == type) {
			random =  new JDefaultStringRandom();
		} else if (Integer.class == type || int.class == type) {
			random = new JDefaultIntRandom();
		} else if (Long.class == type || long.class == type) {
			random = new JDefaultLongRandom();
		} else if (Double.class == type || double.class == type) {
			random = new JDefaultDoubleRandom();
		} else if (Boolean.class == type || boolean.class == type) {
			random = new JDefaultBooleanRandom();
		} else if (Float.class == type || float.class == type) {
			random = new JDefaultFloatRandom();
		} else if (Date.class == type) {
			random = new JDefaultDateRandom();
		} else if (Timestamp.class == type) {
			random = new JDefaultTimestampRandom();
		} else if (BigDecimal.class == type) {
			random = new JDefaultBigDecimalRandom();
		} else {
			random = new JDefaultFieldTypeRandom(clazz);
		}
		return (JRandom<T>) random;
	}

}
