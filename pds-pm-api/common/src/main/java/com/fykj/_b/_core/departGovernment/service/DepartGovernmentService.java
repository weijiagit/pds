package com.fykj._b._core.departGovernment.service;


import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentEditInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageOutVO;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;

/**
 * Created by weijia on 2017/12/21.
 *
 */
public interface DepartGovernmentService {

    /**
     * 查询部门行政审批列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    JPage<DepartGovernmentPageOutVO> getDepartGovernmentPage(DepartGovernmentPageInVO vo, SimplePageRequest pageVo);


    /**
     * 保存部门行政审批
     *
     * @param departGovernment
     * @return
     */
    void saveDepartGovernment(DepartGovernment departGovernment);


    /**
     * 逻辑删除部门行政审批
     *
     * @param ids
     * @return
     */
    void removeSysUser(String[] ids);

    /**
     * 根据id获取部门审批信息
     *
     * @param id
     * @return
     */
    DepartGovernmentPageOutVO getDepartGovernmentById(String id);

    /**
     * 编辑部门审批信息
     *
     * @param InVO
     * @return
     */
    void editDepartGovernment(DepartGovernmentEditInVO InVO);
}
