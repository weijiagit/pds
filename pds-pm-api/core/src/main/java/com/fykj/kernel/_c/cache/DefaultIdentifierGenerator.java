package com.fykj.kernel._c.cache;



public class DefaultIdentifierGenerator extends AbstractIdentifierGenerator implements IdentifierGenerator {

	@Override
	protected String getKey(Object object) {
		return String.valueOf(object.hashCode());
	}
	
	private static final String namesapce="/hashcode/";
	
	@Override
	public String namespace() {
		return namesapce;
	}

}
