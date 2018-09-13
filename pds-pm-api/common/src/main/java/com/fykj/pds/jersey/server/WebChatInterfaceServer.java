package com.fykj.pds.jersey.server;


import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.departGovernment.service.DepartGovernmentService;
import com.fykj._b._core.login.service.LoginService;
import com.fykj._b._core.menu.service.MenuService;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj._b._core.tree.JTreeNode;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.security.SecurityService;
import com.fykj.pds.jersey.util.*;
import com.fykj.pds.leaderMessage.service.LeaderMessageService;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
import com.fykj.pds.notice.service.NoticeService;
import com.fykj.pds.notice.vo.NoticePageInVO;
import com.fykj.pds.notice.vo.NoticePageOutVO;
import com.fykj.pds.project.service.ProjectService;
import com.fykj.pds.project.vo.*;
import com.fykj.pds.task.service.TaskService;
import com.fykj.pds.task.vo.TaskPageInVO;
import com.fykj.pds.task.vo.TaskPageOutVO;
import com.fykj.pds.work.backlogWork.service.BacklogWorkService;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageOutVO;
import com.fykj.pds.workDynamics.service.WorkDynamicesService;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageOutVO;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2018/4/9
 * Time: 9:01
 * Description: 微信接口
 **/
@Path("/webChart/")
public class WebChatInterfaceServer {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WorkDynamicesService workDynamicesService;

    @Autowired
    private LeaderMessageService leaderMessageService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private BacklogWorkService backlogWorkService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DepartGovernmentService departGovernmentService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SysUserService sysUserService;


    /**
     * 工作动态分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("workDynamices/pageWorkDynamices")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // 返回数据的类型
    @Consumes(MediaType.APPLICATION_JSON) // 接受数据的类型
    public String pageWorkDynamices(PageUtil pageUtil) {
        // 结果处理
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        WorkDynamicesPageInVO vo = new WorkDynamicesPageInVO();
        JPage<WorkDynamicesPageOutVO> outVO = workDynamicesService.getWorkDynamicesPage(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 领导指示分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("leaderMsg/pageLeaderMsg")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pageLeaderMsg(PageUtil pageUtil) {
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        LeaderMessagePageInVO vo = new LeaderMessagePageInVO();
        JPage<LeaderMessagePageOutVO> outVO = leaderMessageService.getLeaderMessagePage(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 通知公告分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("notice/pageNotice")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pageNotice(PageUtil pageUtil) {
        // 结果处理
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        NoticePageInVO vo = new NoticePageInVO();
        JPage<NoticePageOutVO> outVO = noticeService.getNoticePage(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 我的待办工作分页查询 、我的已办工作分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("backlogWork/pageBacklogWork")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pageBacklogWork(PageUtil pageUtil) {
        // 结果处理
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        BacklogWorkPageInVO vo = new BacklogWorkPageInVO();
        vo.setState(pageUtil.getState());
        vo.setId(pageUtil.getId());
        JPage<BacklogWorkPageOutVO> outVO = backlogWorkService.getBacklogWorkPage(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 基础项目库分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("project/pageProject")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pageProject(PageUtil pageUtil) {
        // 结果处理
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        ProjectCriteriaVO vo = new ProjectCriteriaVO();
        vo.setProjectName(pageUtil.getProjectName());
        JPage<ProjectRecordVO> outVO = projectService.getProjects(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 基础项目库详情
     *
     * @param id
     * @return
     */
    @Path("project/getProjectDetail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String getProjectDetail(@QueryParam(value="id") String id) {
        // 结果处理
        PlanResult planResult;

        ProjectRecordVO vo = projectService.getProjectById(id);
        if (vo != null) {
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, vo);
        } else {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 基础项目历史记录查询
     *
     * @param projectId
     * @return
     */
    @Path("project/getProjectHistory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProjectHistory(@QueryParam(value="projectId") String projectId) {
        PlanResult planResult;
        ProjectDepartHistoryOutVO vo = projectService.getProjectFlowHistoryListById(projectId);
        if (vo != null) {
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, vo);
        } else {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 项目流程监控
     *
     * @param projectId
     * @return
     */
    @Path("project/getProjectFlowListById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getProjectFlowListById(@QueryParam(value="projectId") String projectId) {
        PlanResult planResult;
        ProjectDepartVO vo = projectService.getProjectFlowListById(projectId);
        if (vo != null) {
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, vo);
        } else {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 项目流程状态选择
     *
     * @param vo
     * @return
     */
    @Path("project/updateProjectFlowStatusById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateProjectFlowStatusById(ProjectDepartHistoryInVO vo) {
        PlanResult planResult;
        try {
            projectService.updateProjectFlowStatusById(vo);
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.UPDATE_SUCCESS_MSG, null);
        } catch (Exception e) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.UPDATE_ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 获取处理人
     *
     * @param departId
     * @return
     */
    @Path("project/selectUserInfoByDepartId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String selectUserInfoByDepartId(@QueryParam(value="departId") String departId) {
        PlanResult planResult;
        DepartGovernment departGovernment = departGovernmentService.getDepartGovernmentById(departId);
        List<SysUserPageOutVO> sysUserPageOutList = taskService.selectUserInfo(departGovernment.getDepartId());
        if (sysUserPageOutList.size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(sysUserPageOutList);
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }


    /**
     * 项目推进分页查询
     *
     * @param pageUtil
     * @return
     */
    @Path("task/pageTask")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pageTask(PageUtil pageUtil) {
        // 结果处理
        PlanResult planResult;
        SimplePageRequest page = new SimplePageRequest(pageUtil.getPageNumber(), pageUtil.getPageSize());
        TaskPageInVO vo = new TaskPageInVO();
        JPage<TaskPageOutVO> outVO = taskService.getTaskPage(vo, page);
        Long totalPageNumber = outVO.getTotalRecordNumber();
        if (outVO.getContent().size() <= 0) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null, totalPageNumber);
        } else {
            JSONArray jsonArray = JSONArray.fromObject(outVO.getContent());
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, jsonArray, totalPageNumber);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 项目推进详情
     *
     * @param id
     * @return
     */
    @Path("task/getTaskDetail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaskDetail(@QueryParam(value="id") String id) {
        PlanResult planResult;
        TaskPageOutVO vo = taskService.getTaskById(id);
        if (vo != null) {
            planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, vo);
        } else {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 用户登录
     *
     * @param pageUtil
     * @return
     */
    @Path("user/userLogin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String userLogin(PageUtil pageUtil) {
        PlanResult planResult;
        try {
            String taskFlag = WebChartConstants.TASK_FLAG_NO; // 没有
            WebChartUtil webChartUser = loginService.publicUserLogin(pageUtil);
            if (webChartUser.getCode().equals(WebChartConstants.ERROR_CODE)) {
                planResult = JSONUtils.result(webChartUser.getCode(), webChartUser.getMsg(), null);
            } else {
                taskFlag = isAccess(taskFlag, webChartUser.getId());
                planResult = JSONUtils.result(webChartUser.getCode(), webChartUser.getMsg(), webChartUser, taskFlag);
            }
        } catch (Exception e) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.ERROR_MSG, null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }

    /**
     * 是否有权限
     *
     * @param taskFlag
     * @param id
     * @return
     */
    private String isAccess(String taskFlag, String id) {
        List<JTreeNode> list = menuService.getMenuTreeByUser(id);
        for (JTreeNode jTreeNode : list) {
            List<JTreeNode> jTreeNodeList = jTreeNode.getChildren();
            for (JTreeNode jTreeNodes : jTreeNodeList) {
                // 项目推进菜单
                if (jTreeNodes.getId().equals(WebChartConstants.TASK_ID)) {
                    taskFlag = WebChartConstants.TASK_FLAG_YES; // 有
                    break;
                }
            }
        }
        return taskFlag;
    }

    /**
     * 账户绑定微信
     *
     * @param pageUtil
     * @return
     */
    @Path("user/userBindingWebChart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String userBindingWebChart(PageUtil pageUtil) {

        PlanResult planResult;
        try {
            SysUser dbUser = sysUserService.getSysUserByAccount(pageUtil.getUserAccount());

            WebChartUtil webChartUser = loginService.checkUserByCode(dbUser.getUserAccount(), dbUser.getPassword());
            if (webChartUser.getCode().equals(WebChartConstants.ERROR_CODE)) {
                planResult = JSONUtils.result(webChartUser.getCode(), webChartUser.getMsg(), null);
            } else {
                if (StringUtils.isNotBlank(pageUtil.getCode())) {
                    // 第一次请求 获取 openid
                    WebChartUtil webChartUtil = SnsApi.getOpenId(pageUtil.getCode());
                    if (webChartUtil.getCode().equals(WebChartConstants.ERROR_CODE)) {
                        planResult = JSONUtils.result(webChartUtil.getCode(), webChartUtil.getMsg(), null);
                    } else {
                        dbUser.setOpenId(webChartUtil.getOpenId());
                        sysUserService.editSysUser(dbUser);
                        String taskFlag = WebChartConstants.TASK_FLAG_NO; // 没有
                        taskFlag = isAccess(taskFlag, dbUser.getId());
                        planResult = JSONUtils.result(WebChartConstants.SUCCESS_CODE, WebChartConstants.SUCCESS_MSG, dbUser, taskFlag);
                    }
                } else {
                    planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, WebChartConstants.CODE_ERROR_MSG, null);
                }
            }
        } catch (Exception e) {
            planResult = JSONUtils.result(WebChartConstants.ERROR_CODE, e.getMessage(), null);
        }
        return JSONUtils.toJSONObject(planResult).toString();
    }


}
