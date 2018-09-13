package com.fykj._b._core.sysparam.service;

import java.util.List;

import com.fykj._b._core.sysparam.model.SysParam;
import com.fykj._b._core.sysparam.vo.SysParamCriteriaInVO;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;

public interface SysParamService {

	void saveSysParam(SysParam sysParam);
	
	void updateSysParam(SysParam sysParam);
	
	void deleteSysParam(SysParam sysParam);
	
	void deleteSysParamById(String id);
	
	void deleteSysParams(String [] ids);
	
	SysParam getSysParamById(String id);
	
	JPage<SysParam> getSysParams(SysParamCriteriaInVO sysParamCriteriaInVO, SimplePageRequest simplePageRequest);
	
	boolean exists(String code);
	
	List<SysParam> loadSysParam();
}
