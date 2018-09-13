package com.fykj.pds.log.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.departProject.vo.DepartProjectPageOutVO;
import com.fykj.pds.log.model.LoginMessage;
import com.fykj.pds.log.vo.LoginMessagePageInVO;
import com.fykj.pds.log.vo.LoginMessagePageOutVO;

public interface LoginMessageService {

	public LoginMessage saveLoginMessage(String logType,SessionUser sessionUser,String projectObject,String projectModule);
	
	public void updateLoginMessage(LoginMessage loginMessage);
	
	public LoginMessage getLoginMessage(String id);

	/**
	 * 登录log日志查询
	 *
	 * @param vo
	 * @param page
	 * @return
	 */
	public JPage<LoginMessagePageOutVO> getLoginMessagePage(LoginMessagePageInVO vo, SimplePageRequest page);

	/**
	 * 部门日志详情
	 *
	 * @param id
	 * @return
	 */
	LoginMessagePageOutVO getLoginMessageById(String id);

}
