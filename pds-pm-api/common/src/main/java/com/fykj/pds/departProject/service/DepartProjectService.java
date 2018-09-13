package com.fykj.pds.departProject.service;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.departProject.model.DepartProject;
import com.fykj.pds.departProject.vo.DepartProjectAddInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageOutVO;
import com.fykj.pds.project.vo.ProjectSaveVO;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/
public interface DepartProjectService {

    /**
     * 获取部门上报项目列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    JPage<DepartProjectPageOutVO> getDepartProjectPage(DepartProjectPageInVO vo, SimplePageRequest pageVo);


    /**
     * 保存部门上报项目
     *
     * @param vo
     * @return
     */
    DepartProject saveDepartProject(DepartProjectAddInVO vo);

    /**
     * 查看部门上报项目
     *
     * @param id
     * @return
     */
    DepartProjectPageOutVO getDepartProjectById(String id);

    /**
     * 编辑部门上报项目
     *
     * @param departProjectAddInVO
     */
    void editDepartProject(DepartProjectAddInVO departProjectAddInVO);

    /**
     * 删除部门上报项目
     *
     * @param ids
     */
    void deleteDepartProject(String[] ids);
}
