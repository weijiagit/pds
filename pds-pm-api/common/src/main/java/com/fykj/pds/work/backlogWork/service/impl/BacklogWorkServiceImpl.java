package com.fykj.pds.work.backlogWork.service.impl;

import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.leaderMessage.constant.LeaderMessageConstant;
import com.fykj.pds.leaderMessage.model.LeaderMessage;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
import com.fykj.pds.task.constant.TaskConstant;
import com.fykj.pds.task.model.Task;
import com.fykj.pds.task.service.TaskService;
import com.fykj.pds.work.backlogWork.constant.BacklogWorkContant;
import com.fykj.pds.work.backlogWork.model.BacklogWork;
import com.fykj.pds.work.backlogWork.service.BacklogWorkService;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkAddInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 16:07
 * Description:
 **/
@Service
@Transactional
public class BacklogWorkServiceImpl extends ServiceSupport implements BacklogWorkService {

    private SingleEntityManager<BacklogWork> internalBakclogWorkServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(BacklogWork.class);

    private SingleEntityManager<Task> internalTaskServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Task.class);

    private SingleEntityManager<LeaderMessage> internalLeaderMessageServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(LeaderMessage.class);


    /**
     * 待办工作、已办工作查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<BacklogWorkPageOutVO> getBacklogWorkPage(BacklogWorkPageInVO vo, SimplePageRequest page) {

        StringBuilder sql = new StringBuilder("select ");
        sql.append(
                " t.id as id, t.project_code as projectCode,t.state as state ," +
                        " DATE_FORMAT(t.project_create_time,'%Y-%m-%d') as projectCreateTimeFormat," +
                        "t.project_name as projectName,t.assignment_task as assignmentTask ," +
                        " t.hasten_task as hastenTask, t.project_id as projectId, t.operation_type as operationType");
        sql.append("  from t_backlog_work t ");
        sql.append(" where t.is_available = :isAvailable and t.leader_id =:leaderId");
        Map<String, Object> params = new WeakHashMap<String, Object>();

        if (StringUtils.isNotBlank(vo.getState())) {
            sql.append(" and t.state = :state ");
            params.put("state", vo.getState());
        }
        sql.append(" order by  t.create_date desc ");

        params.put("isAvailable", Availability.available.ordinal());
        params.put("leaderId", vo.getId());

        return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, BacklogWorkPageOutVO.class);
    }

    /**
     * 新增待办工作
     *
     * @param vo
     * @return
     */
    @Override
    public BacklogWork addBacklogWork(BacklogWorkAddInVO vo) {

       /* BacklogWork backlogWorkVO = internalBakclogWorkServiceImpl.
                singleEntityQuery2().conditionDefault().equals("projectId", vo.getProjectId()).ready().model();
*/

        BacklogWork backlogWork = new BacklogWork();
        Copy.simpleCopyExcludeNull(vo, backlogWork);

        backlogWork.setState(BacklogWorkContant.BacklogWorkStatus.STATE_OFF);

        if (StringUtils.isEmpty(vo.getProjectCode())) {
            backlogWork.setProjectCode(BacklogWorkContant.BacklogWorkStatus.SPRIT);
        }
        if (StringUtils.isEmpty(vo.getProjectName())) {
            backlogWork.setProjectName(BacklogWorkContant.BacklogWorkStatus.SPRIT);
        }
        if (StringUtils.isEmpty(vo.getHastenTask())) {
            backlogWork.setHastenTask(BacklogWorkContant.BacklogWorkStatus.SPRIT);
        }
        if (StringUtils.isEmpty(vo.getAssignmentTask())) {
            backlogWork.setAssignmentTask(BacklogWorkContant.BacklogWorkStatus.SPRIT);
        }
        if (vo.getProjectCreateTime()!=null) {
            backlogWork.setProjectCreateTime(vo.getProjectCreateTime());
        }
        backlogWork.setLeaderId(vo.getLeaderId());

        internalBakclogWorkServiceImpl.saveOnly(backlogWork);


        return backlogWork;
    }

    /**
     * 处理待办
     *
     * @param id
     * @param state
     */
    @Override
    public void updateStateById(String id, String state) {
        BacklogWork backlogWork = internalBakclogWorkServiceImpl.getById(id);
        String operationType = backlogWork.getOperationType();
        String projectId = backlogWork.getProjectId();
        backlogWork.setState(state);
        internalBakclogWorkServiceImpl.updateOnly(backlogWork);
        // 项目推进 or 领导指示
//        if (BacklogWorkContant.BacklogWorkStatus.OPERATION_ONE.equals(operationType) || BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO.equals(operationType)){
//            List<BacklogWork> backlogWorkList = internalBakclogWorkServiceImpl.singleEntityQuery2().
//                    conditionDefault().equals("projectId",projectId).ready().models();
//            for(BacklogWork backlogWorks : backlogWorkList){
//                BacklogWork bw= internalBakclogWorkServiceImpl.getById(backlogWorks.getId());
//                if(bw != null){
//                    bw.setState(state);
//                    internalBakclogWorkServiceImpl.updateOnly(bw);
//                }
//            }
//        }else{// 项目基础
//            backlogWork.setState(state);
//            internalBakclogWorkServiceImpl.updateOnly(backlogWork);
//        }

        // 项目推进
        if (BacklogWorkContant.BacklogWorkStatus.OPERATION_ONE.equals(operationType)){
            if(StringUtils.isNotBlank(projectId)){
                Task task = internalTaskServiceImpl.getById(projectId);
                task.setIsDepartFinish(TaskConstant.TaskStatus.IS_DEPART_FINISH_ON);
                internalTaskServiceImpl.updateOnly(task);
            }
        }else if(BacklogWorkContant.BacklogWorkStatus.OPERATION_TWO.equals(operationType)){ // 领导指示
            if(StringUtils.isNotBlank(projectId)){
                LeaderMessage leaderMessage = internalLeaderMessageServiceImpl.getById(projectId);
                leaderMessage.setFinishState(LeaderMessageConstant.LeaderMessageStatus.FINISH_ON);
                internalLeaderMessageServiceImpl.updateOnly(leaderMessage);
            }
        }

    }

    /**
     * 查看待办详情
     * @param id
     * @return
     */
    @Override
    public BacklogWorkPageOutVO getPendingWorkById(String id) {
        BacklogWork backlogWork = internalBakclogWorkServiceImpl.getById(id);
        BacklogWorkPageOutVO vo = new BacklogWorkPageOutVO();
        Copy.simpleCopyExcludeNull(backlogWork, vo);
        return vo;
    }

    /**
     * 物理删除待办
     * @param projectId
     */
    @Override
    public void deleteBacklogWorkByProjectId(String projectId) {
        String sql = "delete from t_backlog_work where project_id =:projectId";
        Map<String, Object> params = new WeakHashMap<String, Object>();
        params.put("projectId",projectId);
        nativeQuery().setSql(sql).setParams(params).setUpdate(true).model();
    }
}
