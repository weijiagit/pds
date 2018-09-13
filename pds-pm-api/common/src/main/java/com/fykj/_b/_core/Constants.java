package com.fykj._b._core;

/**
 * @author JIAZJ
 *
 */
public interface Constants extends com.fykj.kernel._c.Cs {
	
	public static final int PAGE_SIZE = 100;
	
	public static final int CURRENT_PAGE = 0;
	
	public static final String REGEX_ORGANIZATION_CODE = "^[0-9A-Z]{8}-[0-9|X]$";
	
	public static final String REGEX_THREE_UNION_CODE = "^([0-9A-Z]){18}$";
	
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String SLASH = "/";
	
	public static final String FULL_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	
	/**
	 * 是否启用
	 * @author Lx
	 *
	 */
	public interface isEnable{
		String codeType = "isEnable";
		
		String enable = "1";
		String disable ="0";
	}

	/**
	 * 日志类型
	 * @author weijia
	 *
	 */
	public interface logType{
		String LOGTYPE = "LOGTYPE";

		String LOGIN_TYPE = "0";//登录日志
		String DEL_TYPE ="1";//删除日志
	}

	/**
	 * 模块名称
	 * @author weijia
	 *
	 */
	public interface projectModule{
		String PROJECTMODULE = "PROJECTMODULE";

		String PROJECT_ATTRACT = "基础项目库招商项目";//基础项目库招商项目
		String PROJECT_BASE ="基础项目库基础项目";//基础项目库基础项目
		String NOTICE = "通知公告";//通知公告
		String WORKDYNAMICE = "工作动态";//工作动态
		String LEADERMESSAGE = "领导指示";//领导指示
		String TASK = "项目推进";//项目推进
		String DEPARTPROJECT = "部门上报项目";//部门上报项目
	}

	/**
	 * OA消息接口常量
	 * @author weijia
	 *
	 */
	public interface OAMessageType{
		String OAMESSAGETYPE = "OAMESSAGETYPE";

		String SEND_USER = "admin";
		String CONTENT = "项目管理系统中，您有待处理工作，请进入项目管理系统的“待办工作”中进行查看。";//
		String BIZ_SYSTEM ="项目管理系统";//
		String SEND_MESSAGE_SUCCESS = "发送消息至OA系统成功！";
		String SEND_MESSAGE_FAIL = "发送消息至OA系统失败！";
	}
}
