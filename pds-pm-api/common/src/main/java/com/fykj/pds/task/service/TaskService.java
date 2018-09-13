package com.fykj.pds.task.service;

import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.task.model.Task;
import com.fykj.pds.task.vo.TaskAddInVO;
import com.fykj.pds.task.vo.TaskPageInVO;
import com.fykj.pds.task.vo.TaskPageOutVO;

import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 16:05
 * Description:
 **/
public interface TaskService {
    /**
     * 工作进展查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<TaskPageOutVO> getTaskPage(TaskPageInVO vo, SimplePageRequest page);

    /**
     * 新增工作进展
     *
     * @param pageTask
     */
    public Task addTask(TaskAddInVO pageTask);

    /**
     * 删除工作进展
     *
     * @param ids
     */
    public void deleteTaskById(String[] ids);

    /**
     * 查看工作进展
     *
     * @param id
     * @return
     */
    public TaskPageOutVO getTaskById(String id);

    /**
     * 通知公告首页显示前五条
     *
     * @return
     */
    public JPage<TaskPageOutVO> selectTaskForFront(SimplePageRequest page);

    /**
     * 根据部门Id查找用户
     *
     * @param departmentId
     * @return
     */
    public List<SysUserPageOutVO> selectUserInfo(String departmentId);

    /**
     * 完成情况
     *
     * @param id
     */

    public void updateTaskFinishStatusById(String id);


    /**
     * 核查情况
     *
     * @param id
     */

    public void updateTaskInspectStatusById(String id);

    /**
     * 完成情况
     *
     * @param id
     */
    public void updateFinishStatusById(String id);

    /**
     * 核查情况
     *
     * @param id
     */
    public void updateInspectStatusById(String id);

    /**
     * 导出excel查询
     *
     * @param vo
     * @return
     */
    public List<TaskPageOutVO> exportExcelQuery(TaskPageInVO vo);

    /**
     * 查询批次
     * @return
     */
    public List<TaskPageOutVO> queryBatch();


}
