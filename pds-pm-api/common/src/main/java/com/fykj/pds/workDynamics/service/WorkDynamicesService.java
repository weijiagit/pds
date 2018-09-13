package com.fykj.pds.workDynamics.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.workDynamics.model.WorkDynamics;
import com.fykj.pds.workDynamics.vo.WorkDynamicesAddInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesEditInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageOutVO;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 11:22
 * Description:
 **/
public interface WorkDynamicesService {

    /**
     * 工作动态查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<WorkDynamicesPageOutVO> getWorkDynamicesPage(WorkDynamicesPageInVO vo, SimplePageRequest page);

    /**
     * 新增工作动态
     *
     * @param pageWorkDynamice
     */
    public WorkDynamics addWorkDynamices(WorkDynamicesAddInVO pageWorkDynamice);

    /**
     * 修改工作动态
     *
     * @param pageWorkDynamice
     */
    public void editWorkDynamices(WorkDynamicesEditInVO pageWorkDynamice);

    /**
     * 删除工作动态
     *
     * @param ids
     */
    public void deleteWorkDynamices(String[] ids);

    /**
     * 查看工作动态
     *
     * @param id
     * @return
     */
    public WorkDynamicesPageOutVO getWorkDynamicesById(String id);

    /**
     * 工作动态首页显示前五条
     *
     * @return
     */
    public JPage<WorkDynamicesPageOutVO> selectWorkDynamicesForFront(SimplePageRequest page);

}
