package com.fykj.pds.project.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fykj._b._core.Constants;
import com.fykj._b._core.attachment.model.Attachment;
import com.fykj._b._core.attachment.service.AttachmentService;
import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj._b._core.cache.DictionaryCache;
import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.kernel.springjpa.query2.JSingleEntityQueryMeta.SqlType;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.interfaceManage.InterfaceManager;
import com.fykj.pds.interfaceManage.vo.OAMessageOutVO;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.project.ProjectCodesTable;
import com.fykj.pds.project.ProjectCodesTable.*;
import com.fykj.pds.project.model.*;
import com.fykj.pds.project.model.ProjectTableField;
import com.fykj.pds.project.service.ProjectService;
import com.fykj.pds.project.vo.*;
import com.fykj.pds.work.backlogWork.constant.BacklogWorkContant;
import com.fykj.pds.work.backlogWork.service.BacklogWorkService;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkAddInVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import com.fykj.util.JJSON;
import com.fykj.util.JStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class ProjectServiceImpl extends ServiceSupport implements ProjectService {

    public static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private SingleEntityManager<AttractProject> internalAttractProjectServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(AttractProject.class);

    private SingleEntityManager<Project> internalProjectServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Project.class);

    private SingleEntityManager<ProjectTableField> internalProjectTableFieldServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(ProjectTableField.class);

    private SingleEntityManager<DepartGovernment> internalDepartGovernmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(DepartGovernment.class);

    private SingleEntityManager<ProjectDepartment> internalProjectDepartmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(ProjectDepartment.class);

    private SingleEntityManager<ProjectDepartmentHistory> internalProjectDepartmentHistoryServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(ProjectDepartmentHistory.class);

    private SingleEntityManager<SysUser> internalSysUserServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(SysUser.class);

    private SingleEntityManager<Attachment> attachmentEntityManager = SingleEntityManagerGetter.get()
            .getInstance(Attachment.class);


    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DictionaryCache dictionaryCache;

    @Autowired
    private BacklogWorkService backlogWorkService;

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private LoginMessageService loginMessageService;

    @Autowired
    private InterfaceManager interfaceManager;

    /**
     * the return one is same as the parameter
     *
     * @param projectRecordVO
     * @return
     */
    private ProjectRecordVO then(ProjectRecordVO projectRecordVO) {

        //时间转换
        if (null == projectRecordVO.getPlanBeginDate()) {
            projectRecordVO.setPlanBeginDateStr("");
        } else {
            projectRecordVO.setPlanBeginDateStr(JDateUtils.format(projectRecordVO.getPlanBeginDate()));
        }

        if (null == projectRecordVO.getPlanEndDate()) {
            projectRecordVO.setPlanEndDateStr("");
        } else {
            projectRecordVO.setPlanEndDateStr(JDateUtils.format(projectRecordVO.getPlanEndDate()));
        }

        if (null == projectRecordVO.getActualBeginDate()) {
            projectRecordVO.setActualBeginDateStr("");
        } else {
            projectRecordVO.setActualBeginDateStr(JDateUtils.format(projectRecordVO.getActualBeginDate()));
        }

        if (null == projectRecordVO.getActualEndDate()) {
            projectRecordVO.setActualEndDateStr("");
        } else {
            projectRecordVO.setActualEndDateStr(JDateUtils.format(projectRecordVO.getActualEndDate()));
        }

        //字典转换
        projectRecordVO.setProjectAttributeStr(dictionaryCache.getDictDataName(ProjectAttribute.CODE, projectRecordVO.getProjectAttribute()));
        projectRecordVO.setConstructionNatureStr(dictionaryCache.getDictDataName(ConstructionNature.CODE, projectRecordVO.getConstructionNature()));
        projectRecordVO.setIndustryClassificationStr(dictionaryCache.getDictDataName(IndustryClassification.CODE, projectRecordVO.getIndustryClassification()));
        projectRecordVO.setImplementScheduleStr(dictionaryCache.getDictDataName(ImplementSchedule.CODE, projectRecordVO.getImplementSchedule()));

        return projectRecordVO;
    }


	/**
	 * 获取基础项目信息
	 * @return
	 */
    @Override
    public JPage<ProjectRecordVO> getProjects(ProjectCriteriaVO projectCriteriaVO, SimplePageRequest simplePageRequest) {

        String jpql = internalProjectServiceImpl.selectCause(SqlType.NATIVE, "a") + " from t_project a "
                + " where  a.is_available = :isAvailable ";
        Map<String, Object> params = new HashMap<>();
        params.put("isAvailable", Availability.available.ordinal());

        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getProjectName())) {
            params.put("projectName", "%" + projectCriteriaVO.getProjectName() + "%");
            jpql = jpql + " and a.project_name like :projectName ";
        }

        //是否重点项目
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getImportentProjectIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getImportentProjectIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });

            jpql = jpql + " and (";
            for (String id : ids) {
                if (id.trim().equals(ImportentProject.PROVINCE)) {
                    jpql = jpql + " a.isprovinceimp = :isprovinceimp ";
                    params.put("isprovinceimp", Isprovinceimp.YES);
                } else if (id.trim().equals(ImportentProject.CITY)) {
                    jpql = jpql + " a.iscityimp = :iscityimp ";
                    params.put("iscityimp", Iscityimp.YES);
                } else if (id.trim().equals(ImportentProject.DISTRICT)) {
                    jpql = jpql + " a.isdistrictimp = :isdistrictimp ";
                    params.put("isdistrictimp", Isdistrictimp.YES);
                }
                jpql = jpql + " or ";
            }
            jpql = jpql + " 1!=1 )";
        }

        //实施进度
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getImplementScheduleIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getImplementScheduleIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("implementScheduleIds", ids);
            jpql += " and a.implement_schedule in ( :implementScheduleIds ) ";
        }

        //行业分类
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getIndustryClassificationIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getIndustryClassificationIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("industryClassificationIds", ids);
            jpql += " and a.industry_classification in ( :industryClassificationIds ) ";
        }

        //项目填写状态
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getProjectFillStatusIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getProjectFillStatusIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("projectFillStatusIds", ids);
            jpql += " and a.project_fill_status in ( :projectFillStatusIds ) ";
        }

        jpql = jpql + " order by a.project_fill_status desc,a.isimp,a.modify_date desc  ";

        JPage<ProjectRecordVO> page = nativeQuery().setSql(jpql).setParams(params).modelPage(simplePageRequest, ProjectRecordVO.class);
        page.getContent().forEach(projectRecordVO -> {
            then(projectRecordVO);
        });

        return page;
    }


    /**
     * 导出excel查询
     *
     * @param projectCriteriaVO
     * @return
     */
    @Override
    public List<ProjectPageOutVO> exportExcelQuery(ProjectCriteriaVO projectCriteriaVO) {

        Map<String, Object> params = new WeakHashMap<String, Object>();

        StringBuilder sql = ProjectPublicQuerySQL(projectCriteriaVO, params);
        List<ProjectPageOutVO> projectPageOutVOList = nativeQuery().setSql(sql.toString()).setParams(params).models(ProjectPageOutVO.class);

        processProjectPageExcelOutVo(projectPageOutVOList);
        return projectPageOutVOList;
    }

    /**
     * 字典转换
     * @param projectPageOutVOList
     * @return
     */
    private List<ProjectPageOutVO> processProjectPageExcelOutVo(List<ProjectPageOutVO> projectPageOutVOList) {
        DecimalFormat format = new DecimalFormat("#.00");
        for (ProjectPageOutVO projectPageOutVO:projectPageOutVOList) {
            projectPageOutVO.setTotalInvestmentStr(format.format(projectPageOutVO.getTotalInvestment()));

            projectPageOutVO.setProjectAttributeStr(dictionaryCache.getDictDataName(ProjectAttribute.CODE, projectPageOutVO.getProjectAttribute()));
            projectPageOutVO.setConstructionNatureStr(dictionaryCache.getDictDataName(ConstructionNature.CODE, projectPageOutVO.getConstructionNature()));
            projectPageOutVO.setIndustryClassificationStr(dictionaryCache.getDictDataName(IndustryClassification.CODE, projectPageOutVO.getIndustryClassification()));
            projectPageOutVO.setImplementScheduleStr(dictionaryCache.getDictDataName(ImplementSchedule.CODE, projectPageOutVO.getImplementSchedule()));

            projectPageOutVO.setIspppProjectStr(IS.YES.equals(projectPageOutVO.getIspppProject())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsplanNewWorkStr(IS.YES.equals(projectPageOutVO.getIsplanNewWork())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsplanCompleteStr(IS.YES.equals(projectPageOutVO.getIsplanComplete())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsprovinceimpStr(IS.YES.equals(projectPageOutVO.getIsprovinceimp())?IsString.YES:IsString.NO);
            projectPageOutVO.setIscityimpStr(IS.YES.equals(projectPageOutVO.getIscityimp())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsdistrictimpStr(IS.YES.equals(projectPageOutVO.getIsdistrictimp())?IsString.YES:IsString.NO);
            projectPageOutVO.setIssiteselectStr(IS.YES.equals(projectPageOutVO.getIssiteselect())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsproblemStr(IS.YES.equals(projectPageOutVO.getIsproblem())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsstatelandStr(IS.YES.equals(projectPageOutVO.getIsstateland())?IsString.YES:IsString.NO);
            projectPageOutVO.setIspeopledefenceStr(IS.YES.equals(projectPageOutVO.getIspeopledefence())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsprojectapproveStr(IS.YES.equals(projectPageOutVO.getIsprojectapprove())?IsString.YES:IsString.NO);
            projectPageOutVO.setIscheckapproveStr(IS.YES.equals(projectPageOutVO.getIscheckapprove())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsbuildprojectStr(IS.YES.equals(projectPageOutVO.getIsbuildproject())?IsString.YES:IsString.NO);
            projectPageOutVO.setIssimulateapproveStr(IS.YES.equals(projectPageOutVO.getIssimulateapprove())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsenvironmentapproveStr(IS.YES.equals(projectPageOutVO.getIsenvironmentapprove())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsculturalrelicsStr(IS.YES.equals(projectPageOutVO.getIsculturalrelics())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsweatherStr(IS.YES.equals(projectPageOutVO.getIsweather())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsantiknockStr(IS.YES.equals(projectPageOutVO.getIsantiknock())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsenergyconservationStr(IS.YES.equals(projectPageOutVO.getIsenergyconservation())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsparkafforestStr(IS.YES.equals(projectPageOutVO.getIsparkafforest())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsfirecontrolStr(IS.YES.equals(projectPageOutVO.getIsfirecontrol())?IsString.YES:IsString.NO);
            projectPageOutVO.setIsbuildlandStr(IS.YES.equals(projectPageOutVO.getIsbuildland())?IsString.YES:IsString.NO);
            projectPageOutVO.setIssimulatefinishStr(IS.YES.equals(projectPageOutVO.getIssimulatefinish())?IsString.YES:IsString.NO);
            projectPageOutVO.setTransferStatusStr(IS.YES.equals(projectPageOutVO.getTransferStatus())?IsString.YES:IsString.NO);
            //1不事草稿 2是草稿
            projectPageOutVO.setProjectFillStatusStr(IS.YES.equals(projectPageOutVO.getProjectFillStatus())?IsString.NO:IsString.YES);
        }
        return projectPageOutVOList;
    }

    /**
     * 组装sql
     *
     * @param projectCriteriaVO
     * @param params
     * @return
     */
    private StringBuilder ProjectPublicQuerySQL(ProjectCriteriaVO projectCriteriaVO, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(" t.id as id," +
                   " t.project_name as projectName," +
                   " t.project_number as projectNumber," +
                   " t.company_name as companyName," +
                   " t.organization_code as organizationCode," +
                   " t.project_attribute as projectAttribute," +
                   " t.construction_nature as constructionNature," +
                   " t.industry_classification as industryClassification," +
                   " t.implement_schedule as implementSchedule," +
                   " DATE_FORMAT(t.plan_begin_date,'%Y-%m-%d') as planBeginDateFormat," +
                   " DATE_FORMAT(t.plan_end_date,'%Y-%m-%d') as planEndDateFormat," +
                   " DATE_FORMAT(t.actual_begin_date,'%Y-%m-%d') as actualBeginDateFormat," +
                   " DATE_FORMAT(t.actual_end_date,'%Y-%m-%d') as actualEndDateFormat," +
                   " t.total_investment as totalInvestment," +
                   " t.enterprise_legal_person as enterpriseLegalPerson," +
                   " t.enterprise_contact_person as enterpriseContactPerson," +
                   " t.enterprise_contact_phone as enterpriseContactPhone," +
                   " t.enterprise_charge_person as enterpriseChargePerson," +
                   " t.enterprise_charge_phone as enterpriseChargePhone," +
                   " t.construction_content_scale as constructionContentScale," +
                   " t.isppp_project as ispppProject," +
                   " t.isplan_new_work as isplanNewWork," +
                   " t.isplan_complete as isplanComplete," +
                   " t.isprovinceimp as isprovinceimp," +
                   " t.iscityimp as iscityimp," +
                   " t.isdistrictimp as isdistrictimp," +
                   " t.subcontract_leader as subcontractLeader," +
                   " t.subcontract_company1 as subcontractCompany1," +
                   " t.subcontract_company1_person as subcontractCompany1Person," +
                   " t.subcontract_company1_person_phone as subcontractCompany1PersonPhone," +
                   " t.subcontract_company1_contact as subcontractCompany1Contact," +
                   " t.subcontract_company1_contact_phone as subcontractCompany1ContactPhone," +
                   " t.subcontract_company2 as subcontractCompany2," +
                   " t.subcontract_company2_person as subcontractCompany2Person," +
                   " t.subcontract_company2_person_phone as subcontractCompany2PersonPhone," +
                   " t.subcontract_company2_contact as subcontractCompany2Contact," +
                   " t.subcontract_company2_contact_phone as subcontractCompany2ContactPhone," +
                   " t.subcontract_company3 as subcontractCompany3," +
                   " t.subcontract_company3_person as subcontractCompany3Person," +
                   " t.subcontract_company3_person_phone as subcontractCompany3PersonPhone," +
                   " t.subcontract_company3_contact as subcontractCompany3Contact," +
                   " t.subcontract_company3_contact_phone as subcontractCompany3ContactPhone," +
                   " t.issiteselect as issiteselect," +
                   " t.isproblem as isproblem," +
                   " t.isstateland as isstateland," +
                   " t.ispeopledefence as ispeopledefence," +
                   " t.isprojectapprove as isprojectapprove," +
                   " t.ischeckapprove as ischeckapprove," +
                   " t.isbuildproject as isbuildproject," +
                   " t.issimulateapprove as issimulateapprove," +
                   " t.isenvironmentapprove as isenvironmentapprove," +
                   " t.isculturalrelics as isculturalrelics," +
                   " t.isweather as isweather," +
                   " t.isantiknock as isantiknock," +
                   " t.isenergyconservation as isenergyconservation," +
                   " t.isparkafforest as isparkafforest," +
                   " t.isfirecontrol as isfirecontrol," +
                   " t.isbuildland as isbuildland," +
                   " t.issimulatefinish as issimulatefinish," +
                   " t.transfer_status as transferStatus," +
                   " t.project_fill_status as projectFillStatus ");
        sql.append("  from t_project t ");
        sql.append(" where t.is_available = :isAvailable ");

        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getProjectName())) {
            params.put("projectName", "%" + projectCriteriaVO.getProjectName() + "%");
            sql.append(" and t.project_name like :projectName ");
        }

        //是否重点项目
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getImportentProjectIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getImportentProjectIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });

            sql.append(" and (");
            for (String id : ids) {
                if (id.trim().equals(ImportentProject.PROVINCE)) {
                    sql.append(" t.isprovinceimp = :isprovinceimp ");
                    params.put("isprovinceimp", Isprovinceimp.YES);
                } else if (id.trim().equals(ImportentProject.CITY)) {
                    sql.append(" t.iscityimp = :iscityimp ");
                    params.put("iscityimp", Iscityimp.YES);
                } else if (id.trim().equals(ImportentProject.DISTRICT)) {
                    sql.append(" t.isdistrictimp = :isdistrictimp ");
                    params.put("isdistrictimp", Isdistrictimp.YES);
                }
                sql.append(" or ");
            }
            sql.append(" 1!=1 )");
        }

        //实施进度
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getImplementScheduleIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getImplementScheduleIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("implementScheduleIds", ids);
            sql.append(" and t.implement_schedule in ( :implementScheduleIds ) ");
        }

        //行业分类
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getIndustryClassificationIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getIndustryClassificationIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("industryClassificationIds", ids);
            sql.append(" and t.industry_classification in ( :industryClassificationIds ) ");
        }

        //项目填写状态
        if (JStringUtils.isNotNullOrEmpty(projectCriteriaVO.getProjectFillStatusIds())) {
            List<String> ids = new ArrayList<>();
            Arrays.asList(projectCriteriaVO.getProjectFillStatusIds().split(","))
                    .stream()
                    .filter(id -> {
                        return JStringUtils.isNotNullOrEmpty(id);
                    })
                    .forEach(id -> {
                        ids.add(id);
                    });
            params.put("projectFillStatusIds", ids);
            sql.append(" and t.project_fill_status in ( :projectFillStatusIds ) ");
        }

        sql.append(" order by t.project_fill_status desc,t.isimp,t.modify_date desc  ");
        params.put("isAvailable", Availability.available.ordinal());
        return sql;
    }

	/**
	 * 删除基础项目
	 *
	 * @param id
	 */
    @Override
    public void deleteProjectById(String id) {
        if (StringUtils.isNotBlank(id)) {
            Project project = internalProjectServiceImpl.getById(id);
            internalProjectServiceImpl.delete(project);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,project.getProjectName(),Constants.projectModule.PROJECT_BASE);
            // 删除之前的待办
            backlogWorkService.deleteBacklogWorkByProjectId(project.getId());
        }
    }

	/**
	 * 验证是否存在项目代码
	 *
	 * @param projectNumber
	 * @return
	 */
    @Override
    public List<ProjectRecordVO> getProjectByProjectNumber(String projectNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT t.id as id," +
                "t.project_name as projectName," +
                "t.project_number as projectNumber " +
                "from t_project t " +
                "where t.is_available = :isAvailable and t.project_number = :projectNumber and t.project_fill_status = :projectFillStatus";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("projectNumber", projectNumber);
        map.put("projectFillStatus", ProjectFillStatus.NORMAL);

        List<ProjectRecordVO> attractProjectRecordVOList = new ArrayList<>();
        if(JStringUtils.isNotNullOrEmpty(projectNumber)){
            attractProjectRecordVOList = nativeQuery().setSql(sql).setParams(map).models(ProjectRecordVO.class);
        }
        return attractProjectRecordVOList;
    }

	/**
	 * 保存基础项目
	 *
	 * @param projectSaveVO
	 */
    @Override
    public void saveProject(ProjectSaveVO projectSaveVO) {
        Project project = new Project();
        Copy.simpleCopyExcludeNull(projectSaveVO, project);
        //总投资
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getTotalInvestmentStr())) {
            project.setTotalInvestment(Double.valueOf(projectSaveVO.getTotalInvestmentStr()).doubleValue());
        } else {
            project.setTotalInvestment(0.00);
        }

        //计划开工时间
        project.setPlanBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
        //计划竣工时间
        project.setPlanEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
        //实际开工时间为空则用计划开工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualBeginDateStr())) {
            project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
        } else {
            project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getActualBeginDateStr()));
        }
        //实际竣工时间为空则用计划竣工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualEndDateStr())) {
            project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
        } else {
            project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getActualEndDateStr()));
        }

        //省市区重点项目设置为重点项目
        if(Isprovinceimp.YES.equals(projectSaveVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectSaveVO.getIscityimp())
                || Isdistrictimp.YES.equals(projectSaveVO.getIsdistrictimp())){
            project.setIsimp(Isimp.YES);
        }else{
            project.setIsimp(Isimp.NO);
        }
        // 是否存在问题内容 add by songzhonglin on 2018-03-12
        if(IS.YES.equals(projectSaveVO.getIsproblem())){
            project.setQuestionContent(projectSaveVO.getQuestionContent());
        }else{
            project.setQuestionContent("");
        }
        //正常保存基本项目信息
        project.setProjectFillStatus(ProjectFillStatus.NORMAL);
        internalProjectServiceImpl.saveOnly(project);

        //给项目初始化部门
        initialProjectDepartInfo(project);

        // 保存附件
        attachmentService.uploadFiles(projectSaveVO.getAttachmentIds(), project.getId());

    }

    /**
     * 给项目初始化部门
     *
     * @param project
     */
    public void initialProjectDepartInfo(Project project){
        List<DepartGovernment> departmentOutVOList=  internalDepartGovernmentServiceImpl.singleEntityQuery2()
                .conditionDefault().likes("showPic", "1")
                .ready()
                .order().asc("sequence")
                .ready()
                .models(DepartGovernment.class);
        for (DepartGovernment departGovernment:departmentOutVOList) {
            //初始化项目部门关联表数据
            ProjectDepartment projectDepartment = new ProjectDepartment();
            projectDepartment.setProjectId(project.getId());
            projectDepartment.setDepartmentId(departGovernment.getId());
            projectDepartment.setApproveStatus(ApproveStatus.NOBEGIN);
            internalProjectDepartmentServiceImpl.saveOnly(projectDepartment);

            //新增项目流程历史表新增项目部门新增信息
            ProjectDepartmentHistory projectDepartmentHistory = new ProjectDepartmentHistory();
            projectDepartmentHistory.setApproveStatus(ApproveStatus.NOBEGIN);
            projectDepartmentHistory.setProjectDepartId(projectDepartment.getId());
            internalProjectDepartmentHistoryServiceImpl.saveOnly(projectDepartmentHistory);

            // 只有重点项目会添加到待办任务并发送消息...
            if(Isprovinceimp.YES.equals(project.getIsprovinceimp()) || Iscityimp.YES.equals(project.getIscityimp())
                    || Isdistrictimp.YES.equals(project.getIsdistrictimp())){
                //向对应的部门下项目管理员发送代办事项
                updateDepartProjectAdminVOList(project,departGovernment);
            }
        }

    }

    /**
     * 获取对应的部门下项目管理员
     *
     * @param departGovernment
     * @return
     */
    public List<DepartProjectAdminOutVO> getDepartProjectAdmin(DepartGovernment departGovernment) {
        Department department = departmentSerive.getDepartmentByRemoteId(departGovernment.getDepartId());
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select t.user_id as userId from t_sys_user_department t " +
                " where t.department_id = :departmentId and t.is_available = :isAvailable " +
                " and EXISTS(select 1 from (select t.user_id as userId from t_sys_user_role t " +
                " left join t_sys_role t1 on t.role_id = t1.id and t1.is_available = :isAvailable " +
                " where t.is_available = :isAvailable and t1.name = :projectAdmin) t2 where t2.userId = t.user_id)";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("departmentId",department.getId());
        map.put("projectAdmin",ProjectAdmin.PROJECTADMIN);
        List<DepartProjectAdminOutVO> departProjectAdminOutVOList = nativeQuery().setSql(sql).setParams(map).models(DepartProjectAdminOutVO.class);
        return departProjectAdminOutVOList;
    }

	/**
	 * 保存草稿基础项目
	 *
	 * @param projectSaveVO
	 */
    @Override
    public void saveDraftProject(ProjectSaveVO projectSaveVO) {
        Project project = new Project();
        Copy.simpleCopyExcludeNull(projectSaveVO, project);

        //总投资
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getTotalInvestmentStr())) {
            project.setTotalInvestment(Double.valueOf(projectSaveVO.getTotalInvestmentStr()).doubleValue());
        } else {
            project.setTotalInvestment(0.00);
        }

        //计划开工时间
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanBeginDateStr())) {
            project.setPlanBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
        }
        //计划竣工时间
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanEndDateStr())) {
            project.setPlanEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
        }
        //实际开工时间为空则用计划开工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualBeginDateStr())) {
            if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanBeginDateStr())) {
                project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
            }
        } else {
            project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getActualBeginDateStr()));
        }
        //实际竣工时间为空则用计划竣工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualEndDateStr())) {
            if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanEndDateStr())) {
                project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
            }
        } else {
            project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getActualEndDateStr()));
        }

        //省市区重点项目设置为重点项目
        if(Isprovinceimp.YES.equals(projectSaveVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectSaveVO.getIscityimp())
                || Isdistrictimp.YES.equals(projectSaveVO.getIsdistrictimp())){
            project.setIsimp(Isimp.YES);
        }else{
            project.setIsimp(Isimp.NO);
        }

        // 是否存在问题内容 add by songzhonglin on 2018-03-12
        if(IS.YES.equals(projectSaveVO.getIsproblem())){
            project.setQuestionContent(projectSaveVO.getQuestionContent());
        }else{
            project.setQuestionContent("");
        }

        //草稿保存基本项目信息
        project.setProjectFillStatus(ProjectFillStatus.DRAFT);
        internalProjectServiceImpl.saveOnly(project);
        // 保存附件
        attachmentService.uploadFiles(projectSaveVO.getAttachmentIds(), project.getId());
    }

	/**
	 * 查看基本项目详情
	 *
	 * @param id
	 * @return
	 */
    @Override
    public ProjectRecordVO getProjectById(String id) {
        Project project = internalProjectServiceImpl.getById(id);
        ProjectRecordVO vo = new ProjectRecordVO();
        Copy.simpleCopyExcludeNull(project, vo);
        List<AttachmentInfo> attachmentInfoLists = attachmentService.getAttachmentList(id);
        vo.setAttachmentInfoList(attachmentInfoLists);
        then(vo);
        return vo;
    }

	/**
	 * 修改基础项目
	 *
	 * @param projectSaveVO
	 */
    @Override
    public void editProject(ProjectSaveVO projectSaveVO) {
        Project project = internalProjectServiceImpl.getById(projectSaveVO.getId());

        //是否重点项目
        String isProvinceImp = project.getIsprovinceimp();
        String isCityImp = project.getIscityimp();
        String isDistrictImp = project.getIsdistrictimp();

        Copy.simpleCopyExcludeNull(projectSaveVO, project);

        //局部项目填写状态变量
        String projectFillStatus = project.getProjectFillStatus();

        //总投资
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getTotalInvestmentStr())) {
            project.setTotalInvestment(Double.valueOf(projectSaveVO.getTotalInvestmentStr()).doubleValue());
        } else {
            project.setTotalInvestment(0.00);
        }

        //计划开工时间
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanBeginDateStr())) {
            project.setPlanBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
        }
        //计划竣工时间
        if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanEndDateStr())) {
            project.setPlanEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
        }
        //实际开工时间为空则用计划开工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualBeginDateStr())) {
            if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanBeginDateStr())) {
                project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getPlanBeginDateStr()));
            }
        } else {
            project.setActualBeginDate(JDateUtils.parseDate(projectSaveVO.getActualBeginDateStr()));
        }
        //实际竣工时间为空则用计划竣工时间
        if (JStringUtils.isNullOrEmpty(projectSaveVO.getActualEndDateStr())) {
            if (JStringUtils.isNotNullOrEmpty(projectSaveVO.getPlanEndDateStr())) {
                project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getPlanEndDateStr()));
            }
        } else {
            project.setActualEndDate(JDateUtils.parseDate(projectSaveVO.getActualEndDateStr()));
        }

        //省市区重点项目设置为重点项目
        if(Isprovinceimp.YES.equals(projectSaveVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectSaveVO.getIscityimp())
                || Isdistrictimp.YES.equals(projectSaveVO.getIsdistrictimp())){
            project.setIsimp(Isimp.YES);
        }else{
            project.setIsimp(Isimp.NO);
        }

        // 是否存在问题内容 add by songzhonglin on 2018-03-12
        if(IS.YES.equals(projectSaveVO.getIsproblem())){
            project.setQuestionContent(projectSaveVO.getQuestionContent());
        }else{
            project.setQuestionContent("");
        }

        //如果是草稿则改为正常状态
        if(ProjectFillStatus.DRAFT.equals(projectFillStatus)){
            if(ProjectFillStatus.NORMAL.equals(projectSaveVO.getProjectFillStatusStr())){
                project.setProjectFillStatus(ProjectFillStatus.NORMAL);
            }
        }
        internalProjectServiceImpl.updateOnly(project);

        //项目原来是草稿
        if(ProjectFillStatus.DRAFT.equals(projectFillStatus)) {
            //点击保存
            if(ProjectFillStatus.NORMAL.equals(projectSaveVO.getProjectFillStatusStr())) {
                initialProjectDepartInfo(project);
            }
        }else{//项目原来是正常的
            //非重点项目现在修改成重点项目
            if((Isprovinceimp.NO.equals(isProvinceImp) && Iscityimp.NO.equals(isCityImp) && Isdistrictimp.NO.equals(isDistrictImp))
                    && (Isprovinceimp.YES.equals(projectSaveVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectSaveVO.getIscityimp()) || Isdistrictimp.YES.equals(projectSaveVO.getIsdistrictimp()))){
                //向对应的部门下项目管理员发送代办事项
                List<DepartGovernment> departmentOutVOList=  internalDepartGovernmentServiceImpl.singleEntityQuery2()
                        .conditionDefault().likes("showPic", "1")
                        .ready()
                        .order().asc("sequence")
                        .ready()
                        .models(DepartGovernment.class);
                for (DepartGovernment departGovernment:departmentOutVOList) {
                    updateDepartProjectAdminVOList(project,departGovernment);
                }
            }
        }

        // 修改附件之前，先删除之前的附件
        Optional.ofNullable(projectSaveVO.getDelImageIds()).ifPresent(delImageIds -> {
            for (String imageId : delImageIds.split(",")) {
                if (JStringUtils.isNotNullOrEmpty(imageId)) {
                    attachmentService.deleteAttachById(imageId);
                }
            }
        });
        // 重新上传附件
        attachmentService.uploadFiles(projectSaveVO.getAttachmentIds(), project.getId());
    }

	/**
	 * 从QA获得招商前期项目信息
	 * @return
	 */
    @Override
    public String insertAttractJson(AttractProjectRecordVO attractProjectRecordVO) {
        AttractProject attractProject = new AttractProject();
        Copy.simpleCopyExcludeNull(attractProjectRecordVO, attractProject);

        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT t.id as id," +
                "t.project_name as projectName," +
                "t.project_describe as projectDescribe," +
                "t.invest_survey as investSurvey," +
                "t.land_acquisition as landAcquisition," +
                "t.floor_space as floorSpace," +
                "t.total_investment as totalInvestment," +
                "t.investment_company as investmentCompany," +
                "t.company_name as companyName," +
                "t.site_location as siteLocation," +
                "t.charge_name as chargeName," +
                "t.charge_phone as chargePhone," +
                "t.contact_name as contactName," +
                "t.contact_phone as contactPhone," +
                "t.back_flag as backFlag " +
                "from t_project_attract t " +
                "where t.is_available = :isAvailable and t.special_number = :specialNumber ";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("specialNumber", attractProjectRecordVO.getSpecialNumber());
        List<AttractProject> attractProjectList = nativeQuery().setSql(sql).setParams(map).models(AttractProject.class);

        if(attractProjectList.size() == 0){//管理系统没有招商项目
            attractProject.setBackFlag(BackFlag.NO);
            internalAttractProjectServiceImpl.saveOnly(attractProject);
            //插入附件
            if(attractProjectRecordVO.getProtocolScanAttachmentList() != null && attractProjectRecordVO.getProtocolScanAttachmentList().size() > 0){
                insertAttachmentList(attractProjectRecordVO.getProtocolScanAttachmentList(),attractProject);
            }

            return GetAttractProjectFlag.YES;
        }else{//管理系统有招商项目
            if(BackFlag.YES.equals(attractProjectList.get(0).getBackFlag())){//退回标志为1
                AttractProject attractProjectUpdate = internalAttractProjectServiceImpl.getById(attractProjectList.get(0).getId());

                //删除招商附件list
                deleteAttractProjectByFkid(attractProjectUpdate.getId());

                Copy.simpleCopyExcludeNull(attractProjectRecordVO, attractProjectUpdate);
                attractProjectUpdate.setBackFlag(BackFlag.NO);
                internalAttractProjectServiceImpl.updateOnly(attractProjectUpdate);

                //插入附件
                if(attractProjectRecordVO.getProtocolScanAttachmentList() != null && attractProjectRecordVO.getProtocolScanAttachmentList().size() > 0){
                    insertAttachmentList(attractProjectRecordVO.getProtocolScanAttachmentList(),attractProjectUpdate);
                }

                return GetAttractProjectFlag.YES;
            }else{//退回标志为0
                return GetAttractProjectFlag.DUPLICATE;
            }
        }
    }

    public void insertAttachmentList(List<AttractProjectAttachment> protocolScanAttachmentList,AttractProject attractProject){
        for (AttractProjectAttachment attractProjectAttachment:protocolScanAttachmentList) {
            Attachment attachment = new Attachment();
            String fileName = attractProjectAttachment.getName().substring(0,attractProjectAttachment.getName().lastIndexOf("."));
            String fileSuffix= attractProjectAttachment.getName().substring(attractProjectAttachment.getName().lastIndexOf(".")+1,attractProjectAttachment.getName().length());
            attachment.setName(fileName);
            attachment.setPath("/zs"+attractProjectAttachment.getPath());
            attachment.setSuffix(fileSuffix);
            attachment.setFkId(attractProject.getId());
            attachmentEntityManager.saveOnly(attachment);
        }
    }
	/**
	 * 获取招商前期项目信息
	 * @return
	 */
    @Override
    public List<AttractProjectRecordVO> getAttractProjectsPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT t.id as id," +
                "t.project_name as projectName," +
                "t.project_describe as projectDescribe," +
                "t.invest_survey as investSurvey," +
                "t.land_acquisition as landAcquisition," +
                "t.floor_space as floorSpace," +
                "t.total_investment as totalInvestment," +
                "t.investment_company as investmentCompany," +
                "t.company_name as companyName," +
                "t.site_location as siteLocation," +
                "t.charge_name as chargeName," +
                "t.charge_phone as chargePhone," +
                "t.contact_name as contactName," +
                "t.contact_phone as contactPhone," +
                "t.back_flag as backFlag " +
                "from t_project_attract t " +
                "where t.is_available = :isAvailable ORDER BY t.modify_date DESC";
        map.put("isAvailable", Availability.available.ordinal());
        List<AttractProjectRecordVO> attractProjectRecordVOList = nativeQuery().setSql(sql).setParams(map).models(AttractProjectRecordVO.class);
        int i = 1;
        for (AttractProjectRecordVO attractProjectRecordVO : attractProjectRecordVOList) {
            attractProjectRecordVO.setSequence(i++);
        }
        return attractProjectRecordVOList;
    }

	/**
	 * 删除招商前期项目
	 *
	 * @param id
	 */
    @Override
    public void deleteAttractProject(String id) {
        if (StringUtils.isNotBlank(id)) {
            AttractProject attractProject = internalAttractProjectServiceImpl.getById(id);
            internalAttractProjectServiceImpl.delete(attractProject);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,attractProject.getProjectName(),Constants.projectModule.PROJECT_ATTRACT);
        }
    }

	/**
	 * 退回招商前期项目
	 *
	 * @param id
	 */
    @Override
    public void backAttractProject(String id) {
        AttractProject attractProject = internalAttractProjectServiceImpl.getById(id);
        attractProject.setBackFlag(BackFlag.YES);
        internalAttractProjectServiceImpl.updateOnly(attractProject);
    }

    /**
     * 物理删除招商的附件
     * @param fkid
     */
    public void deleteAttractProjectByFkid(String fkid) {
        String sql = "delete from t_attachment where fk_id =:fkid";
        Map<String, Object> params = new WeakHashMap<String, Object>();
        params.put("fkid",fkid);
        nativeQuery().setSql(sql).setParams(params).setUpdate(true).model();
    }

	/**
	 * 获取招商前期项目信息详情
	 * @return
	 */
    @Override
    public AttractProjectRecordVO getAttractProjectContById(String id) {
        AttractProject attractProject = internalAttractProjectServiceImpl.getById(id);
        AttractProjectRecordVO vo = new AttractProjectRecordVO();
        Copy.simpleCopyExcludeNull(attractProject, vo);
        vo.setCreateDateStr(JDateUtils.format(vo.getCreateDate()));
        List<AttachmentInfo> attachmentInfoLists = attachmentService.getAttachmentList(id);
        vo.setAttachmentInfoList(attachmentInfoLists);
        return vo;
    }

	/**
	 * 查询项目所有选择列
	 * @return
	 */
	@Override
	public List<ProjectTableFieldRecordVO> getProjectAllColumn(String columnName) {
		Map<String, Object> map =new HashMap<String, Object>();
		String sql ="SELECT t.id as id," +
				"t.field_name as fieldName," +
				"t.table_name as tableName " +
				"from t_project_table_field t " +
				"where t.is_available = :isAvailable ";

		if(JStringUtils.isNotNullOrEmpty(columnName)){
			map.put("columnName", "%"+columnName+"%");
			sql= sql+" and t.table_name like :columnName ";
		}
		sql=sql+" order by t.count desc";

		map.put("isAvailable", Availability.available.ordinal());
		List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = nativeQuery().setSql(sql).setParams(map).models(ProjectTableFieldRecordVO.class);
		return projectTableFieldRecordVOList;
	}

	/**
	 * 根据id与列名查询项目信息
	 * @param idsString
	 * @param columnNamesString
	 * @return
	 */
	@Override
	public List<ProjectRecordVO> getProjectByIdAndColumn(String idsString,String columnNamesString) {
		//批量修改项目信息列名
		List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = processProjectTableField(columnNamesString);

		//更新所选列的热度排行
		for (ProjectTableFieldRecordVO projectTableFieldRecordVO:projectTableFieldRecordVOList) {
			ProjectTableField projectTableField = internalProjectTableFieldServiceImpl.getById(projectTableFieldRecordVO.getId());
			projectTableField.setCount(projectTableFieldRecordVO.getCount()+1);
			internalProjectTableFieldServiceImpl.updateOnly(projectTableField);
		}

		//批量修改项目信息id集合
		List<String> ids = new ArrayList<>();
		Arrays.asList(idsString.split(","))
				.stream()
				.filter(id -> {
					return JStringUtils.isNotNullOrEmpty(id);
				})
				.forEach(id -> {
					ids.add(id);
				});

		//组装sql语句
        String fieldName = processFieldName(projectTableFieldRecordVOList);
        StringBuilder sql = new StringBuilder("select t.project_name as projectName, ");
        if(fieldName != null){
            sql.append(fieldName);
        }
        sql.append(" ifnull(t.question_content,'') as questionContent ,");
        sql.append(" t.id as id ");
        sql.append("  from t_project t ");
        sql.append(" where t.is_available = :isAvailable and t.id in ( :ids )");
        sql.append(" order by  t.modify_date desc ");

        Map<String, Object> map =new HashMap<String, Object>();
		map.put("isAvailable", Availability.available.ordinal());
		map.put("ids",ids);
		List<ProjectRecordVO> projectRecordVOList = nativeQuery().setSql(sql.toString()).setParams(map).models(ProjectRecordVO.class);
		for (ProjectRecordVO projectRecord :projectRecordVOList) {
		    if(columnNamesString.indexOf(ProjectCodesTable.ProjectTableField.TOTAL_INVESTMENT_FLAG) >= 0){
                projectRecord.setTotalInvestmentFlag(IS.YES);
            }else{
                projectRecord.setTotalInvestmentFlag(IS.NO);
            }
            then(projectRecord);
		}
		return projectRecordVOList;
	}

    /**
     * 拼接SQL
     * @param projectTableFieldRecordVOList
     * @return
     */

    private String processFieldName(List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList) {
        StringBuilder sql = new StringBuilder();
        for (ProjectTableFieldRecordVO projectTableFieldRecordVO : projectTableFieldRecordVOList) {
            //基础信息
            if(ProjectCodesTable.ProjectTableField.PROJECT_NUMBER.equals(projectTableFieldRecordVO.getFieldName())){
                sql.append("t.project_number as projectNumber ,");
            } else if (ProjectCodesTable.ProjectTableField.COMPANY_NAME.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.company_name as companyName ,");
            } else if (ProjectCodesTable.ProjectTableField.ORGANIZATION_CODE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.organization_code as organizationCode ,");
            } else if (ProjectCodesTable.ProjectTableField.PROJECT_ATTRIBUTE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.project_attribute as projectAttribute ,");
            } else if (ProjectCodesTable.ProjectTableField.CONSTRUCTION_NATURE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.construction_nature as constructionNature ,");
            } else if (ProjectCodesTable.ProjectTableField.INDUSTRY_CLASSIFICATION.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.industry_classification as industryClassification ,");
            } else if (ProjectCodesTable.ProjectTableField.IMPLEMENT_SCHEDULE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.implement_schedule as implementSchedule ,");
            } else if (ProjectCodesTable.ProjectTableField.PLAN_BEGIN_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.plan_begin_date as planBeginDate ,");
            } else if (ProjectCodesTable.ProjectTableField.PLAN_END_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.plan_end_date as planEndDate ,");
            } else if (ProjectCodesTable.ProjectTableField.ACTUAL_BEGIN_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.actual_begin_date as actualBeginDate ,");
            } else if (ProjectCodesTable.ProjectTableField.ACTUAL_END_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.actual_end_date as actualEndDate ,");
            } else if (ProjectCodesTable.ProjectTableField.TOTAL_INVESTMENT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.total_investment as totalInvestment ,");
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_LEGAL_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.enterprise_legal_person as enterpriseLegalPerson ,");
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CONTACT_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.enterprise_contact_person as enterpriseContactPerson ,");
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.enterprise_contact_phone as enterpriseContactPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CHARGE_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.enterprise_charge_person as enterpriseChargePerson ,");
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CHARGE_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.enterprise_charge_phone as enterpriseChargePhone ,");
            } else if (ProjectCodesTable.ProjectTableField.CONSTRUCTION_CONTENT_SCALE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.construction_content_scale as constructionContentScale ,");
            } else if (ProjectCodesTable.ProjectTableField.ISPPP_PROJECT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isppp_project as ispppProject ,");
            } else if (ProjectCodesTable.ProjectTableField.ISPLAN_NEW_WORK.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isplan_new_work as isplanNewWork ,");
            } else if (ProjectCodesTable.ProjectTableField.ISPLAN_COMPLETE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isplan_complete as isplanComplete ,");
            } else if (ProjectCodesTable.ProjectTableField.ISPROVINCEIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isprovinceimp as isprovinceimp ,");
            } else if (ProjectCodesTable.ProjectTableField.ISCITYIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.iscityimp as iscityimp ,");
            } else if (ProjectCodesTable.ProjectTableField.ISDISTRICTIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isdistrictimp as isdistrictimp ,");
            }//工作安排
            else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_LEADER.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_leader as subcontractLeader ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company1 as subcontractCompany1 ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company1_person as subcontractCompany1Person ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company1_person_phone as subcontractCompany1PersonPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company1_contact as subcontractCompany1Contact ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company1_contact_phone as subcontractCompany1ContactPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company2 as subcontractCompany2 ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company2_person as subcontractCompany2Person ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company2_person_phone as subcontractCompany2PersonPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company2_contact as subcontractCompany2Contact ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company2_contact_phone as subcontractCompany2ContactPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company3 as subcontractCompany3 ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company3_person as subcontractCompany3Person ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company3_person_phone as subcontractCompany3PersonPhone ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company3_contact as subcontractCompany3Contact ,");
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.subcontract_company3_contact_phone as subcontractCompany3ContactPhone ,");
            }//审批字段
            else if (ProjectCodesTable.ProjectTableField.ISSITESELECT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.issiteselect as issiteselect ,");
            }else if (ProjectCodesTable.ProjectTableField.ISPROBLEM.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isproblem as isproblem ,");
            }else if (ProjectCodesTable.ProjectTableField.ISSTATELAND.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isstateland as isstateland ,");
            }else if (ProjectCodesTable.ProjectTableField.ISPEOPLEDEFENCE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.ispeopledefence as ispeopledefence ,");
            }else if (ProjectCodesTable.ProjectTableField.ISPROJECTAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isprojectapprove as isprojectapprove ,");
            }else if (ProjectCodesTable.ProjectTableField.ISCHECKAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.ischeckapprove as ischeckapprove ,");
            }else if (ProjectCodesTable.ProjectTableField.ISBUILDPROJECT.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isbuildproject as isbuildproject ,");
            }else if (ProjectCodesTable.ProjectTableField.ISSIMULATEAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.issimulateapprove as issimulateapprove ,");
            }else if (ProjectCodesTable.ProjectTableField.ISENVIRONMENTAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isenvironmentapprove as isenvironmentapprove ,");
            }else if (ProjectCodesTable.ProjectTableField.ISCULTURALRELICS.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isculturalrelics as isculturalrelics ,");
            }else if (ProjectCodesTable.ProjectTableField.ISWEATHER.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isweather as isweather ,");
            }else if (ProjectCodesTable.ProjectTableField.ISANTIKNOCK.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isantiknock as isantiknock ,");
            }else if (ProjectCodesTable.ProjectTableField.ISENERGYCONSERVATION.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isenergyconservation as isenergyconservation ,");
            }else if (ProjectCodesTable.ProjectTableField.ISPARKAFFOREST.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isparkafforest as isparkafforest ,");
            }else if (ProjectCodesTable.ProjectTableField.ISFIRECONTROL.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isfirecontrol as isfirecontrol ,");
            }else if (ProjectCodesTable.ProjectTableField.ISBUILDLAND.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.isbuildland as isbuildland ,");
            }else if (ProjectCodesTable.ProjectTableField.ISSIMULATEFINISH.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.issimulatefinish as issimulatefinish ,");
            }else if (ProjectCodesTable.ProjectTableField.TRANSFER_STATUS.equals(projectTableFieldRecordVO.getFieldName())) {
                sql.append("t.transfer_status as transferStatus ,");
            }
        }
        if (sql != null && sql.length() > 0) {
            return sql.toString();
        }
        return null;
    }

	public List<ProjectTableFieldRecordVO> processProjectTableField(String columnNamesString){
		//批量修改项目信息列名
		List<String> coulumns = new ArrayList<>();
		Arrays.asList(columnNamesString.split(","))
				.stream()
				.filter(coulumn -> {
					return JStringUtils.isNotNullOrEmpty(coulumn);
				})
				.forEach(coulumn -> {
					coulumns.add(coulumn);
				});

		Map<String, Object> map =new HashMap<String, Object>();
		String sql = "select t.id as id," +
				"t.count as count," +
				"t.field_name as fieldName," +
				"t.table_name as tableName " +
				" from t_project_table_field t " +
				" where t.is_available = :isAvailable " +
				" and t.table_name in ( :coulumns ) order by t.id";
		map.put("isAvailable", Availability.available.ordinal());
		map.put("coulumns",coulumns);
		List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = nativeQuery().setSql(sql).setParams(map).models(ProjectTableFieldRecordVO.class);
		return projectTableFieldRecordVOList;
	}

    public String deCode(String str){
        String s = "";
        if(str.length() == 0) return "";
        s = str.replace("&quot;","\"");
        return s;
    }

    @Override
    public void updateBatchProject(ProjectBatchUpdateInVO projectBatchUpdateInVO) {
        //批量修改项目信息列名
        List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = processProjectTableField(projectBatchUpdateInVO.getColumnNamesString());
        //页面Json转成数组对象
        List<ProjectRecordVO> formFormFrameworkInVOs = JJSON.get().parse(deCode(projectBatchUpdateInVO.getProjectBatchUpdateInVOs()),new TypeReference<List<ProjectRecordVO>>() {});
        for (ProjectRecordVO projectRecordVO:formFormFrameworkInVOs) {
            Project project = internalProjectServiceImpl.getById(projectRecordVO.getId());

            //省市区重点项目设置为重点项目
            if(Isprovinceimp.YES.equals(projectRecordVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectRecordVO.getIscityimp())
                    || Isdistrictimp.YES.equals(projectRecordVO.getIsdistrictimp())){
                project.setIsimp(Isimp.YES);
            }else{
                project.setIsimp(Isimp.NO);
            }

            //非重点项目现在修改成重点项目
            if((Isprovinceimp.NO.equals(project.getIsprovinceimp()) && Iscityimp.NO.equals(project.getIscityimp()) && Isdistrictimp.NO.equals(project.getIsdistrictimp()))
                    && (Isprovinceimp.YES.equals(projectRecordVO.getIsprovinceimp()) || Iscityimp.YES.equals(projectRecordVO.getIscityimp()) || Isdistrictimp.YES.equals(projectRecordVO.getIsdistrictimp()))){
                //向对应的部门下项目管理员发送代办事项
                List<DepartGovernment> departmentOutVOList=  internalDepartGovernmentServiceImpl.singleEntityQuery2()
                        .conditionDefault().likes("showPic", "1")
                        .ready()
                        .order().asc("sequence")
                        .ready()
                        .models(DepartGovernment.class);
                for (DepartGovernment departGovernment:departmentOutVOList) {
                    updateDepartProjectAdminVOList(project,departGovernment);
                }
            }

            processBatchUpdateProject(projectTableFieldRecordVOList,project,projectRecordVO);
            internalProjectServiceImpl.updateOnly(project);
        }
    }

    /**
     *  向对应的部门下项目管理员发送代办事项
     * @param departGovernment
     * @param project
     * @return
     */
    public void updateDepartProjectAdminVOList(Project project,DepartGovernment departGovernment){
        List<DepartProjectAdminOutVO> departProjectAdminOutVOList = getDepartProjectAdmin(departGovernment);
        for (DepartProjectAdminOutVO departProjectAdminOutVO: departProjectAdminOutVOList) {
            BacklogWorkAddInVO backlogWorkAddInVO =new BacklogWorkAddInVO();
            backlogWorkAddInVO.setProjectName(project.getProjectName());
            backlogWorkAddInVO.setProjectCode(project.getProjectNumber());
            backlogWorkAddInVO.setHastenTask(departGovernment.getImageText()+"待办");
            backlogWorkAddInVO.setProjectId(project.getId());
            backlogWorkAddInVO.setProjectCreateTime(project.getActualBeginDate());
            backlogWorkAddInVO.setLeaderId(departProjectAdminOutVO.getUserId());
            backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_ZERP);
            backlogWorkService.addBacklogWork(backlogWorkAddInVO);
        }
        //往OA发通知消息
        logger.info("新增基础项目审批流程单位:" + departGovernment.getImageText());
        sendOAMessage(departProjectAdminOutVOList);
    }

    public void sendOAMessage(List<DepartProjectAdminOutVO> departProjectAdminOutVOList){
        JSONArray array = new JSONArray();
        for (DepartProjectAdminOutVO departProjectAdminOutVO: departProjectAdminOutVOList) {
            SysUser sysUser = internalSysUserServiceImpl.getById(departProjectAdminOutVO.getUserId());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content", Constants.OAMessageType.CONTENT);
            jsonObject.put("remind_user",sysUser.getUserAccount());
            jsonObject.put("biz_system",Constants.OAMessageType.BIZ_SYSTEM );
            jsonObject.put("URL", "");
            array.add(jsonObject);
            logger.info("新增基础项目审批流程单位项目管理员:" + sysUser.getUserAccount());
        }
        //往OA发通知消息
        OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
        if(oaMessageOutVO.getSuccess().equals("true")){
            logger.info("新增基础项目：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
        }else{
            logger.info("新增基础项目：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
        }
    }

    /**
     *  验证批量修改草稿项目
     * @param idsString
     * @return
     */
    @Override
    public List<ProjectRecordVO> checkDraftProject(String idsString) {
        //批量修改项目信息id集合
        List<String> ids = new ArrayList<>();
        Arrays.asList(idsString.split(","))
                .stream()
                .filter(id -> {
                    return JStringUtils.isNotNullOrEmpty(id);
                })
                .forEach(id -> {
                    ids.add(id);
                });

        //组装sql语句
        StringBuilder sql = new StringBuilder("select t.project_name as projectName, ");
        sql.append(" t.id as id ");
        sql.append("  from t_project t ");
        sql.append(" where t.is_available = :isAvailable and t.id in ( :ids ) and t.project_fill_status = :projectFillStatus");
        sql.append(" order by  t.modify_date desc ");

        Map<String, Object> map =new HashMap<String, Object>();
        map.put("isAvailable", Availability.available.ordinal());
        map.put("ids",ids);
        map.put("projectFillStatus",ProjectFillStatus.DRAFT);
        List<ProjectRecordVO> projectRecordVOList = nativeQuery().setSql(sql.toString()).setParams(map).models(ProjectRecordVO.class);
        return projectRecordVOList;
    }

    /**
     * 处理批量修改列数据
     * @param projectTableFieldRecordVOList
     * @param project
     * @param projectRecordVO
     * @return
     */

    private Project processBatchUpdateProject(List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList,Project project,ProjectRecordVO projectRecordVO) {
        for (ProjectTableFieldRecordVO projectTableFieldRecordVO : projectTableFieldRecordVOList) {
            //基础信息
            if(ProjectCodesTable.ProjectTableField.PROJECT_NUMBER.equals(projectTableFieldRecordVO.getFieldName())){
                project.setProjectNumber(projectRecordVO.getProjectNumber());
            } else if (ProjectCodesTable.ProjectTableField.COMPANY_NAME.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setCompanyName(projectRecordVO.getCompanyName());
            } else if (ProjectCodesTable.ProjectTableField.ORGANIZATION_CODE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setOrganizationCode(projectRecordVO.getOrganizationCode());
            } else if (ProjectCodesTable.ProjectTableField.PROJECT_ATTRIBUTE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setProjectAttribute(projectRecordVO.getProjectAttribute());
            } else if (ProjectCodesTable.ProjectTableField.CONSTRUCTION_NATURE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setConstructionNature(projectRecordVO.getConstructionNature());
            } else if (ProjectCodesTable.ProjectTableField.INDUSTRY_CLASSIFICATION.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIndustryClassification(projectRecordVO.getIndustryClassification());
            } else if (ProjectCodesTable.ProjectTableField.IMPLEMENT_SCHEDULE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setImplementSchedule(projectRecordVO.getImplementSchedule());
            } else if (ProjectCodesTable.ProjectTableField.PLAN_BEGIN_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                //计划开工时间
                project.setPlanBeginDate(JDateUtils.parseDate(projectRecordVO.getPlanBeginDateStr()));
            } else if (ProjectCodesTable.ProjectTableField.PLAN_END_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                //计划竣工时间
                project.setPlanEndDate(JDateUtils.parseDate(projectRecordVO.getPlanEndDateStr()));
            } else if (ProjectCodesTable.ProjectTableField.ACTUAL_BEGIN_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                //实际开工时间为空则不修改
                if (JStringUtils.isNotNullOrEmpty(projectRecordVO.getActualBeginDateStr())) {
                    project.setActualBeginDate(JDateUtils.parseDate(projectRecordVO.getActualBeginDateStr()));
                }
            } else if (ProjectCodesTable.ProjectTableField.ACTUAL_END_DATE.equals(projectTableFieldRecordVO.getFieldName())) {
                //实际竣工时间为空则不修改
                if (JStringUtils.isNotNullOrEmpty(projectRecordVO.getActualEndDateStr())) {
                    project.setActualEndDate(JDateUtils.parseDate(projectRecordVO.getActualEndDateStr()));
                }
            } else if (ProjectCodesTable.ProjectTableField.TOTAL_INVESTMENT.equals(projectTableFieldRecordVO.getFieldName())) {
                //总投资
                if (JStringUtils.isNotNullOrEmpty(String.valueOf(projectRecordVO.getTotalInvestment()))) {
                    project.setTotalInvestment(Double.valueOf(projectRecordVO.getTotalInvestment()).doubleValue());
                } else {
                    project.setTotalInvestment(0.00);
                }
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_LEGAL_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setEnterpriseLegalPerson(projectRecordVO.getEnterpriseLegalPerson());
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CONTACT_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setEnterpriseContactPerson(projectRecordVO.getEnterpriseContactPerson());
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setEnterpriseContactPhone(projectRecordVO.getEnterpriseContactPhone());
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CHARGE_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setEnterpriseChargePerson(projectRecordVO.getEnterpriseChargePerson());
            } else if (ProjectCodesTable.ProjectTableField.ENTERPRISE_CHARGE_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setEnterpriseChargePhone(projectRecordVO.getEnterpriseChargePhone());
            } else if (ProjectCodesTable.ProjectTableField.CONSTRUCTION_CONTENT_SCALE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setConstructionContentScale(projectRecordVO.getConstructionContentScale());
            } else if (ProjectCodesTable.ProjectTableField.ISPPP_PROJECT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIspppProject(projectRecordVO.getIspppProject());
            } else if (ProjectCodesTable.ProjectTableField.ISPLAN_NEW_WORK.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsplanNewWork(projectRecordVO.getIsplanNewWork());
            } else if (ProjectCodesTable.ProjectTableField.ISPLAN_COMPLETE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsplanComplete(projectRecordVO.getIsplanComplete());
            } else if (ProjectCodesTable.ProjectTableField.ISPROVINCEIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsprovinceimp(projectRecordVO.getIsprovinceimp());
            } else if (ProjectCodesTable.ProjectTableField.ISCITYIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIscityimp(projectRecordVO.getIscityimp());
            } else if (ProjectCodesTable.ProjectTableField.ISDISTRICTIMP.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsdistrictimp(projectRecordVO.getIsdistrictimp());
            }//工作安排
            else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_LEADER.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractLeader(projectRecordVO.getSubcontractLeader());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany1(projectRecordVO.getSubcontractCompany1());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany1Person(projectRecordVO.getSubcontractCompany1Person());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany1PersonPhone(projectRecordVO.getSubcontractCompany1PersonPhone());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany1Contact(projectRecordVO.getSubcontractCompany1Contact());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY1_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany1ContactPhone(projectRecordVO.getSubcontractCompany1ContactPhone());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany2(projectRecordVO.getSubcontractCompany2());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany2Person(projectRecordVO.getSubcontractCompany2Person());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany2PersonPhone(projectRecordVO.getSubcontractCompany2PersonPhone());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany2Contact(projectRecordVO.getSubcontractCompany2Contact());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY2_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany2ContactPhone(projectRecordVO.getSubcontractCompany2ContactPhone());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany3(projectRecordVO.getSubcontractCompany3());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_PERSON.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany3Person(projectRecordVO.getSubcontractCompany3Person());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_PERSON_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany3PersonPhone(projectRecordVO.getSubcontractCompany3PersonPhone());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_CONTACT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany3Contact(projectRecordVO.getSubcontractCompany3Contact());
            } else if (ProjectCodesTable.ProjectTableField.SUBCONTRACT_COMPANY3_CONTACT_PHONE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setSubcontractCompany3ContactPhone(projectRecordVO.getSubcontractCompany3ContactPhone());
            }//审批字段
            else if (ProjectCodesTable.ProjectTableField.ISSITESELECT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIssiteselect(projectRecordVO.getIssiteselect());
            }else if (ProjectCodesTable.ProjectTableField.ISPROBLEM.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsproblem(projectRecordVO.getIsproblem());
                if(IS.YES.equals(projectRecordVO.getIsproblem())){
                    project.setQuestionContent(projectRecordVO.getQuestionContent());
                }else{
                    project.setQuestionContent("");
                }
            }else if (ProjectCodesTable.ProjectTableField.ISSTATELAND.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsstateland(projectRecordVO.getIsstateland());
            }else if (ProjectCodesTable.ProjectTableField.ISPEOPLEDEFENCE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIspeopledefence(projectRecordVO.getIspeopledefence());
            }else if (ProjectCodesTable.ProjectTableField.ISPROJECTAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsprojectapprove(projectRecordVO.getIsprojectapprove());
            }else if (ProjectCodesTable.ProjectTableField.ISCHECKAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIscheckapprove(projectRecordVO.getIscheckapprove());
            }else if (ProjectCodesTable.ProjectTableField.ISBUILDPROJECT.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsbuildproject(projectRecordVO.getIsbuildproject());
            }else if (ProjectCodesTable.ProjectTableField.ISSIMULATEAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIssimulateapprove(projectRecordVO.getIssimulateapprove());
            }else if (ProjectCodesTable.ProjectTableField.ISENVIRONMENTAPPROVE.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsenvironmentapprove(projectRecordVO.getIsenvironmentapprove());
            }else if (ProjectCodesTable.ProjectTableField.ISCULTURALRELICS.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsculturalrelics(projectRecordVO.getIsculturalrelics());
            }else if (ProjectCodesTable.ProjectTableField.ISWEATHER.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsweather(projectRecordVO.getIsweather());
            }else if (ProjectCodesTable.ProjectTableField.ISANTIKNOCK.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsantiknock(projectRecordVO.getIsantiknock());
            }else if (ProjectCodesTable.ProjectTableField.ISENERGYCONSERVATION.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsenergyconservation(projectRecordVO.getIsenergyconservation());
            }else if (ProjectCodesTable.ProjectTableField.ISPARKAFFOREST.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsparkafforest(projectRecordVO.getIsparkafforest());
            }else if (ProjectCodesTable.ProjectTableField.ISFIRECONTROL.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsfirecontrol(projectRecordVO.getIsfirecontrol());
            }else if (ProjectCodesTable.ProjectTableField.ISBUILDLAND.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setIsbuildland(projectRecordVO.getIsbuildland());
            }else if(ProjectCodesTable.ProjectTableField.ISSIMULATEFINISH.equals(projectTableFieldRecordVO.getFieldName())){
                project.setIssimulatefinish(projectRecordVO.getIssimulatefinish());
            }else if (ProjectCodesTable.ProjectTableField.TRANSFER_STATUS.equals(projectTableFieldRecordVO.getFieldName())) {
                project.setTransferStatus(projectRecordVO.getTransferStatus());
            }
        }
        return project;
    }


    /**
     * 根据id查询项目流程信息
     * * @param projectId
     * @return
     */
    public ProjectDepartVO getProjectFlowListById(String projectId){
        ProjectDepartVO projectDepartVO = new ProjectDepartVO();
        //获取初始化部门信息
        Map<String, Object> map =new HashMap<String, Object>();
        String sql = "select t.id as id," +
                "t.department_id as departmentId," +
                "t.project_id as projectId," +
                "t.approve_status as approveStatus," +
                "t1.image_text as imageText " +
                " from t_project_deprtment t " +
                " left join t_depart_government t1 on t.department_id = t1.id and t1.is_available = :isAvailable" +
                " where t.is_available = :isAvailable " +
                " and t.project_id = :projectId order by t1.sequence";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("projectId",projectId);
        List<ProjectDepartInfo> projectDepartInfoList = nativeQuery().setSql(sql).setParams(map).models(ProjectDepartInfo.class);

        //根据项目ID获取项目名称
        Project project = internalProjectServiceImpl.getById(projectId);
        projectDepartVO.setProjectName(project.getProjectName());
        projectDepartVO.setProjectDepartInfoList(projectDepartInfoList);
        return projectDepartVO;
    }

    /**
     * 根据id更新项目流程状态
     * * @param projectDepartHistoryInVO
     * @return
     */
    @Override
    public void updateProjectFlowStatusById(ProjectDepartHistoryInVO projectDepartHistoryInVO) {
        //更新项目部门关联表审批状态
        ProjectDepartment projectDepartment = internalProjectDepartmentServiceImpl.getById(projectDepartHistoryInVO.getProjectDepartId());
        projectDepartment.setApproveStatus(projectDepartHistoryInVO.getApproveStatus());
        internalProjectDepartmentServiceImpl.updateOnly(projectDepartment);

        //更新项目流程修改历史表
        ProjectDepartmentHistory projectDepartmentHistory = new ProjectDepartmentHistory();
        projectDepartmentHistory.setApproveStatus(projectDepartHistoryInVO.getApproveStatus());
        projectDepartmentHistory.setProjectDepartId(projectDepartment.getId());
        if(ApproveStatus.PROBLEM.equals(projectDepartHistoryInVO.getApproveStatus())){
            projectDepartmentHistory.setProblemDescribe(projectDepartHistoryInVO.getProblemDescribe());
            projectDepartmentHistory.setLeaderId(projectDepartHistoryInVO.getLeaderId());

            //查询项目信息
            Project project = internalProjectServiceImpl.getById(projectDepartHistoryInVO.getProjectId());
            //插入到代办
            BacklogWorkAddInVO backlogWorkAddInVO =new BacklogWorkAddInVO();
            backlogWorkAddInVO.setProjectName(project.getProjectName());
            backlogWorkAddInVO.setProjectCode(project.getProjectNumber());
            backlogWorkAddInVO.setHastenTask(projectDepartHistoryInVO.getProblemDescribe());
            backlogWorkAddInVO.setProjectId(project.getId());
            backlogWorkAddInVO.setProjectCreateTime(project.getActualBeginDate());
            backlogWorkAddInVO.setLeaderId(projectDepartHistoryInVO.getLeaderId());
            backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_ZERP);
            backlogWorkService.addBacklogWork(backlogWorkAddInVO);

            //往OA发通知消息
            JSONArray array = new JSONArray();
            SysUser sysUser = internalSysUserServiceImpl.getById(projectDepartHistoryInVO.getLeaderId());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content", Constants.OAMessageType.CONTENT);
            jsonObject.put("remind_user",sysUser.getUserAccount());
            jsonObject.put("biz_system",Constants.OAMessageType.BIZ_SYSTEM );
            jsonObject.put("URL", "");
            array.add(jsonObject);
            logger.info("新增基础项目存在问题处理人:" + sysUser.getUserAccount());

            OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
            if(oaMessageOutVO.getSuccess().equals("true")){
                logger.info("新增基础项目存在问题：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
            }else{
                logger.info("新增基础项目存在问题：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
            }

        }else if(ApproveStatus.CLOSE.equals(projectDepartHistoryInVO.getApproveStatus())){
            if(JStringUtils.isNotNullOrEmpty(projectDepartHistoryInVO.getRemark())){
                projectDepartmentHistory.setRemark(projectDepartHistoryInVO.getRemark());
            }
        }

        internalProjectDepartmentHistoryServiceImpl.saveOnly(projectDepartmentHistory);

        if(ApproveStatus.CLOSE.equals(projectDepartHistoryInVO.getApproveStatus())){
            // 保存附件
            attachmentService.uploadFiles(projectDepartHistoryInVO.getAttachmentIds(), projectDepartmentHistory.getId());
        }
    }

    /**
     * 根据id查询项目流程历史信息
     * * @param projectId
     * @return
     */
    public ProjectDepartHistoryOutVO getProjectFlowHistoryListById(String projectId){
        ProjectDepartHistoryOutVO projectDepartHistoryOutVO = new ProjectDepartHistoryOutVO();
        //获取初始化部门信息
        Map<String, Object> map =new HashMap<String, Object>();
        String sql = "select t.id as id," +
                "t.department_id as departmentId," +
                "t.project_id as projectId," +
                "t.approve_status as approveStatus," +
                "t1.image_text as imageText " +
                " from t_project_deprtment t " +
                " left join t_depart_government t1 on t.department_id = t1.id and t1.is_available = :isAvailable" +
                " where t.is_available = :isAvailable " +
                " and t.project_id = :projectId order by t1.sequence";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("projectId",projectId);
        List<ProjectDepartInfo> projectDepartInfoList = nativeQuery().setSql(sql).setParams(map).models(ProjectDepartInfo.class);

        //组装项目部门历史信息集合
        processProjectDepartHistoryList(projectDepartInfoList);

        //根据项目ID获取项目名称
        Project project = internalProjectServiceImpl.getById(projectId);
        projectDepartHistoryOutVO.setProjectName(project.getProjectName());

        List<ProjectDepartInfo> projectDepartOneInfoList = new ArrayList<>();
        projectDepartOneInfoList.add(projectDepartInfoList.get(0));
        projectDepartOneInfoList.add(projectDepartInfoList.get(1));
        projectDepartOneInfoList.add(projectDepartInfoList.get(2));
        projectDepartHistoryOutVO.setProjectDepartInfoOneList(projectDepartOneInfoList);

        List<ProjectDepartInfo> projectDepartTwoInfoList = new ArrayList<>();
        projectDepartTwoInfoList.add(projectDepartInfoList.get(3));
        projectDepartTwoInfoList.add(projectDepartInfoList.get(4));
        projectDepartTwoInfoList.add(projectDepartInfoList.get(5));
        projectDepartHistoryOutVO.setProjectDepartInfoTwoList(projectDepartTwoInfoList);

        List<ProjectDepartInfo> projectDepartThreeInfoList = new ArrayList<>();
        projectDepartThreeInfoList.add(projectDepartInfoList.get(6));
        projectDepartThreeInfoList.add(projectDepartInfoList.get(7));
        projectDepartThreeInfoList.add(projectDepartInfoList.get(8));
        projectDepartHistoryOutVO.setProjectDepartInfoThreeList(projectDepartThreeInfoList);

        List<ProjectDepartInfo> projectDepartFourInfoList = new ArrayList<>();
        projectDepartFourInfoList.add(projectDepartInfoList.get(9));
        projectDepartFourInfoList.add(projectDepartInfoList.get(10));
        projectDepartFourInfoList.add(projectDepartInfoList.get(11));
        projectDepartHistoryOutVO.setProjectDepartInfoFourList(projectDepartFourInfoList);

        List<ProjectDepartInfo> projectDepartFiveInfoList = new ArrayList<>();
        projectDepartFiveInfoList.add(projectDepartInfoList.get(12));
        projectDepartFiveInfoList.add(projectDepartInfoList.get(13));
        projectDepartFiveInfoList.add(projectDepartInfoList.get(14));
        projectDepartHistoryOutVO.setProjectDepartInfoFiveList(projectDepartFiveInfoList);

        return projectDepartHistoryOutVO;
    }

    /**
     * 组装项目部门历史信息集合
     * @param projectDepartInfoList
     * @return
     */
    private List<ProjectDepartInfo> processProjectDepartHistoryList(List<ProjectDepartInfo> projectDepartInfoList) {
        for (ProjectDepartInfo projectDepartInfo:projectDepartInfoList) {
            //根据项目部门ID获取项目流程历史记录
            List<ProjectDepartmentHistoryInfo> projectDepartmentHistoryInfoList = getProjectDepartmentHistoryListById(projectDepartInfo.getId());
            projectDepartInfo.setProjectDepartmentHistoryList(projectDepartmentHistoryInfoList);
        }
        return projectDepartInfoList;
    }

    /**
     * 根据项目部门ID获取项目流程历史记录
     * @return
     */
    public List<ProjectDepartmentHistoryInfo> getProjectDepartmentHistoryListById(String projectDepartId) {
        List<ProjectDepartmentHistoryInfo> projectDepartmentHistoryList = internalProjectDepartmentHistoryServiceImpl.
                singleEntityQuery2()
                .conditionDefault().likes("projectDepartId", projectDepartId)
                .ready()
                .order().asc("createDate")
                .ready()
                .models(ProjectDepartmentHistoryInfo.class);
        processProjectDepartmentHistoryInfoName(projectDepartmentHistoryList);
        return projectDepartmentHistoryList;
    }

    /**
     * 根据createiId获取创建人姓名
     * @return
     */
    public List<ProjectDepartmentHistoryInfo> processProjectDepartmentHistoryInfoName(List<ProjectDepartmentHistoryInfo> projectDepartmentHistoryList) {
        for (ProjectDepartmentHistoryInfo projectDepartmentHistoryInfo:projectDepartmentHistoryList) {
                SysUser sysUser = internalSysUserServiceImpl.getById(projectDepartmentHistoryInfo.getCreatorId());
            projectDepartmentHistoryInfo.setName(sysUser.getName());
        }
        return projectDepartmentHistoryList;
    }

    /**
     * 根据id查询项目流程存在问题详细信息
     * * @param id
     * @return
     */
    @Override
    public 	ProjectDepartmentHistoryRecordVO viewProjectFlowInfoByid(String id){
        ProjectDepartmentHistory projectDepartmentHistory = internalProjectDepartmentHistoryServiceImpl.getById(id);
        ProjectDepartmentHistoryRecordVO vo = new ProjectDepartmentHistoryRecordVO();
        Copy.simpleCopyExcludeNull(projectDepartmentHistory, vo);

        if(projectDepartmentHistory.getLeaderId() !=null){
            SysUser sysUser = internalSysUserServiceImpl.getById(projectDepartmentHistory.getLeaderId());
            vo.setLeaderName(sysUser.getName());
        }

        List<AttachmentInfo> attachmentInfoLists = attachmentService.getAttachmentList(id);
        vo.setAttachmentInfoList(attachmentInfoLists);
        return vo;
    }

    /**
     * 查询项目审批进展情况
     * @return
     */
    public JPage<ProjectFlowApproveVO> getProjectFlowApproveList(ProjectCriteriaVO projectCriteriaVO, SimplePageRequest simplePageRequest){
        //获取重点项目信息
        Map<String, Object> map =new HashMap<String, Object>();
        String sql = " select t.id as id," +
                " t.project_name as projectName " +
                " from t_project t " +
                " where (t.isprovinceimp = :isprovinceimp or t.iscityimp = :iscityimp or t.isdistrictimp = :isdistrictimp) " +
                " and t.is_available = :isAvailable " +
                " and t.project_fill_status = :projectFillStatus " +
                " order by t.modify_date desc ";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("projectFillStatus", ProjectFillStatus.NORMAL);
        map.put("isprovinceimp", Isprovinceimp.YES);
        map.put("iscityimp", Iscityimp.YES);
        map.put("isdistrictimp", Isdistrictimp.YES);
        JPage<ProjectFlowApproveVO> page = nativeQuery().setSql(sql).setParams(map).modelPage(simplePageRequest, ProjectFlowApproveVO.class);

        page.getContent().forEach(projectFlowApproveVO -> {
            thenProcessProjectFlowApprove(projectFlowApproveVO);
        });

        return page;
    }

    /**
     * the return one is same as the parameter
     *
     * @param projectFlowApproveVO
     * @return
     */
    private ProjectFlowApproveVO thenProcessProjectFlowApprove(ProjectFlowApproveVO projectFlowApproveVO) {
        List<ProjectFlowApproveInfo> projectFlowApproveInfoList = getProjectFlowApproveListById(projectFlowApproveVO.getId());
        projectFlowApproveVO.setGszc(projectFlowApproveInfoList.get(0).getApproveStatus());
        projectFlowApproveVO.setLx(projectFlowApproveInfoList.get(1).getApproveStatus());
        projectFlowApproveVO.setJsydghxkz(projectFlowApproveInfoList.get(2).getApproveStatus());
        projectFlowApproveVO.setGytdsyz(projectFlowApproveInfoList.get(3).getApproveStatus());
        projectFlowApproveVO.setJsgcghxkz(projectFlowApproveInfoList.get(4).getApproveStatus());
        projectFlowApproveVO.setXzyjs(projectFlowApproveInfoList.get(5).getApproveStatus());
        projectFlowApproveVO.setJsgcsgxkz(projectFlowApproveInfoList.get(6).getApproveStatus());
        projectFlowApproveVO.setJnsp(projectFlowApproveInfoList.get(7).getApproveStatus());
        projectFlowApproveVO.setKzsfsp(projectFlowApproveInfoList.get(8).getApproveStatus());
        projectFlowApproveVO.setHpsp(projectFlowApproveInfoList.get(9).getApproveStatus());
        projectFlowApproveVO.setWwkt(projectFlowApproveInfoList.get(10).getApproveStatus());
        projectFlowApproveVO.setRf(projectFlowApproveInfoList.get(11).getApproveStatus());
        projectFlowApproveVO.setQx(projectFlowApproveInfoList.get(12).getApproveStatus());
        projectFlowApproveVO.setXf(projectFlowApproveInfoList.get(13).getApproveStatus());
        projectFlowApproveVO.setYllh(projectFlowApproveInfoList.get(14).getApproveStatus());
        return projectFlowApproveVO;
    }


    /**
     * 根据id查询项目流程审批信息
     * * @param projectId
     * @return
     */
    public List<ProjectFlowApproveInfo> getProjectFlowApproveListById(String projectId){
        //获取初始化部门信息
        Map<String, Object> map =new HashMap<String, Object>();
        String sql = "select t.id as id," +
                "t.department_id as departmentId," +
                "t.project_id as projectId," +
                "t.approve_status as approveStatus," +
                "t1.image_text as imageText " +
                " from t_project_deprtment t " +
                " left join t_depart_government t1 on t.department_id = t1.id and t1.is_available = :isAvailable" +
                " where t.is_available = :isAvailable " +
                " and t.project_id = :projectId order by t1.sequence ";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("projectId",projectId);
        List<ProjectFlowApproveInfo> projectFlowApproveInfoList = nativeQuery().setSql(sql).setParams(map).models(ProjectFlowApproveInfo.class);
        return projectFlowApproveInfoList;
    }
}
