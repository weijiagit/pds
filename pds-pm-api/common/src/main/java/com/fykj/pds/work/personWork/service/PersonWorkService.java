package com.fykj.pds.work.personWork.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.work.personWork.model.PersonWork;
import com.fykj.pds.work.personWork.vo.PersonWorkAddInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageOutVO;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 14:12
 * Description:
 **/
public interface PersonWorkService {

    /**
     * 个人办公任务查询
     *
     * @param vo
     * @param page
     * @return
     */
    public JPage<PersonWorkPageOutVO> getPersonWorkPage(PersonWorkPageInVO vo, SimplePageRequest page);

    /**
     * 新增个人办公任务
     *
     * @param pageTask
     */
    public PersonWork addPersonWork(PersonWorkAddInVO pageTask);

    /**
     * 删除个人办公任务
     *
     * @param ids
     */
    public void deletePersonWorkById(String[] ids);

    /**
     * 查看个人办公任务
     *
     * @param id
     * @return
     */
    public PersonWorkPageOutVO getPersonWorkById(String id);

    /**
     * 个人办公任务状态
     *
     * @param id
     */

    public void updateStatusById(String id);
}
