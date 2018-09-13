package com.fykj.pds.leaderMessage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fykj._b._core.Constants;
import com.fykj._b._core.sysrole.model.SysRole;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.interfaceManage.InterfaceManager;
import com.fykj.pds.interfaceManage.vo.OAMessageOutVO;
import com.fykj.pds.leaderMessage.constant.LeaderMessageConstant;
import com.fykj.pds.leaderMessage.model.LeaderMessage;
import com.fykj.pds.leaderMessage.service.LeaderMessageService;
import com.fykj.pds.leaderMessage.vo.LeaderMessageAddInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessageEditInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.project.service.impl.ProjectServiceImpl;
import com.fykj.pds.task.constant.TaskConstant;
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

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/11/13
 * Time: 14:55
 * Description:
 **/
@Service
@Transactional
public class LeaderMessageServiceImpl extends ServiceSupport implements LeaderMessageService {

    public static final Logger logger = LoggerFactory.getLogger(LeaderMessageServiceImpl.class);

    private SingleEntityManager<LeaderMessage> internalLeaderMessageServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(LeaderMessage.class);


    private SingleEntityManager<SysUser> internalSysUserServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(SysUser.class);

    @Autowired
    private BacklogWorkService backlogWorkService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private LoginMessageService loginMessageService;

    @Autowired
    private InterfaceManager interfaceManager;

    /**
     * 领导留言查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<LeaderMessagePageOutVO> getLeaderMessagePage(LeaderMessagePageInVO vo, SimplePageRequest page) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.content as content," +
                        " DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat," +
                        "t.user_name as userName,t.finish_state as finishState ," +
                        "d.name as deptName , t.leader_id as leaderId");
        sql.append("  from t_leader_message t ");
        sql.append(" LEFT JOIN t_department d ON t.dept_id = d.remote_id");
        sql.append(" where t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        if (StringUtils.isNotBlank(vo.getContent())) {
            sql.append(" and t.content like :content ");
            params.put("content", "%" + vo.getContent() + "%");
        }

        if (StringUtils.isNotBlank(vo.getUserName())) {
            sql.append(" and t.user_name like :userName ");
            params.put("userName", "%" + vo.getUserName() + "%");
        }

        sql.append(" order by  t.create_date desc ");


        params.put("isAvailable", Availability.available.ordinal());

        return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, LeaderMessagePageOutVO.class);
    }

    /**
     * 新增领导留言
     *
     * @param pageLeaderMessageAddInVO
     * @return
     */
    @Override
    public LeaderMessage addLeaderMessage(LeaderMessageAddInVO pageLeaderMessageAddInVO) {
        LeaderMessage leaderMessage = new LeaderMessage();
        Copy.simpleCopyExcludeNull(pageLeaderMessageAddInVO, leaderMessage);
        leaderMessage.setDeptId(pageLeaderMessageAddInVO.getDeptId());
        leaderMessage.setFinishState(LeaderMessageConstant.LeaderMessageStatus.FINISH_OFF);
        leaderMessage.setLeaderId(pageLeaderMessageAddInVO.getLeaderId());
        // 留言人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        leaderMessage.setUserName(sessionUser.getUserName());
        internalLeaderMessageServiceImpl.saveOnly(leaderMessage);

        // 责任领导人
        if(StringUtils.isNotBlank(pageLeaderMessageAddInVO.getDeptId())) {
            Department department = departmentSerive.getDepartmentByRemoteId(pageLeaderMessageAddInVO.getDeptId());
            List<SysUserPageOutVO> sysUserPageOutVOList = sysUserService.getResponsibleLeader(department.getId());
            if (sysUserPageOutVOList != null && sysUserPageOutVOList.size() > 0) {
                for (SysUserPageOutVO sysUserPageOutVO : sysUserPageOutVOList) {
                    BacklogWorkAddInVO backlogWorkAddInVO = new BacklogWorkAddInVO();
                    String content = setContent(pageLeaderMessageAddInVO.getContent(), sessionUser);
                    backlogWorkAddInVO.setAssignmentTask(content);
                    backlogWorkAddInVO.setProjectId(leaderMessage.getId());
                    backlogWorkAddInVO.setProjectCreateTime(leaderMessage.getCreateDate());
                    backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO);
                    backlogWorkAddInVO.setLeaderId(sysUserPageOutVO.getId());
                    backlogWorkAddInVO.setProjectCode(null);
                    backlogWorkService.addBacklogWork(backlogWorkAddInVO);
                }
                //往OA发通知消息
                sendOAMessage(sysUserPageOutVOList);
            }
        }

        if(StringUtils.isNotBlank(pageLeaderMessageAddInVO.getDeptId())){
            // 待办任务
            BacklogWorkAddInVO backlogWorkAddInVO =new BacklogWorkAddInVO();

            String content = sessionUser.getUserName() +LeaderMessageConstant.LeaderMessageName.LEAVE_MESSAGE
                    + pageLeaderMessageAddInVO.getContent();

            backlogWorkAddInVO.setAssignmentTask(content);
            backlogWorkAddInVO.setProjectId(leaderMessage.getId());
            backlogWorkAddInVO.setProjectCreateTime(leaderMessage.getCreateDate());
            backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO);
            backlogWorkAddInVO.setLeaderId(pageLeaderMessageAddInVO.getLeaderId());
            backlogWorkService.addBacklogWork(backlogWorkAddInVO);

            //往OA发通知消息
            if(pageLeaderMessageAddInVO.getLeaderId() != null){
                JSONArray array = new JSONArray();
                SysUser sysUser = internalSysUserServiceImpl.getById(pageLeaderMessageAddInVO.getLeaderId());
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("content", Constants.OAMessageType.CONTENT);
                jsonObject.put("remind_user",sysUser.getUserAccount());
                jsonObject.put("biz_system",Constants.OAMessageType.BIZ_SYSTEM );
                jsonObject.put("URL", "");
                array.add(jsonObject);
                logger.info("领导指示代办人:" + sysUser.getUserAccount());

                OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
                if(oaMessageOutVO.getSuccess().equals("true")){
                    logger.info("新增领导指示：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
                }else{
                    logger.info("新增领导指示：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
                }
            }
        }

        return leaderMessage;
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
            logger.info("领导指示代办人领导:" + sysUser.getUserAccount());
        }
        //往OA发通知消息
        OAMessageOutVO oaMessageOutVO = interfaceManager.sendOAMessage(Constants.OAMessageType.SEND_USER,array.toString());
        if(oaMessageOutVO.getSuccess().equals("true")){
            logger.info("新增领导指示：" + Constants.OAMessageType.SEND_MESSAGE_SUCCESS);
        }else{
            logger.info("新增领导指示：" + Constants.OAMessageType.SEND_MESSAGE_FAIL +" " +oaMessageOutVO.getMsg());
        }
    }

    /**
     * 编辑领导留言
     *
     * @param pageLeaderMessageEditInVO
     */
    @Override
    public void editLeaderMessage(LeaderMessageEditInVO pageLeaderMessageEditInVO) {
        LeaderMessage leaderMessage = internalLeaderMessageServiceImpl.getById(pageLeaderMessageEditInVO.getId());
        // 内容
        leaderMessage.setContent(pageLeaderMessageEditInVO.getContent());
        // 公告提交人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        leaderMessage.setUserName(sessionUser.getUserName());
        leaderMessage.setDeptId(pageLeaderMessageEditInVO.getDeptId());
        leaderMessage.setLeaderId(pageLeaderMessageEditInVO.getLeaderId());
        internalLeaderMessageServiceImpl.updateOnly(leaderMessage);

        // 删除之前的待办
        backlogWorkService.deleteBacklogWorkByProjectId(leaderMessage.getId());
        // 责任领导人
//        List<SysUserPageOutVO> sysUserPageOutVOList =sysUserService.getResponsibleLeader(pageLeaderMessageEditInVO.getDeptId());
        if(StringUtils.isNotBlank(pageLeaderMessageEditInVO.getDeptId())) {
            Department department = departmentSerive.getDepartmentByRemoteId(pageLeaderMessageEditInVO.getDeptId());
            List<SysUserPageOutVO> sysUserPageOutVOList = sysUserService.getResponsibleLeader(department.getId());
            if (sysUserPageOutVOList != null && sysUserPageOutVOList.size() > 0) {
                for (SysUserPageOutVO sysUserPageOutVO : sysUserPageOutVOList) {
                    BacklogWorkAddInVO backlogWorkAddInVO = new BacklogWorkAddInVO();
                    String content = setContent(pageLeaderMessageEditInVO.getContent(), sessionUser);
                    backlogWorkAddInVO.setAssignmentTask(content);
                    backlogWorkAddInVO.setProjectId(leaderMessage.getId());
                    backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO);
                    backlogWorkAddInVO.setLeaderId(sysUserPageOutVO.getId());
                    backlogWorkAddInVO.setProjectCreateTime(leaderMessage.getModifyDate());
                    backlogWorkService.addBacklogWork(backlogWorkAddInVO);
                }
            }
        }

        if(StringUtils.isNotBlank(pageLeaderMessageEditInVO.getLeaderId())){
            BacklogWorkAddInVO backlogWorkAddInVO =new BacklogWorkAddInVO();

            String content = setContent(pageLeaderMessageEditInVO.getContent(), sessionUser);

            backlogWorkAddInVO.setAssignmentTask(content);
            backlogWorkAddInVO.setProjectId(leaderMessage.getId());
            backlogWorkAddInVO.setOperationType(BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO);
            backlogWorkAddInVO.setLeaderId(pageLeaderMessageEditInVO.getLeaderId());
            backlogWorkAddInVO.setProjectCreateTime(leaderMessage.getModifyDate());
            backlogWorkService.addBacklogWork(backlogWorkAddInVO);
        }


    }



    private String setContent(String content, SessionUser sessionUser) {
        return sessionUser.getUserName() + LeaderMessageConstant.LeaderMessageName.LEAVE_MESSAGE
                        + content;
    }

    /**
     * 删除领导留言
     *
     * @param ids
     */
    @Override
    public void deleteLeaderMessageById(String[] ids) {
        for (String id : ids) {
            deleteLeaderMessageById(id);
        }
    }

    private void deleteLeaderMessageById(String id) {
        if (StringUtils.isNotBlank(id)) {
            // 删除之前的待办
            backlogWorkService.deleteBacklogWorkByProjectId(id);
            LeaderMessage leaderMessage = internalLeaderMessageServiceImpl.getById(id);
            internalLeaderMessageServiceImpl.delete(leaderMessage);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,leaderMessage.getContent(),Constants.projectModule.LEADERMESSAGE);

        }
    }

    /**
     * 查看领导留言
     *
     * @param id
     * @return
     */
    @Override
    public LeaderMessagePageOutVO getLeaderMessageById(String id) {
        LeaderMessage leaderMessage = internalLeaderMessageServiceImpl.getById(id);
        LeaderMessagePageOutVO vo = new LeaderMessagePageOutVO();

        Copy.simpleCopyExcludeNull(leaderMessage, vo);
        if(StringUtils.isNotBlank(leaderMessage.getDeptId())){
            Department department = departmentSerive.getDepartmentById(leaderMessage.getDeptId());
            if(department != null){
                vo.setDeptName(department.getName());
            }
        }
        return vo;
    }

    /**
     * 领导留言首 页显示前三条
     *
     * @param page
     * @return
     */
    @Override
    public JPage<LeaderMessagePageOutVO> selectLeaderMessageForFront(SimplePageRequest page) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.creator_id as userId,t.content as content," +
                        " DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat," +
                        "t.user_name as userName ");
        sql.append("  from t_leader_message t ");
        sql.append(" where t.is_available = :isAvailable ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        sql.append(" order by  t.modify_date desc ");

        page.setPageSize(3);
        params.put("isAvailable", Availability.available.ordinal());

        JPage<LeaderMessagePageOutVO> pageResult = nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, LeaderMessagePageOutVO.class);

        pageResult.getContent().forEach(leaderMessagePageOutVO -> {
            //如果创建ID与登录用户相同可以修改删除
            then(leaderMessagePageOutVO);
        });

        return pageResult;
    }

    /**
     * the return one is same as the parameter
     *
     * @param leaderMessagePageOutVO
     * @return
     */
    private LeaderMessagePageOutVO then(LeaderMessagePageOutVO leaderMessagePageOutVO) {
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        if(sessionUser.getId().equals(leaderMessagePageOutVO.getUserId())){
            leaderMessagePageOutVO.setDelOrUpdateFlag(LeaderMessageConstant.DelOrUpdateFlag.YES);
        }else{
            leaderMessagePageOutVO.setDelOrUpdateFlag(LeaderMessageConstant.DelOrUpdateFlag.NO);
        }

        return leaderMessagePageOutVO;
    }

    /**
     * 完成情况
     * @param id
     * @param state
     */
    @Override
    public void updateFinishStateById(String id, String state) {
        LeaderMessage leaderMessage = internalLeaderMessageServiceImpl.getById(id);
        if(leaderMessage !=null ){
            leaderMessage.setFinishState(state);
            internalLeaderMessageServiceImpl.updateOnly(leaderMessage);
        }
    }
}
