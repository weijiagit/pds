package com.fykj.pds.project;

import com.fykj.CodesTable;

public interface ProjectCodesTable extends CodesTable {

	public interface ImportentProject{
		String CODE = "IMPORTENT_PROJECT";//是否重点项目
		String PROVINCE = "1";  //省重点项目;
		String CITY= "2";  //市重点项目;
		String DISTRICT= "3";  //区重点项目;
	}

	public interface Isprovinceimp{
		String CODE = "IS_PROVINCEIMP";//是否省重点项目
		String YES = "1";  //是;
		String NO= "2";  //否;
	}

	public interface Iscityimp{
		String CODE = "IS_CITYIMP";//是否市重点项目
		String YES = "1";  //是;
		String NO= "2";  //否;
	}

	public interface Isdistrictimp{
		String CODE = "IS_DISTRICTIMP";//是否区重点项目
		String YES = "1";  //是;
		String NO= "2";  //否;
	}

	public interface Isimp{
		String CODE = "IS_IMP";//是否重点项目
		String YES = "1";  //是;
		String NO= "2";  //否;
	}

	public interface RadioList{
		String CODE = "RADIO_LIST";//单选是否
		String YES = "1";  //是;
		String NO= "2";  //否;
	}

	public interface ImplementSchedule{
		String CODE = "IMPLEMENT_SCHEDULE";//实施进度
		String NOTSTARTED = "0";  //未开始项目
		String BEFORE = "1";  //前期
		String PROCEED= "2";  //新开工
		String CONTINUE= "3";  //续建
		String END= "4";  //竣工
	}
	
	public interface IndustryClassification{
		String CODE = "INDUSTRY_CLASSIFICATION";//行业分类
		String INDUSTRY = "1";  //工业
		String BASE = "2";  //基础设施
		String SERVICE = "3";  //服务业
		String PPP = "4";  //PPP项目
		String REALTY = "5";  //房地产
		String MEDICAL = "6";  //医疗卫生
		String EDUCATIOn = "7";  //教育
		String AGRICULTURE = "8";  //农林水利
		String OTHER = "9";  //其他
	}

	public interface ProjectFillStatus{
		String CODE = "PROJECTFILL_STATUS";//项目填写进度
		String NORMAL = "1";  //正常
		String DRAFT = "2";  //草稿
	}

	public interface BackFlag{
		String CODE = "BACK_FLAG";//退回标志
		String NO= "0";  //非退回;
		String YES = "1";  //退回;

	}

	public interface GetAttractProjectFlag{
		String CODE = "GET_ATTRACT_PROJECT_FLAG";//退回标志
		String NO= "-1";  //录入失败，系统错误！;
		String DUPLICATE = "0"; //录入失败，已录入系统！
		String YES = "1";  //录入成功！;
	}

	public interface RejectAttractProjectReason{
		String REASON= "此项目不符合要求";
	}

	public interface ProjectAttribute{
		String CODE = "PROJECT_ATTRIBUTE";//项目属地
		String ZHDZ= "1";  //遵化店镇;
		String HTJDBSC = "2";  //皇台街道办事处;
		String GXQ = "3";  //高新区;
		String QT = "4";  //其他;

	}

	public interface ConstructionNature{
		String CODE = "CONSTRUCTION_NATURE";//建设性质
		String XJ= "1";  //新建;
		String KJ = "2";  //扩建;
		String GJ = "3";  //改建;
		String QJ = "4";  //迁建;
		String JSGZ = "5";  //技术改造;

	}
	
	public interface IS_ZS{
		String YES= "1";  //是招商前期项目;
		String NO = "2";  //不是招商前期项目;
	}

	public interface IS{
		String YES= "1";  //是;
		String NO = "2";  //不是;
	}

	public interface IsString{
		String YES= "是";  //是;
		String NO = "否";  //不是;
	}

	public interface ApproveStatus{
		String CODE = "APPROVES_TATUS";//审批状态
		String NONEED= "1";  //无需办理;
		String NOBEGIN = "2";  //未开始;
		String PROCESSING = "3";  //受理中;
		String PROBLEM = "4";  //存在问题;
		String CLOSE = "5";  //完结;
	}

	public interface ProjectAdmin{
		String PROJECTADMIN= "项目管理员";
	}

	public interface ProjectExcelReport {
		String SHEET_NAME = "项目详细信息";
		String SUFFIX = ".xls";
	}

	public interface ProjectTableField{
		String CODE = "PROJECT_TABLE_FIELD";//项目表字段
		String PROJECT_NUMBER= "project_number";
		String COMPANY_NAME= "company_name";
		String ORGANIZATION_CODE = "organization_code";
		String PROJECT_ATTRIBUTE = "project_attribute";
		String CONSTRUCTION_NATURE = "construction_nature";
		String INDUSTRY_CLASSIFICATION = "industry_classification";
		String IMPLEMENT_SCHEDULE = "implement_schedule";
		String PLAN_BEGIN_DATE = "plan_begin_date";
		String PLAN_END_DATE = "plan_end_date";
		String ACTUAL_BEGIN_DATE = "actual_begin_date";
		String ACTUAL_END_DATE = "actual_end_date";
		String TOTAL_INVESTMENT = "total_investment";
		String ENTERPRISE_LEGAL_PERSON = "enterprise_legal_person";
		String ENTERPRISE_CONTACT_PERSON = "enterprise_contact_person";
		String ENTERPRISE_CONTACT_PHONE = "enterprise_contact_phone";
		String ENTERPRISE_CHARGE_PERSON = "enterprise_charge_person";
		String ENTERPRISE_CHARGE_PHONE = "enterprise_charge_phone";
		String CONSTRUCTION_CONTENT_SCALE = "construction_content_scale";
		String ISPPP_PROJECT = "isppp_project";
		String ISPLAN_NEW_WORK = "isplan_new_work";
		String ISPLAN_COMPLETE = "isplan_complete";
		String ISPROVINCEIMP = "isprovinceimp";
		String ISCITYIMP = "iscityimp";
		String ISDISTRICTIMP = "isdistrictimp";
		String SUBCONTRACT_LEADER = "subcontract_leader";
		String SUBCONTRACT_COMPANY1 = "subcontract_company1";
		String SUBCONTRACT_COMPANY1_PERSON = "subcontract_company1_person";
		String SUBCONTRACT_COMPANY1_PERSON_PHONE = "subcontract_company1_person_phone";
		String SUBCONTRACT_COMPANY1_CONTACT = "subcontract_company1_contact";
		String SUBCONTRACT_COMPANY1_CONTACT_PHONE = "subcontract_company1_contact_phone";
		String SUBCONTRACT_COMPANY2 = "subcontract_company2";
		String SUBCONTRACT_COMPANY2_PERSON = "subcontract_company2_person";
		String SUBCONTRACT_COMPANY2_PERSON_PHONE = "subcontract_company2_person_phone";
		String SUBCONTRACT_COMPANY2_CONTACT = "subcontract_company2_contact";
		String SUBCONTRACT_COMPANY2_CONTACT_PHONE = "subcontract_company2_contact_phone";
		String SUBCONTRACT_COMPANY3 = "subcontract_company3";
		String SUBCONTRACT_COMPANY3_PERSON = "subcontract_company3_person";
		String SUBCONTRACT_COMPANY3_PERSON_PHONE = "subcontract_company3_person_phone";
		String SUBCONTRACT_COMPANY3_CONTACT = "subcontract_company3_contact";
		String SUBCONTRACT_COMPANY3_CONTACT_PHONE = "subcontract_company3_contact_phone";
		String ISSITESELECT = "issiteselect";
		String ISPROBLEM = "isproblem";
		String ISSTATELAND = "isstateland";
		String ISPEOPLEDEFENCE = "ispeopledefence";
		String ISPROJECTAPPROVE = "isprojectapprove";
		String ISCHECKAPPROVE = "ischeckapprove";
		String ISBUILDPROJECT = "isbuildproject";
		String ISSIMULATEAPPROVE = "issimulateapprove";
		String ISENVIRONMENTAPPROVE = "isenvironmentapprove";
		String ISCULTURALRELICS = "isculturalrelics";
		String ISWEATHER = "isweather";
		String ISANTIKNOCK = "isantiknock";
		String ISENERGYCONSERVATION = "isenergyconservation";
		String ISPARKAFFOREST = "isparkafforest";
		String ISFIRECONTROL = "isfirecontrol";
		String ISSIMULATEFINISH = "issimulatefinish";
		String ISBUILDLAND = "isbuildland";
		String TRANSFER_STATUS = "transfer_status";
		String QUESTION_CONTENT ="question_content";
        String TOTAL_INVESTMENT_FLAG = "总投资";
	}
}
