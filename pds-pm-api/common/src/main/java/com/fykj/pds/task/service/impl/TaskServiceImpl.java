package com.fykj.pds.task.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fykj._b._core.Constants;
import com.fykj._b._core.sysrole.model.SysRole;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.*;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.interfaceManage.InterfaceManager;
import com.fykj.pds.interfaceManage.vo.OAMessageOutVO;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.project.service.impl.ProjectServiceImpl;
import com.fykj.pds.task.constant.TaskConstant;
import com.fykj.pds.task.model.Task;
import com.fykj.pds.task.service.TableFieldService;
import com.fykj.pds.task.service.TaskService;
import com.fykj.pds.task.vo.TableFieldPageOutVO;
import com.fykj.pds.task.vo.TaskAddInVO;
import com.fykj.pds.task.vo.TaskPageInVO;
import com.fykj.pds.task.vo.TaskPageOutVO;
import com.fykj.pds.work.backlogWork.constant.BacklogWorkContant;
import com.fykj.pds.work.backlogWork.service.BacklogWorkService;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkAddInVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 16:10
 * Description:
 **/
@Service
@Transactional
public class TaskServiceImpl extends ServiceSupport implements TaskService {
    public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private SingleEntityManager<Task> internalTaskServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Task.class);

    private SingleEntityManager<SysRole> internalSysRoleService = SingleEntityManagerGetter.get()
            .getInstance(SysRole.class);

    private SingleEntityManager<SysUser> internalSysUserServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(SysUser.class);

    @Autowired
    private TableFieldService tableFieldService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private BacklogWorkService backlogWorkService;

    @Autowired
    private LoginMessageService loginMessageService;

    @Autowired
    private InterfaceManager interfaceManager;
    /**
     * 后台工作进展查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<TaskPageOutVO> getTaskPage(TaskPageInVO vo, SimplePageRequest page) {

        Map<String, Object> params = new WeakHashMap<String, Object>();

        StringBuilder sql = taskPublicQuerySQL(vo, params);
        JPage<TaskPageOutVO> pages = nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, TaskPageOutVO.class);

        List<TaskPageOutVO> outVOS = getTaskPageOutVOS(pages);
        JPageUtil.replaceConent(pages, outVOS);
        return pages;
    }

    /**
     * 共同查询sql
     *
     * @param vo
     * @param params
     * @return
     */
    private StringBuilder taskPublicQuerySQL(TaskPageInVO vo, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.project_code as projectCode," +
                        " t.project_name as projectName," +
                        " DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat," +
                        " t.responsible_department as responsibleDepartment, " +
                        " t.state as state, t.is_finish as isFinish, " +
                        " DATE_FORMAT(t.end_date,'%Y-%m-%d') as endDateFormat," +
                        " t.is_depart_finish as isDepartFinish," +
                        " t.work_content as workContent," +
                        " t.responsible_leader as responsibleLeader," +
                        " t.responsible_people as responsiblePeople," +
                        " t.subcontract_leader as subcontractLeader," +
                        " t.progress_work as progressWork," +
                        " t.batch as batch");
        sql.append("  from t_task t ");
        sql.append(" where t.is_available = :isAvailable ");

        if (StringUtils.isNotBlank(vo.getProjectName())) {
            sql.append(" and t.project_name like :projectName ");
            params.put("projectName", "%" + vo.getProjectName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getResponsibleDepartmentId())) {
            sql.append(" and t.responsible_department = :responsibleDepartment ");
            params.put("responsibleDepartment", vo.getResponsibleDepartmentId());
        }
        if (StringUtils.isNotBlank(vo.getIsDepartFinish())) {
            sql.append(" and t.is_depart_finish =:isDepartFinish ");
            params.put("isDepartFinish", vo.getIsDepartFinish());
        }

        if (StringUtils.isNotBlank(vo.getBatch())) {
            sql.append(" and t.batch =:batch ");
            params.put("batch", deCode(vo.getBatch()));
        }
        sql.append(" order by  t.create_date desc ");
        params.put("isAvailable", Availability.available.ordinal());
        return sql;
    }

    public String deCode(String str){
        String s = "";
        if(str.length() == 0) return "";
        s = str.replace("&amp;","&");
        return s;
    }

    /**
     * 添加工作进展
     *
     * @param pageTask
     * @return
     */
    @Override
    public Task addTask(TaskAddInVO pageTask) {
        Task task = new Task();
        Copy.simpleCopyExcludeNull(pageTask, task);
        task.setIsFinish(TaskConstant.TaskStatus.IS_FINISH_OFF);
        task.setIsDepartFinish(TaskConstant.TaskStatus.IS_DEPART_FINISH_OFF);
        task.setState(TaskConstant.TaskStatus.STATE_OFF);
        if (StringUtils.isNotBlank(pageTask.getEndDateStr())) {
            task.setEndDate(JDateUtils.parseDate(pageTask.getEndDateStr()));
        }
        if (StringUtils.isNotBlank(pageTask.getResponsiblePeople())) {
            task.setResponsiblePeople(pageTask.getResponsiblePeople());
        }
        if (StringUtils.isNotBlank(pageTask.getProgressWork())) {
            task.setProgressWork(pageTask.getProgressWork());
        } else {
            task.setProgressWork(TaskConstant.TaskStatus.SPIRT);
        }

        task.setBatch(pageTask.getBatch());
        // 责任领导人
        Department department = departmentSerive.getDepartmentByRemoteId(pageTask.getResponsibleDepartmentIds());
        List<SysUserPageOutVO> sysUserPageOutVOList =sysUserService.getResponsibleLeader(department.getId());

        if (sysUserPageOutVOList != null && sysUserPageOutVOList.size() > 0) {
            String leaderIds ="";
            for (SysUserPageOutVO sysUserPageOutVO :sysUserPageOutVOList) {
                leaderIds+=sysUserPageOutVO.getId()+",";
            }
            task.setResponsibleLeader(leaderIds.substring(0,leaderIds.length()-1));
        }

        task.setResponsibleDepartment(pageTask.getResponsibleDepartmentIds());
        internalTaskServiceImpl.saveOnly(task);

        if (StringUtils.isNotBlank(pageTask.getResponsiblePeople())) {
            // 待办任务
            BacklogWorkAddInVO backlogWorkAddInVO = new BacklogWorkAddInVO();
            backlogWorkAddInVO.setProjectName(pageTask.getProjectName());
            backlogWorkAddInVO.setAssignmentTask(pageTask.getWorkContent());
            backlogWorkAddInVO.setProjectId(task.getId());
            backlogWorkAddInVO.setProjectCreateTime(task.getCreateDate());
            backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_ONE);
            backlogWorkAddInVO.setLeaderId(pageTask.getResponsiblePeople());
            backlogWorkService.addBacklogWork(backlogWorkAddInVO);

            //往OA发通知消息
            JSONArray array = new JSONArray();
            SysUser sysUser = internalSysUserServiceImpl.getById(pageTask.getResponsiblePeople());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content", Constants.OAMessageType.CONTENT);
            jsonObject.put("remind_user",sysUser.getUserAccount());
            jsonObject.put("biz_system",Constants.OAMessageType.BIZ_SYSTEM );
            jsonObject.put("URL", "");
            array.add(jsonObject);
            logger.info("新增项目推进责任人:" + sysUser.getUserAccount());

            OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
            if(oaMessageOutVO.getSuccess().equals("true")){
                logger.info("新增项目推进：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
            }else{
                logger.info("新增项目推进：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
            }

        }
        // 责任领导人代办
        if (sysUserPageOutVOList != null && sysUserPageOutVOList.size() > 0) {
            for (SysUserPageOutVO sysUserPageOutVO :sysUserPageOutVOList) {
                BacklogWorkAddInVO backlogWorkAddIn = new BacklogWorkAddInVO();
                backlogWorkAddIn.setProjectName(pageTask.getProjectName());
                backlogWorkAddIn.setAssignmentTask(pageTask.getWorkContent());
                backlogWorkAddIn.setProjectId(task.getId());
                backlogWorkAddIn.setProjectCreateTime(task.getCreateDate());
                backlogWorkAddIn.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_ONE);
                backlogWorkAddIn.setLeaderId(sysUserPageOutVO.getId());
                backlogWorkService.addBacklogWork(backlogWorkAddIn);
            }
            //往OA发通知消息
            sendOAMessage(sysUserPageOutVOList);
        }
        return task;
    }

    public void sendOAMessage(List<SysUserPageOutVO> sysUserPageOutVOList){
        JSONArray array = new JSONArray();
        for (SysUserPageOutVO sysUserPageOutVO: sysUserPageOutVOList) {
            SysUser sysUser = internalSysUserServiceImpl.getById(sysUserPageOutVO.getId());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content", Constants.OAMessageType.CONTENT);
            jsonObject.put("remind_user",sysUser.getUserAccount());
            jsonObject.put("biz_system",Constants.OAMessageType.BIZ_SYSTEM );
            jsonObject.put("URL", "");
            array.add(jsonObject);
            logger.info("新增项目推进责任人领导:" + sysUser.getUserAccount());
        }
        //往OA发通知消息
        OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
        if(oaMessageOutVO.getSuccess().equals("true")){
            logger.info("新增项目推进：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
        }else{
            logger.info("新增项目推进：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
        }
    }

    /**
     * 删除工作进展
     *
     * @param ids
     */
    @Override
    public void deleteTaskById(String[] ids) {

        for (String id : ids) {
            deleteTaskById(id);
        }

    }

    public void deleteTaskById(String id) {
        if (StringUtils.isNotBlank(id)) {
            // 删除之前的待办
            backlogWorkService.deleteBacklogWorkByProjectId(id);
            Task task = internalTaskServiceImpl.getById(id);
            internalTaskServiceImpl.delete(task);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,task.getWorkContent(),Constants.projectModule.TASK);

        }
    }

    /**
     * 工作进展查看
     *
     * @param id
     * @return
     */
    @Override
    public TaskPageOutVO getTaskById(String id) {
        Task task = internalTaskServiceImpl.getById(id);
        TaskPageOutVO vo = new TaskPageOutVO();
        Copy.simpleCopyExcludeNull(task, vo);

        if (StringUtils.isNotBlank(task.getResponsibleDepartment())) {
            Department department = departmentSerive.getDepartmentById(task.getResponsibleDepartment());
            setDeptName(vo, department);
        }
        if (StringUtils.isNotBlank(task.getResponsiblePeople())) {
            SysUser sysUser = sysUserService.getSysUserById(task.getResponsiblePeople());
            if (sysUser != null) {
                vo.setResponsiblePeople(sysUser.getName());
            }
        }

        return vo;
    }

    private void setDeptName(TaskPageOutVO vo, Department department) {
        if (department != null) {
            vo.setResponsibleDepartment(department.getName());
        } else {
            vo.setResponsibleDepartment(null);
        }
    }

    /**
     * 工作进展 首页查询
     *
     * @param page
     * @return
     */
    @Override
    public JPage<TaskPageOutVO> selectTaskForFront(SimplePageRequest page) {
        List<TableFieldPageOutVO> tableFieldPageOutVOList =
                tableFieldService.showTableFieldList();
        String fieldName = processFieldName(tableFieldPageOutVOList);
        StringBuilder sql = new StringBuilder("select ");
        if (fieldName != null) {
            sql.append(fieldName);
        }
        sql.append("t.id");
        sql.append("  from t_task t ");
        sql.append(" where t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        sql.append(" order by  t.create_date desc ");


        params.put("isAvailable", Availability.available.ordinal());

        page.setPageSize(5);

        JPage<TaskPageOutVO> pages = nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, TaskPageOutVO.class);
        List<TaskPageOutVO> list = pages.getContent();
        List<TaskPageOutVO> outVOS = new ArrayList<>();
        for (TaskPageOutVO taskPageOutVO : list) {
            TaskPageOutVO out = Copy.simpleCopy(taskPageOutVO, TaskPageOutVO.class);
            if (StringUtils.isNotBlank(out.getResponsibleDepartment())) {
                setDeptNames(out);
            }
            for (TableFieldPageOutVO pageOutVO : tableFieldPageOutVOList) {
                if (TaskConstant.TaskName.RESPONSIBLE_PEOPLE.equals(pageOutVO.getFieldName())) {
                    setResponsiblePeople(out);
                    break;
                }
            }
            for (TableFieldPageOutVO pageOutVO : tableFieldPageOutVOList) {
                if (TaskConstant.TaskName.END_DATE.equals(pageOutVO.getFieldName())) {
                    if(StringUtils.isBlank(out.getEndDateFormat())){
                        out.setEndDateFormat(TaskConstant.TaskStatus.SPIRT);
                    }
                    break;
                }
            }
            outVOS.add(out);
        }
        JPageUtil.replaceConent(pages, outVOS);
        return pages;

    }

    private void setResponsiblePeople(TaskPageOutVO out) {
        if (StringUtils.isNotBlank(out.getResponsiblePeople())) {
            SysUser sysUser = sysUserService.getSysUserById(out.getResponsiblePeople());
            if (sysUser != null) {
                out.setResponsiblePeople(sysUser.getName());
            }
        } else {
            out.setResponsiblePeople("/");
        }
    }

    private List<TaskPageOutVO> getTaskPageOutVOS(JPage<TaskPageOutVO> pages) {
        List<TaskPageOutVO> list = pages.getContent();
        List<TaskPageOutVO> outVOS = new ArrayList<>();
        for (TaskPageOutVO taskPageOutVO : list) {
            TaskPageOutVO out = Copy.simpleCopy(taskPageOutVO, TaskPageOutVO.class);
            if (StringUtils.isNotBlank(out.getResponsibleDepartment())) {
                setDeptNames(out);
            }
            setResponsiblePeople(out);

            outVOS.add(out);
        }
        return outVOS;
    }

    private void setDeptNames(TaskPageOutVO out) {
        Department department = departmentSerive.getDepartmentById(out.getResponsibleDepartment());
        if (department != null) {
            if(StringUtils.isNotBlank(department.getName())){
                out.setResponsibleDepartment(department.getName());
            }else{
                out.setResponsibleDepartment(TaskConstant.TaskStatus.SPIRT);
            }
        } else {
            out.setResponsibleDepartment(TaskConstant.TaskStatus.SPIRT);
        }
    }

    /**
     * 拼接SQL
     *
     * @param tableFieldPageOutVOList
     * @return
     */

    private String processFieldName(List<TableFieldPageOutVO> tableFieldPageOutVOList) {
        StringBuilder sql = new StringBuilder();
        for (TableFieldPageOutVO pageOutVO : tableFieldPageOutVOList) {
            if (TaskConstant.TaskName.PROJECT_NAME.equals(pageOutVO.getFieldName())) {
                sql.append("t.project_name as projectName ,");
            } else if (TaskConstant.TaskName.RESPONSIBLE_DEPARTMENT.equals(pageOutVO.getFieldName())) {
                sql.append("t.responsible_department as responsibleDepartment ,");
            } else if (TaskConstant.TaskName.RESPONSIBLE_LEADER.equals(pageOutVO.getFieldName())) {
                sql.append("t.responsible_leader as responsibleLeader ,");
            } else if (TaskConstant.TaskName.RESPONSIBLE_PEOPLE.equals(pageOutVO.getFieldName())) {
                sql.append("t.responsible_people as responsiblePeople ,");
            } else if (TaskConstant.TaskName.STATE.equals(pageOutVO.getFieldName())) {
                sql.append("t.state as state ,");
            } else if (TaskConstant.TaskName.SUBCONTRACT_LEADER.equals(pageOutVO.getFieldName())) {
                sql.append("t.subcontract_leader as subcontractLeader ,");
            } else if (TaskConstant.TaskName.WORK_CONTENT.equals(pageOutVO.getFieldName())) {
                sql.append("t.work_content as workContent ,");
            } else if (TaskConstant.TaskName.IS_FINISH.equals(pageOutVO.getFieldName())) {
                sql.append("t.is_finish as isFinish ,");
            } else if (TaskConstant.TaskName.IS_DEPART_FINISH.equals(pageOutVO.getFieldName())) {
                sql.append("t.is_depart_finish as isDepartFinish ,");
            } else if (TaskConstant.TaskName.END_DATE.equals(pageOutVO.getFieldName())) {
                sql.append("DATE_FORMAT(t.end_date,'%Y-%m-%d') as endDateFormat ,");
            } else if (TaskConstant.TaskName.PROJECT_CODE.equals(pageOutVO.getFieldName())) {
                sql.append("t.project_code as projectCode ,");
            } else if (TaskConstant.TaskName.CREATE_DATE.equals(pageOutVO.getFieldName())) {
                sql.append("DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat ,");
            } else if (TaskConstant.TaskName.BATCH.equals(pageOutVO.getFieldName())) {
                sql.append("t.batch as batch,");
            }
        }
        if (sql != null && sql.length() > 0) {
            return sql.toString();
        }
        return null;
    }

    /**
     * 根据部门Id查找用户
     *
     * @param departmentId
     * @return
     */
    @Override
    public List<SysUserPageOutVO> selectUserInfo(String departmentId) {
        Department department = departmentSerive.getDepartmentByRemoteId(departmentId);
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " u.id as id, u.name as name");
        sql.append("  from t_sys_user u left JOIN t_sys_user_department su on u.id =su.user_id ");
        sql.append(" where su.is_available = :isAvailable and su.department_id =:departmentId and disabled = 1 and u.is_available = :isAvailable");

        Map<String, Object> params = new WeakHashMap<String, Object>();


        sql.append(" order by  su.create_date desc ");

        params.put("isAvailable", Availability.available.ordinal());
        params.put("departmentId", department.getId());

        return nativeQuery().setSql(sql.toString()).setParams(params).models(SysUserPageOutVO.class);
    }

    /**
     * 完成情况
     *
     * @param id
     */
    @Override
    public void updateTaskFinishStatusById(String id) {

        Task task = internalTaskServiceImpl.getById(id);
        task.setIsDepartFinish(TaskConstant.TaskStatus.IS_DEPART_FINISH_ON);

        internalTaskServiceImpl.updateOnly(task);

    }

    /**
     * 核查情况
     *
     * @param id
     */
    @Override
    public void updateTaskInspectStatusById(String id) {
        Task task = internalTaskServiceImpl.getById(id);
        task.setIsFinish(TaskConstant.TaskStatus.IS_FINISH_ON);
        internalTaskServiceImpl.updateOnly(task);
    }

    /**
     * 完成情况
     *
     * @param id
     */
    @Override
    public void updateFinishStatusById(String id) {
        Task task = internalTaskServiceImpl.getById(id);
        task.setIsDepartFinish(TaskConstant.TaskStatus.IS_DEPART_FINISH_OFF);
        internalTaskServiceImpl.updateOnly(task);
    }

    /**
     * 核查情况
     *
     * @param id
     */
    @Override
    public void updateInspectStatusById(String id) {
        Task task = internalTaskServiceImpl.getById(id);
        task.setIsFinish(TaskConstant.TaskStatus.IS_FINISH_OFF);
        internalTaskServiceImpl.updateOnly(task);
    }

    /**
     * 导出excel查询
     *
     * @param vo
     * @return
     */
    @Override
    public List<TaskPageOutVO> exportExcelQuery(TaskPageInVO vo) {

        Map<String, Object> params = new WeakHashMap<String, Object>();

        StringBuilder sql = taskPublicQuerySQL(vo, params);

        return nativeQuery().setSql(sql.toString()).setParams(params).models(TaskPageOutVO.class);
    }

    /**
     * 查询批次
     *
     * @return
     */
    @Override
    public List<TaskPageOutVO> queryBatch() {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                "  DISTINCT t.batch as batch ");
        sql.append("  from t_task t ");
        sql.append(" where t.is_available = :isAvailable  ");

        sql.append("  ORDER BY  t.create_date desc");

        Map<String, Object> params = new WeakHashMap<String, Object>();
        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).models(TaskPageOutVO.class);

    }


}
