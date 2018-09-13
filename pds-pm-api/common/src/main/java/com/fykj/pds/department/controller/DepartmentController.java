package com.fykj.pds.department.controller;

import com.fykj._b._core.tree.JTreeNode;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.model.SysUserDepartment;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.department.vo.AllocationDepartmentInVO;
import com.fykj.pds.department.vo.DepartmentEditInVO;
import com.fykj.pds.department.vo.DepartmentSaveInVO;
import com.fykj.util.Copy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 15:06
 * Description:
 **/
@Controller
@RequestMapping("/department")
public class DepartmentController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartmentSerive departmentSerive;

    /**
     * 获取父节点id
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getPid", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getPid(String id) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        result.put("pid", id);
        return InvokeResult.success(result);
    }

    /**
     * 加载部门菜单数
     *
     * @return
     */
    @RequestMapping(path = "/loadDepartmentTree", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult loadDepartmentTree() throws Exception {
        List<JTreeNode> list = departmentSerive.getDepartmentTree();
        return InvokeResult.success(list);
    }

    /**
     * 保存部门
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveDepartment", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveDepartment(DepartmentSaveInVO vo) throws Exception {
        Department department = departmentSerive.saveDepartment(Copy.simpleCopy(vo, Department.class));
        return InvokeResult.success(department);
    }

    /**
     * 根据ID获取部门
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getDepartmentById", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getDepartmentById(String id) throws Exception {
        Department department = departmentSerive.getDepartmentById(id);
        return InvokeResult.success(department);
    }

    /**
     * 编辑部门
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/editDepartment", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editDepartment(DepartmentEditInVO vo) throws Exception {
        departmentSerive.editDepartment(Copy.simpleCopy(vo, Department.class));
        return InvokeResult.success(true);
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/deleteDepartment", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteDepartment(String id) throws Exception {
        departmentSerive.deleteDepartment(id);
        return InvokeResult.success(true);
    }

    /**
     * 分配用户
     *
     * @param allocationDepartmentInVO
     * @return
     */
    @RequestMapping(path = "/allocationUser", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult allocationUser(AllocationDepartmentInVO allocationDepartmentInVO) throws Exception {
        departmentSerive.allocationDepartment(allocationDepartmentInVO);
        return InvokeResult.success(true);
    }

    /**
     * 获取已分配的部门
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = "/getAssignUserDepartment", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getAssignUserDepartment(String userId) throws Exception {
        List<SysUserDepartment> sysUserDepartmentList = departmentSerive.getAssignUserDepartment(userId);
        return InvokeResult.success(sysUserDepartmentList);
    }


}
