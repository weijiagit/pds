package com.fykj.kernel._c.cache;



public class SimpleStringIdentifierGenerator  extends AbstractIdentifierGenerator implements IdentifierGenerator {

	private static final String namesapce="/string/";
	
	@Override
	public String namespace() {
		return namesapce;
	}
	
	@Override
	public String getKey(Object object) {
		if(object==null){
			throw new IllegalArgumentException("IS NULL");
		}
		return String.valueOf(object);
	}

}
