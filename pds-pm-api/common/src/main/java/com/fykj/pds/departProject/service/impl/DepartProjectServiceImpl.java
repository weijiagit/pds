package com.fykj.pds.departProject.service.impl;

import com.fykj._b._core.Constants;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.departProject.constant.DepartProjectConstant;
import com.fykj.pds.departProject.model.DepartProject;
import com.fykj.pds.departProject.service.DepartProjectService;
import com.fykj.pds.departProject.vo.DepartProjectAddInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageOutVO;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.model.SysUserDepartment;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.project.vo.DepartProjectAdminOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import com.fykj.util.JStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/

@Service
@Transactional
public class DepartProjectServiceImpl extends ServiceSupport implements DepartProjectService {

    private SingleEntityManager<DepartProject> internalDepartProjectServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(DepartProject.class);

    private SingleEntityManager<SysUserDepartment> internalSysUserDepartmentServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(SysUserDepartment.class);

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LoginMessageService loginMessageService;
    /**
     * 获取部门上报项目列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @Override
    public JPage<DepartProjectPageOutVO> getDepartProjectPage(DepartProjectPageInVO vo, SimplePageRequest pageVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select t.id as id, " +
                " t.project_name as projectName, " +
                " t.company_name as companyName, " +
                " t.construction_content_scale as constructionContentScale, " +
                " t.total_investment as totalInvestment, " +
                " t.plan_begin_date as planBeginDate, " +
                " t.plan_end_date as planEndDate, " +
                " t.depart_id as departId, " +
                " t.user_id as userId, " +
                " t1.name as departName, " +
                " t2.name as userName " +
                " from t_depart_project t " +
                " left join t_department t1 on t.depart_id = t1.remote_id and t1.is_available = :isAvailable " +
                " left join t_sys_user t2 on t.user_id = t2.id and t2.is_available = :isAvailable" +
                " where t.is_available = :isAvailable ";

        // 部门
        if (StringUtils.isNotBlank(vo.getDepartmentId())) {
            sql += (" and t.depart_id = :departId ");
            map.put("departId", vo.getDepartmentId());
        }

        // 内容
        if (StringUtils.isNotBlank(vo.getProjectName())) {
            sql += (" and t.project_name like :projectName ");
            map.put("projectName", "%" + vo.getProjectName() + "%");
        }

        sql += (" order by t.modify_date desc ");

        map.put("isAvailable", Availability.available.ordinal());
        JPage<DepartProjectPageOutVO> page = nativeQuery().setSql(sql).setParams(map).modelPage(pageVo, DepartProjectPageOutVO.class);
        page.getContent().forEach(departProjectPageOutVO -> {
            then(departProjectPageOutVO);
        });

        return page;
    }

    /**
     * the return one is same as the parameter
     *
     * @param departProjectPageOutVO
     * @return
     */
    private DepartProjectPageOutVO then(DepartProjectPageOutVO departProjectPageOutVO) {

        //时间转换
        if (null == departProjectPageOutVO.getPlanBeginDate()) {
            departProjectPageOutVO.setPlanBeginDateStr("");
        } else {
            departProjectPageOutVO.setPlanBeginDateStr(JDateUtils.format(departProjectPageOutVO.getPlanBeginDate()));
        }

        if (null == departProjectPageOutVO.getPlanEndDate()) {
            departProjectPageOutVO.setPlanEndDateStr("");
        } else {
            departProjectPageOutVO.setPlanEndDateStr(JDateUtils.format(departProjectPageOutVO.getPlanEndDate()));
        }

        //当前登录用户ID
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        String userId = sessionUser.getId();
        List<DepartProjectAdminOutVO> departProjectAdminOutVOList = getDepartProjectAdmin(userId);
        //如果是系统超级管理员
        if(departProjectAdminOutVOList.size() >= 1){
            departProjectPageOutVO.setDelOrUpdateFlag(DepartProjectConstant.DelOrUpdateFlag.YES);
        }else{
            //当前登录用户所属部门集合
            List<SysUserDepartment> sysUserDepartmentList = departmentSerive.getAssignUserDepartment(userId);
            Department department = departmentSerive.getDepartmentByRemoteId(departProjectPageOutVO.getDepartId());
            //如果登录用户的部门在所属部门集合中显示修改删除按钮

            int i = 0;
            for (SysUserDepartment sysUserDepartment: sysUserDepartmentList) {
                if(sysUserDepartment.getDepartmentId().equals(department.getId())){
                    i++;
                }
            }
            if(i >= 1){
                departProjectPageOutVO.setDelOrUpdateFlag(DepartProjectConstant.DelOrUpdateFlag.YES);
            }else{
                departProjectPageOutVO.setDelOrUpdateFlag(DepartProjectConstant.DelOrUpdateFlag.NO);
            }
        }
        return departProjectPageOutVO;
    }

    public List<DepartProjectAdminOutVO> getDepartProjectAdmin(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select t.id as id,t.user_id as userId" +
                " from t_sys_user_role t " +
                " left join t_sys_role t1 on t1.id = t.role_id and t1.is_available = :isAvailable " +
                " where t.user_id = :userId " +
                " and t.is_available = :isAvailable and t1.name = :administrator";
        map.put("isAvailable", Availability.available.ordinal());
        map.put("userId",userId);
        map.put("administrator", DepartProjectConstant.Administrator.ADMINISTRATOR);
        List<DepartProjectAdminOutVO> departProjectAdminOutVOList = nativeQuery().setSql(sql).setParams(map).models(DepartProjectAdminOutVO.class);
        return departProjectAdminOutVOList;
    }

    /**
     * 保存部门上报项目
     *
     * @param vo
     * @return
     */
    @Override
    public DepartProject saveDepartProject(DepartProjectAddInVO vo) {
        DepartProject departProject = new DepartProject();
        Copy.simpleCopyExcludeNull(vo, departProject);
        //总投资
        if (JStringUtils.isNotNullOrEmpty(vo.getTotalInvestmentStr())) {
            departProject.setTotalInvestment(Double.valueOf(vo.getTotalInvestmentStr()).doubleValue());
        } else {
            departProject.setTotalInvestment(0.00);
        }

        //计划开工时间
        departProject.setPlanBeginDate(JDateUtils.parseDate(vo.getPlanBeginDateStr()));
        //计划竣工时间
        departProject.setPlanEndDate(JDateUtils.parseDate(vo.getPlanEndDateStr()));
        internalDepartProjectServiceImpl.saveOnly(departProject);
        return departProject;
    }

    /**
     * 查看部门上报项目
     *
     * @param id
     * @return
     */
    @Override
    public DepartProjectPageOutVO getDepartProjectById(String id) {
        DepartProject departProject = internalDepartProjectServiceImpl.getById(id);
        DepartProjectPageOutVO vo = new DepartProjectPageOutVO();
        Copy.simpleCopyExcludeNull(departProject, vo);

        Department department = departmentSerive.getDepartmentById(vo.getDepartId());
        if(department != null){
            vo.setDepartName(department.getName());
        }else{
            vo.setDepartName("");
        }

        SysUser sysUser = sysUserService.getSysUserById(vo.getUserId());
        if(department != null){
            vo.setUserName(sysUser.getName());
        }else{
            vo.setUserName("");
        }

        //时间转换
        if (null == vo.getPlanBeginDate()) {
            vo.setPlanBeginDateStr("");
        } else {
            vo.setPlanBeginDateStr(JDateUtils.format(vo.getPlanBeginDate()));
        }

        if (null == vo.getPlanEndDate()) {
            vo.setPlanEndDateStr("");
        } else {
            vo.setPlanEndDateStr(JDateUtils.format(vo.getPlanEndDate()));
        }

        return vo;
    }

    /**
     * 编辑部门上报项目
     *
     * @param departProjectAddInVO
     */
    @Override
    public void editDepartProject(DepartProjectAddInVO departProjectAddInVO) {
        DepartProject departProject = internalDepartProjectServiceImpl.getById(departProjectAddInVO.getId());

        Copy.simpleCopyExcludeNull(departProjectAddInVO, departProject);

        //总投资
        if (JStringUtils.isNotNullOrEmpty(departProjectAddInVO.getTotalInvestmentStr())) {
            departProject.setTotalInvestment(Double.valueOf(departProjectAddInVO.getTotalInvestmentStr()).doubleValue());
        } else {
            departProject.setTotalInvestment(0.00);
        }

        //计划开工时间
        if (JStringUtils.isNotNullOrEmpty(departProjectAddInVO.getPlanBeginDateStr())) {
            departProject.setPlanBeginDate(JDateUtils.parseDate(departProjectAddInVO.getPlanBeginDateStr()));
        }
        //计划竣工时间
        if (JStringUtils.isNotNullOrEmpty(departProjectAddInVO.getPlanEndDateStr())) {
            departProject.setPlanEndDate(JDateUtils.parseDate(departProjectAddInVO.getPlanEndDateStr()));
        }
        internalDepartProjectServiceImpl.updateOnly(departProject);
    }

    /**
     * 删除部门上报项目
     *
     * @param ids
     */
    @Override
    public void deleteDepartProject(String[] ids) {
        for (String id : ids) {
            deleteDepartProjectById(id);
        }
    }

    private void deleteDepartProjectById(String id) {
        if (StringUtils.isNotBlank(id)) {
            DepartProject departProject = internalDepartProjectServiceImpl.getById(id);
            internalDepartProjectServiceImpl.delete(departProject);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,departProject.getProjectName(),Constants.projectModule.DEPARTPROJECT);

        }
    }
}
