package com.fykj.pds.project.controller;

import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.departGovernment.service.DepartGovernmentService;
import com.fykj._b._core.dictionary.service.DictionaryService;
import com.fykj._b._core.dictionary.vo.DictDataOutVO;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.kernel.excel.ExcelHelper;
import com.fykj.pds.interfaceManage.InterfaceManager;
import com.fykj.pds.project.excelVo.ProjectPageExcel;
import com.fykj.pds.project.service.ProjectService;

import com.fykj.pds.project.vo.*;
import com.fykj.pds.task.service.TaskService;
import com.fykj.util.JStringUtils;
import com.fykj.web.model.SimplePageRequestVO;
import com.fykj.pds.project.ProjectCodesTable.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by weijia on 2017/11/8.
 * 项目管理
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends baseController {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private InterfaceManager interfaceManager;

	@Autowired
	private TaskService taskService;

	@Autowired
	private DepartGovernmentService departGovernmentService;

	/**
	 * 查询复选框数据内容
	 *
	 * @param code
	 * @param simplePageRequestVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/getCheckBoxByPageCode",method=RequestMethod.GET)
	public InvokeResult getCheckBoxByPageCode(String code,SimplePageRequestVO simplePageRequestVO) throws Exception {
		JPage<DictDataOutVO> page = dictionaryService.getDictionaryDataPage(code,
				new SimplePageRequest(simplePageRequestVO.getPage(), simplePageRequestVO.getSize())
		);
		return InvokeResult.success(page);
	}

	/**
	 * 查询基本项目信息列表
	 *
	 * @param projectCriteriaVO
	 * @param simplePageRequest
	 * @return
	 */
	@RequestMapping(path = "/getProjectsPage", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectsPage(ProjectCriteriaVO projectCriteriaVO , SimplePageRequestVO simplePageRequest) {
		JPage<ProjectRecordVO> page = projectService.getProjects(projectCriteriaVO,
				new SimplePageRequest(simplePageRequest.getPage(), simplePageRequest.getSize())
				);
		return InvokeResult.success(page);
	}

	/**
	 * 导出excel验证
	 *
	 * @param projectCriteriaVO
	 * @return
	 */
	@RequestMapping(path = "/validExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public InvokeResult validExportExcel(ProjectCriteriaVO projectCriteriaVO) {
		List<ProjectPageOutVO> projectPageOutVOList = projectService.exportExcelQuery(projectCriteriaVO);
		if (projectPageOutVOList == null || projectPageOutVOList.size() <= 0) {
			return InvokeResult.bys("Excel文件导出失败，原因：查询列表无数据！");
		}
		return InvokeResult.success(true);
	}

	/**
	 * 导出excel
	 *
	 * @param projectCriteriaVO
	 * @return
	 */
	@RequestMapping(path = "/projectExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void taskExportExcel(ProjectCriteriaVO projectCriteriaVO, HttpServletResponse response) {
		List<ProjectPageOutVO> projectPageOutVOList = projectService.exportExcelQuery(projectCriteriaVO);
		List<ProjectPageExcel> taskExcelList = new ArrayList<>();
		for (ProjectPageOutVO projectPageOutVO : projectPageOutVOList) {
			ProjectPageExcel taskExcel = new ProjectPageExcel(JStringUtils.deCode(projectPageOutVO.getProjectName()),JStringUtils.deCode(projectPageOutVO.getProjectNumber()),
					JStringUtils.deCode(projectPageOutVO.getCompanyName()),JStringUtils.deCode(projectPageOutVO.getOrganizationCode()),projectPageOutVO.getProjectAttributeStr(),
					projectPageOutVO.getConstructionNatureStr(),projectPageOutVO.getIndustryClassificationStr(),projectPageOutVO.getImplementScheduleStr(),
					projectPageOutVO.getPlanBeginDateFormat(),projectPageOutVO.getPlanEndDateFormat(),projectPageOutVO.getActualBeginDateFormat(),
					projectPageOutVO.getActualEndDateFormat(),projectPageOutVO.getTotalInvestmentStr(),
					JStringUtils.deCode(projectPageOutVO.getEnterpriseLegalPerson()),JStringUtils.deCode(projectPageOutVO.getEnterpriseContactPerson()),
					JStringUtils.deCode(projectPageOutVO.getEnterpriseContactPhone()),JStringUtils.deCode(projectPageOutVO.getEnterpriseChargePerson()),
					JStringUtils.deCode(projectPageOutVO.getEnterpriseChargePhone()),JStringUtils.deCode(projectPageOutVO.getConstructionContentScale()),
					projectPageOutVO.getIspppProjectStr(),projectPageOutVO.getIsplanNewWorkStr(),projectPageOutVO.getIsplanCompleteStr(),
					projectPageOutVO.getIsprovinceimpStr(),projectPageOutVO.getIscityimpStr(),projectPageOutVO.getIsdistrictimpStr(),
					JStringUtils.deCode(projectPageOutVO.getSubcontractLeader()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany1()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany1Person()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany1PersonPhone()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany1Contact()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany1ContactPhone()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany2()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany2Person()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany2PersonPhone()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany2Contact()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany2ContactPhone()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany3()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany3Person()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany3PersonPhone()),
					JStringUtils.deCode(projectPageOutVO.getSubcontractCompany3Contact()),JStringUtils.deCode(projectPageOutVO.getSubcontractCompany3ContactPhone()),
					projectPageOutVO.getIssiteselectStr(),projectPageOutVO.getIsproblemStr(),projectPageOutVO.getIsstatelandStr(),
					projectPageOutVO.getIspeopledefenceStr(),projectPageOutVO.getIsprojectapproveStr(),projectPageOutVO.getIscheckapproveStr(),
					projectPageOutVO.getIsbuildprojectStr(),projectPageOutVO.getIssimulateapproveStr(),projectPageOutVO.getIsenvironmentapproveStr(),
					projectPageOutVO.getIsculturalrelicsStr(),projectPageOutVO.getIsweatherStr(),projectPageOutVO.getIsantiknockStr(),
					projectPageOutVO.getIsenergyconservationStr(),projectPageOutVO.getIsparkafforestStr(),projectPageOutVO.getIsfirecontrolStr(),
					projectPageOutVO.getIsbuildlandStr(),projectPageOutVO.getIssimulatefinishStr(),projectPageOutVO.getTransferStatusStr(),projectPageOutVO.getProjectFillStatusStr());
			taskExcelList.add(taskExcel);
		}
		ExcelHelper excelHelper = new ExcelHelper();
		byte[] bytes = excelHelper.excel2003(excelHelper.readRecord(taskExcelList), ProjectExcelReport.SHEET_NAME);
		String fileName = UUID.randomUUID() + ProjectExcelReport.SUFFIX;
		try {
			excelHelper.excelHelper(response, bytes, fileName);
		} catch (Exception e) {

		}
	}

	/**
	 * 验证是否存在项目代码
	 *
	 * @param projectNumber
	 * @return
	 */
	@RequestMapping(path = "/getProjectByProjectNumber", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectByProjectNumber(String projectNumber) {
		List<ProjectRecordVO>  projectRecordVOList = projectService.getProjectByProjectNumber(projectNumber);
			return InvokeResult.success(projectRecordVOList);
	}

	/**
	 * 保存基本项目信息
	 * @param projectSaveVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/saveProject",method=RequestMethod.POST)
	public InvokeResult saveProject(ProjectSaveVO projectSaveVO){
		projectService.saveProject(projectSaveVO);
		return InvokeResult.success(true);
	}

	/**
	 * 保存草稿基本项目信息
	 * @param projectSaveVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path="/saveDraftProject",method=RequestMethod.POST)
	public InvokeResult saveDraftProject(ProjectSaveVO projectSaveVO){
		projectService.saveDraftProject(projectSaveVO);
		return InvokeResult.success(true);
	}

	/**
	 * 查看基本项目详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/getProjectById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectById(String id) {
		if (!StringUtils.isNotBlank(id)) {
			return InvokeResult.bys("未获取页面元素信息");
		}
		ProjectRecordVO projectRecordVO = projectService.getProjectById(id);
		return InvokeResult.success(projectRecordVO);
	}

	/**
	 * 编辑基础项目信息
	 *
	 * @param projectSaveVO
	 * @return
	 */
	@RequestMapping(path = "/editProject", method = RequestMethod.POST)
	@ResponseBody
	public InvokeResult editProject(ProjectSaveVO projectSaveVO) {
		projectService.editProject(projectSaveVO);
		return InvokeResult.success(true);
	}

	/**
	 * 删除基本项目
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/deleteProjectById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult deleteProjectById(String id) {
		if (!StringUtils.isNotBlank(id)) {
			return InvokeResult.bys("未获取页面元素信息");
		}

		projectService.deleteProjectById(id);
		return InvokeResult.success(true);

	}

	/**
	 * 查询招商前期项目信息
	 * @return
	 */
	@RequestMapping(path = "/getAttractProjectsPage", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getAttractProjectsPage() {
		List<AttractProjectRecordVO> attractProjectRecordVOList = projectService.getAttractProjectsPage();
		return InvokeResult.success(attractProjectRecordVOList);
	}

	/**
	 * 删除招商信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/deleteAttractProject", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult deleteAttractProject(String id) {
		if (!StringUtils.isNotBlank(id)) {
			return InvokeResult.bys("未获取页面元素信息");
		}

		projectService.deleteAttractProject(id);

		List<AttractProjectRecordVO> attractProjectRecordVOList = projectService.getAttractProjectsPage();
		return InvokeResult.success(attractProjectRecordVOList);

	}

	/**
	 * 退回招商信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/backAttractProject", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult backAttractProject(String id) {
		if (!StringUtils.isNotBlank(id)) {
			return InvokeResult.bys("未获取页面元素信息");
		}

		//调用QA退回接口
		AttractProjectRejectOutVO attractProjectRejectOutVO = interfaceManager.attractProjectReject(id);

		if(attractProjectRejectOutVO.getCode().equals("1")){
			projectService.backAttractProject(id);
			List<AttractProjectRecordVO> attractProjectRecordVOList = projectService.getAttractProjectsPage();
			return InvokeResult.success(attractProjectRecordVOList);
		}else{
			return InvokeResult.bys(attractProjectRejectOutVO.getMessage());
		}


	}

	/**
	 * 查询招商信息详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/getAttractProjectContById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getAttractProjectContById(String id) {
		if (!StringUtils.isNotBlank(id)) {
			return InvokeResult.bys("未获取页面元素信息");
		}

		AttractProjectRecordVO attractProjectRecordVO = projectService.getAttractProjectContById(id);
		return InvokeResult.success(attractProjectRecordVO);

	}

	/**
	 * 查询项目所有选择列
	 *
	 * @param columnName
	 * @return
	 */
	@RequestMapping(path = "/getProjectAllColumn", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectAllColumn(String columnName) {
		List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = projectService.getProjectAllColumn(columnName);
		return InvokeResult.success(projectTableFieldRecordVOList);

	}

	/**
	 * 根据选择列查询批量修改列信息
	 * @param columnNamesString
	 * @return
	 */
	@RequestMapping(path = "/getChoiseColumn", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getChoiseColumn(String columnNamesString) {
		List<ProjectTableFieldRecordVO> projectTableFieldRecordVOList = projectService.processProjectTableField(columnNamesString);
		return InvokeResult.success(projectTableFieldRecordVOList);
	}

    /**
     * 根据id与列名查询项目信息
     * @param idsString
     * @param columnNamesString
     * @return
     */
    @RequestMapping(path = "/getProjectByIdAndColumn", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getProjectByIdAndColumn(String idsString,String columnNamesString) {
        List<ProjectRecordVO> projectRecordVOList = projectService.getProjectByIdAndColumn(idsString,columnNamesString);
        return InvokeResult.success(projectRecordVOList);
    }

	/**
	 * 批量修改项目信息
	 * @param projectBatchUpdateInVO
	 * @return
	 */
	@RequestMapping(path = "/updateBatchProject", method = RequestMethod.POST)
	@ResponseBody
	public InvokeResult updateBatchProject(ProjectBatchUpdateInVO projectBatchUpdateInVO) throws Exception{
		projectService.updateBatchProject(projectBatchUpdateInVO);
		return InvokeResult.success(true);
	}

	/**
	 *  验证批量修改草稿项目
	 * @param ids
	 * @return
	 */
	@RequestMapping(path = "/checkDraftProject", method = RequestMethod.POST)
	@ResponseBody
	public InvokeResult checkDraftProject(String ids) throws Exception{
		List<ProjectRecordVO> projectRecordVOList = projectService.checkDraftProject(ids);
		return InvokeResult.success(projectRecordVOList);
	}

	/**
	 * 根据id查询项目流程信息
	 * * @param projectId
	 * @return
	 */
	@RequestMapping(path = "/getProjectFlowListById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectFlowListById(String projectId) {
		ProjectDepartVO projectDepartVO= projectService.getProjectFlowListById(projectId);
		return InvokeResult.success(projectDepartVO);
	}

	/**
	 * 根据部门Id查找用户
	 *
	 * @return
	 */
	@RequestMapping(path = "/selectUserInfoByDepartId", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult selectUserInfo(String deptId) {
		DepartGovernment departGovernment = departGovernmentService.getDepartGovernmentById(deptId);
		List<SysUserPageOutVO> sysUserPageOutList = taskService.selectUserInfo(departGovernment.getDepartId());
		return InvokeResult.success(sysUserPageOutList);
	}

	/**
	 * 根据id更新项目流程状态
	 * * @param projectDepartHistoryInVO
	 * @return
	 */
	@RequestMapping(path = "/updateProjectFlowStatusById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult updateProjectFlowStatusById(ProjectDepartHistoryInVO projectDepartHistoryInVO) {
		projectService.updateProjectFlowStatusById(projectDepartHistoryInVO);
		return InvokeResult.success(true);
	}

	/**
	 * 根据id查询项目流程历史信息
	 * * @param projectId
	 * @return
	 */
	@RequestMapping(path = "/getProjectFlowHistoryListById", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectFlowHistoryListById(String projectId) {
		ProjectDepartHistoryOutVO projectDepartHistoryOutVO= projectService.getProjectFlowHistoryListById(projectId);
		return InvokeResult.success(projectDepartHistoryOutVO);
	}

	/**
	 * 根据id查询项目流程存在问题详细信息
	 * * @param id
	 * @return
	 */
	@RequestMapping(path = "/viewProjectFlowInfoByid", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult viewProjectFlowInfoByid(String id) {
		ProjectDepartmentHistoryRecordVO projectDepartmentHistoryRecordVO= projectService.viewProjectFlowInfoByid(id);
		return InvokeResult.success(projectDepartmentHistoryRecordVO);
	}

	/**
	 * 查询项目审批进展情况
	 * @return
	 */
	@RequestMapping(path = "/getProjectFlowApproveList", method = RequestMethod.GET)
	@ResponseBody
	public InvokeResult getProjectFlowApproveList(ProjectCriteriaVO projectCriteriaVO , SimplePageRequestVO simplePageRequest) {
		JPage<ProjectFlowApproveVO> page = projectService.getProjectFlowApproveList(projectCriteriaVO,
				new SimplePageRequest(simplePageRequest.getPage(), simplePageRequest.getSize())
		);
		return InvokeResult.success(page);
	}
}
