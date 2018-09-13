package com.fykj.kernel._c.model;

import java.util.List;

public  abstract class JPageUtil {

	public static void replaceConent(JPage<?> page,  List<?> content){
		if(page instanceof JImpl<?>){
			JImpl<?> simplePageImpl=(JImpl<?>)page;
			simplePageImpl.setContent(content);
		}
	}
	
	static public JPage wrap(List content,Long total, SimplePageRequest simplePageRequest){
		JImpl page=new JImpl();
		JPageUtil.replaceConent(page, content);
		page.setTotalRecordNumber(total);
		page.setPageable(simplePageRequest);
		page.caculatePageNumber();
		return page;
	}
	
	
	
}
