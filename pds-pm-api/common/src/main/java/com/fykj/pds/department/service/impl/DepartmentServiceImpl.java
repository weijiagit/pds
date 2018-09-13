package com.fykj.pds.department.service.impl;

import com.fykj.TreeConstant;
import com.fykj._b._core.sysrole.SysRoleCodesTable;
import com.fykj._b._core.tree.JTreeNode;
import com.fykj._b._core.tree.JTreeNodeMenu;
import com.fykj._b._core.tree.JTreeUtils;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.model.SysUserDepartment;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.department.vo.AllocationDepartmentInVO;
import com.fykj.pds.department.vo.DepartmentTreeTemplate;
import com.fykj.util.Copy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 14:15
 * Description:
 **/
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceSupport implements DepartmentSerive {

    private SingleEntityManager<Department> internalDepartmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(Department.class);

    private SingleEntityManager<SysUserDepartment> internalSysUserDepartmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(SysUserDepartment.class);

    /**
     * 部门菜单结构
     *
     * @return
     */
    @Override
    public List<JTreeNode> getDepartmentTree() {
        List<Department> list = internalDepartmentServiceImpl.singleEntityQuery2().condition()
                .equals("isAvailable", Availability.available.ordinal()).ready().models();

        List<DepartmentTreeTemplate> data = new ArrayList<DepartmentTreeTemplate>();
        for (Department department : list) {
            data.add(Copy.simpleCopy(department, DepartmentTreeTemplate.class));
        }

        return JTreeUtils.buildTree("0", data, JTreeNodeMenu.class,TreeConstant.TREE_TYPE_REMOTE);
    }

    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    @Override
    public Department saveDepartment(Department department) {
        internalDepartmentServiceImpl.saveOnly(department);
        return department;
    }

    /**
     * 查看部门
     *
     * @param id
     * @return
     */
    @Override
    public Department getDepartmentById(String id) {
       /* Department department =null;
        try {
            department = internalDepartmentServiceImpl.getById(id);
            if(department != null){
                return department;
            }
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return department;
        }
        return department;*/
        Department department = internalDepartmentServiceImpl.singleEntityQuery2().condition()
                .equals("remoteId", id).ready().model();
        return department;

    }
    
    @Override
    public Department getDepartmentByRemoteId(String remoteId) {
        Department department = internalDepartmentServiceImpl.singleEntityQuery2().condition()
                .equals("remoteId", remoteId).ready().model();
        return department;

    }

    /**
     * 编辑部门
     *
     * @param department
     */
    @Override
    public void editDepartment(Department department) {

        Department dept = internalDepartmentServiceImpl.getById(department.getId());
        dept.setName(department.getName());
        internalDepartmentServiceImpl.updateOnly(dept);

    }

    /**
     * 删除部门
     *
     * @param id
     */
    @Override
    public void deleteDepartment(String id) {

        if (StringUtils.isNotBlank(id)) {
            // 删除这个部门下的所有用户
            delAllUsersByDeptId(id);
            // 删除部门
            Department department = internalDepartmentServiceImpl.getById(id);
            internalDepartmentServiceImpl.delete(department);
        }

    }

    /**
     * 根据部门ID物理删除部门下所有的用户
     *
     * @param id
     */
    private void delAllUsersByDeptId(String id) {
        Map<String, Object> mp = new HashMap<>();
        String sql = " DELETE FROM t_sys_user_department WHERE department_id =:deptId";
        mp.put("deptId", id);
        nativeQuery().setSql(sql).setParams(mp).setUpdate(true).model();
    }

    /**
     * 分配部门
     *
     * @param allocationDepartmentInVO
     */
    @Override
    public void allocationDepartment(AllocationDepartmentInVO allocationDepartmentInVO) {

        String[] selected = allocationDepartmentInVO.getSelected().split(",");
        String[] undetermined = allocationDepartmentInVO.getUndetermined().split(",");
        String userId = allocationDepartmentInVO.getUserId();
        // 分配部门之前先删除，再添加
        delDeptByUserId(allocationDepartmentInVO.getUserId());

        if (null != selected && selected.length > 0) {
            assignUserDept(userId, selected);
        }
        if (null != undetermined && undetermined.length > 0) {
            for (String str : undetermined) {
                assignUserDept(userId, str, SysRoleCodesTable.MenuCheckState.UNDETERMINED);
            }
        }
    }

    private void assignUserDept(String userId, String[] deptIds) {
        delDeptByUserId(userId);
        for (String deptId : deptIds) {
            assignUserDept(userId, deptId);
        }
    }
    

    @Override
    public void assignUserDept(String userId, String deptId) {
        assignUserDept(userId, deptId, SysRoleCodesTable.MenuCheckState.SELECTED);
    }

    /**
     * 用户分配部门
     *
     * @param userId
     * @param deptId
     * @param checkState
     */
    public void assignUserDept(String userId, String deptId, String checkState) {
        if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(deptId)) {
            SysUserDepartment sysUserDepartment = new SysUserDepartment();
            sysUserDepartment.setUserId(userId);
            sysUserDepartment.setDepartmentId(deptId);
            sysUserDepartment.setCheckState(checkState);
            internalSysUserDepartmentServiceImpl.saveOnly(sysUserDepartment);
        }
    }

    @Override
    public void delDeptByUserId(String userId) {
        List<SysUserDepartment> list = internalSysUserDepartmentServiceImpl.singleEntityQuery2().conditionDefault()
                .equals("userId", userId).ready().models();
        if (null != list && !list.isEmpty()) {
            internalSysUserDepartmentServiceImpl.deleteAllByModels(list);
        }
    }

    /**
     * 用户所对应的部门
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysUserDepartment> getAssignUserDepartment(String userId) {
        List<SysUserDepartment> list = internalSysUserDepartmentServiceImpl.singleEntityQuery2().conditionDefault()
                .equals("userId", userId).ready().models();
        return list;
    }

    @Override
    public List<Department> getUserDepartmentName(String userId) {
        StringBuilder sql = new StringBuilder("select d.name ");
        sql.append("  from t_department d left join t_sys_user_department s on d.id =s.department_id ");
        sql.append(" where s.is_available =:isAvailable and s.user_id=:userId and s.check_state=:checkState ");

        Map<String, Object> params = new WeakHashMap<String, Object>();

        params.put("isAvailable", Availability.available.ordinal());
        params.put("userId", userId);
        params.put("checkState", SysRoleCodesTable.MenuCheckState.SELECTED);

        return nativeQuery().setSql(sql.toString()).setParams(params).models(Department.class);
    }
}
