package com.fykj.pds.project.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.project.model.AttractProject;
import com.fykj.pds.project.model.Project;
import com.fykj.pds.project.model.ProjectDepartmentHistory;
import com.fykj.pds.project.vo.*;

import java.util.List;


public interface ProjectService {

	/**
	 * 获取基础项目信息
	 * @return
	 */
	JPage<ProjectRecordVO> getProjects(ProjectCriteriaVO projectCriteriaVO, SimplePageRequest simplePageRequest);

	/**
	 * 导出excel查询
	 *
	 * @param projectCriteriaVO
	 * @return
	 */
	List<ProjectPageOutVO> exportExcelQuery(ProjectCriteriaVO projectCriteriaVO);

	/**
	 * 删除基础项目
	 *
	 * @param id
	 */
	void deleteProjectById(String id);

	/**
	 * 验证是否存在项目代码
	 *
	 * @param projectNumber
	 * @return
	 */
	List<ProjectRecordVO> getProjectByProjectNumber(String projectNumber);

	/**
	 * 保存基础项目
	 *
	 * @param projectSaveVO
	 */
	void saveProject(ProjectSaveVO projectSaveVO);

	/**
	 * 保存草稿基础项目
	 *
	 * @param projectSaveVO
	 */
	void saveDraftProject(ProjectSaveVO projectSaveVO);

	/**
	 * 查看基本项目详情
	 *
	 * @param id
	 * @return
	 */
	ProjectRecordVO getProjectById(String id);

	/**
	 * 修改基础项目
	 *
	 * @param projectSaveVO
	 */
	void editProject(ProjectSaveVO projectSaveVO);





	/**
	 * 从QA获得招商前期项目信息
	 * @return
	 */
	String insertAttractJson(AttractProjectRecordVO attractProjectRecordVO);

	/**
	 * 获取招商前期项目信息
	 * @return
	 */
	List<AttractProjectRecordVO> getAttractProjectsPage();

	/**
	 * 删除招商前期项目
	 *
	 * @param id
	 */
	void deleteAttractProject(String id);

	/**
	 * 退回招商前期项目
	 *
	 * @param id
	 */
	void backAttractProject(String id);

	/**
	 * 获取招商前期项目信息详情
	 * @return
	 */
	AttractProjectRecordVO getAttractProjectContById(String id);

	/**
	 * 查询项目所有选择列
	 * @return
	 */
	List<ProjectTableFieldRecordVO> getProjectAllColumn(String columnName);

	/**
	 * 根据选择列查询批量修改列信息
	 * @param columnNamesString
	 * @return
	 */
	List<ProjectTableFieldRecordVO> processProjectTableField(String columnNamesString);

	/**
	 * 根据id与列名查询项目信息
	 * @param idsString
	 * @param columnNamesString
	 * @return
	 */
	List<ProjectRecordVO> getProjectByIdAndColumn(String idsString,String columnNamesString);

	/**
	 * 批量修改项目信息
	 * @param projectBatchUpdateInVO
	 * @return
	 */
	void updateBatchProject(ProjectBatchUpdateInVO projectBatchUpdateInVO);

	/**
	 *  验证批量修改草稿项目
	 * @param ids
	 * @return
	 */
	List<ProjectRecordVO> checkDraftProject(String ids);

	/**
	 * 根据id查询项目流程信息
	 * * @param projectId
	 * @return
	 */
	ProjectDepartVO getProjectFlowListById(String projectId);

	/**
	 * 根据id更新项目流程状态
	 * * @param projectDepartHistoryInVO
	 * @return
	 */
	void updateProjectFlowStatusById(ProjectDepartHistoryInVO projectDepartHistoryInVO);

	/**
	 * 根据id查询项目流程历史信息
	 * * @param projectId
	 * @return
	 */
	ProjectDepartHistoryOutVO getProjectFlowHistoryListById(String projectId);

	/**
	 * 根据id查询项目流程存在问题详细信息
	 * * @param id
	 * @return
	 */
	ProjectDepartmentHistoryRecordVO viewProjectFlowInfoByid(String id);

	/**
	 * 查询项目审批进展情况
	 * @return
	 */
	JPage<ProjectFlowApproveVO> getProjectFlowApproveList(ProjectCriteriaVO projectCriteriaVO, SimplePageRequest simplePageRequest);
}
