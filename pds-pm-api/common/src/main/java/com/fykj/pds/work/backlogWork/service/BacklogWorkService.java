package com.fykj.pds.work.backlogWork.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
import com.fykj.pds.work.backlogWork.model.BacklogWork;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkAddInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageOutVO;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 16:00
 * Description:
 **/
public interface BacklogWorkService {
    /**
     * 待办工作、已办工作查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<BacklogWorkPageOutVO> getBacklogWorkPage(BacklogWorkPageInVO vo, SimplePageRequest page);

    /**
     * 新增待办工作
     *
     * @param vo
     */
    public BacklogWork addBacklogWork(BacklogWorkAddInVO vo);

    /**
     * 处理待办
     *
     * @param projectId
     */
    public void updateStateById(String projectId,String state);

    /**
     * 查看待办
     *
     * @param id
     * @return
     */
    public BacklogWorkPageOutVO getPendingWorkById(String id);

    /**
     * 物理删除待办
     * @param projectId
     */
    public void deleteBacklogWorkByProjectId (String projectId);
}
