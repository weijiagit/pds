package com.fykj.pds.department.service;

import com.fykj._b._core.tree.JTreeNode;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.model.SysUserDepartment;
import com.fykj.pds.department.vo.AllocationDepartmentInVO;

import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 13:58
 * Description:
 **/
public interface DepartmentSerive {

    public List<JTreeNode> getDepartmentTree();

    public Department saveDepartment(Department department);

    public Department getDepartmentById(String id);
    
    public Department getDepartmentByRemoteId(String remoteId);

    public void editDepartment(Department department);

    public void deleteDepartment(String id);

    public void allocationDepartment(AllocationDepartmentInVO allocationDepartmentInVO );

    public List<SysUserDepartment> getAssignUserDepartment(String userId);

    public List<Department> getUserDepartmentName(String userId);
    
    public void assignUserDept(String userId, String deptId);
    
    public void delDeptByUserId(String userId);
    
}
