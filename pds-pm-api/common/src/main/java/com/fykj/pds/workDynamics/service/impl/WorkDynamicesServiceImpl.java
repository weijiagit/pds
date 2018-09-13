package com.fykj.pds.workDynamics.service.impl;

import com.fykj._b._core.Constants;
import com.fykj._b._core.attachment.model.Attachment;
import com.fykj._b._core.attachment.service.AttachmentService;
import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj._b._core.attachment.vo.AttachmentRecordVO;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.*;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.task.vo.TaskPageOutVO;
import com.fykj.pds.workDynamics.model.WorkDynamics;
import com.fykj.pds.workDynamics.service.WorkDynamicesService;
import com.fykj.pds.workDynamics.vo.WorkDynamicesAddInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesEditInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 13:03
 * Description:
 **/

@Service
@Transactional
public class WorkDynamicesServiceImpl extends ServiceSupport implements WorkDynamicesService {

    private SingleEntityManager<WorkDynamics> internalWorkDynamicsServiceImpl = SingleEntityManagerGetter.get()
            .getInstance(WorkDynamics.class);

    private SingleEntityManager<Attachment> attachmentEntityManager = SingleEntityManagerGetter.get()
            .getInstance(Attachment.class);

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DepartmentSerive departmentSerive;

    @Autowired
    private LoginMessageService loginMessageService;

    /**
     * 后台工作动态查询
     *
     * @param vo
     * @param page
     * @return
     */
    @Override
    public JPage<WorkDynamicesPageOutVO> getWorkDynamicesPage(WorkDynamicesPageInVO vo, SimplePageRequest page) {
        StringBuilder sql = publicSQL();
        Map<String, Object> params = new WeakHashMap<String, Object>();

        // 部门
        if (StringUtils.isNotBlank(vo.getDepartmentId())) {
            sql.append(" and t.department_name = :departmentName ");
            params.put("departmentName", vo.getDepartmentId());
        }


        // 内容
        if (StringUtils.isNotBlank(vo.getContent())) {
            sql.append(" and t.content like :content ");
            params.put("content", "%" + vo.getContent() + "%");
        }

        sql.append(" order by  t.create_date desc ");


        params.put("isAvailable", Availability.available.ordinal());
        JPage<WorkDynamicesPageOutVO> pages = nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, WorkDynamicesPageOutVO.class);

        List<WorkDynamicesPageOutVO> outVOS = getWorkDynamicesPageOutVOS(pages);
        JPageUtil.replaceConent(pages,outVOS);

        return pages;
    }

    /**
     * 新增工作动态
     *
     * @param pageAddWorkDynamice
     * @return
     */
    @Override
    public WorkDynamics addWorkDynamices(WorkDynamicesAddInVO pageAddWorkDynamice) {

        WorkDynamics workDynamics = new WorkDynamics();
        workDynamics.setContent(pageAddWorkDynamice.getContent());
        if (StringUtils.isNotEmpty(pageAddWorkDynamice.getImageId())) {
            // 图片名称、地址
            AttachmentRecordVO attachmentRecordVO = attachmentService.getAttachment(pageAddWorkDynamice.getImageId());
            if (attachmentRecordVO != null) {
                workDynamics.setImgName(attachmentRecordVO.getName() + "." + attachmentRecordVO.getSuffix());
                workDynamics.setImgUrl(attachmentRecordVO.getUri());
            }
        }
        workDynamics.setDepartmentName(pageAddWorkDynamice.getDepartmentId());
        internalWorkDynamicsServiceImpl.saveOnly(workDynamics);
        // 修改图片
        attachmentService.uploadFiles(pageAddWorkDynamice.getImageId(), workDynamics.getId());

        return workDynamics;
    }

    /**
     * 编辑工作动态
     *
     * @param pageWorkDynamice
     */
    @Override
    public void editWorkDynamices(WorkDynamicesEditInVO pageWorkDynamice) {
        WorkDynamics workDynamics = internalWorkDynamicsServiceImpl.getById(pageWorkDynamice.getId());
        // 内容
        workDynamics.setContent(pageWorkDynamice.getContent());
        workDynamics.setDepartmentName(pageWorkDynamice.getDepartmentId());

        if (StringUtils.isNotBlank(pageWorkDynamice.getImageId())) {
            AttachmentRecordVO attachmentRecordVO = attachmentService.getAttachment(pageWorkDynamice.getImageId());
            if (attachmentRecordVO != null) {
                workDynamics.setImgName(attachmentRecordVO.getName() + "." + attachmentRecordVO.getSuffix());
                workDynamics.setImgUrl(attachmentRecordVO.getUri());
            }
        }
        internalWorkDynamicsServiceImpl.saveOnly(workDynamics);
        // 修改附件之前，先删除之前的附件
        Optional.ofNullable(pageWorkDynamice.getImageId()).ifPresent(attachmentId -> {
            for (String id : attachmentId.split(",")) {
                if (JStringUtils.isNotNullOrEmpty(id)) {
                    AttachmentRecordVO attachmentRecordVO = attachmentService.getAttachment(id);
                    if (attachmentRecordVO != null && StringUtils.isNotEmpty(attachmentRecordVO.getFkId())) {
                        // 不为空，则说明附件没有做修改
                    } else {
                        attachmentService.deleteAttachmentByFkId(workDynamics.getId());
                        Attachment attachment = attachmentEntityManager.getById(id);
                        if (attachment != null) {
                            attachment.setFkId(workDynamics.getId());
                            attachmentEntityManager.updateOnly(attachment);
                        }
                    }

                }
            }
        });
    }

    /**
     * 删除工作动态
     *
     * @param ids
     */
    @Override
    public void deleteWorkDynamices(String[] ids) {
        for (String id : ids) {
            deleteWorkDynamicesById(id);
        }
    }

    private void deleteWorkDynamicesById(String id) {
        if (StringUtils.isNotBlank(id)) {
            WorkDynamics workDynamics = internalWorkDynamicsServiceImpl.getById(id);
            internalWorkDynamicsServiceImpl.delete(workDynamics);
            //记录删除日志
            SessionUser sessionUser = ServerSessionHolder.getSessionUser();
            loginMessageService.saveLoginMessage(Constants.logType.DEL_TYPE,sessionUser,workDynamics.getContent(),Constants.projectModule.WORKDYNAMICE);

        }
    }

    /**
     * 查看工作动态
     *
     * @param id
     * @return
     */
    @Override
    public WorkDynamicesPageOutVO getWorkDynamicesById(String id) {
        WorkDynamics workDynamics = internalWorkDynamicsServiceImpl.getById(id);
        WorkDynamicesPageOutVO vo = new WorkDynamicesPageOutVO();
        Copy.simpleCopyExcludeNull(workDynamics, vo);
        List<AttachmentInfo> attachmentInfoLists = attachmentService.getAttachmentList(id);
        vo.setDepartmentId(workDynamics.getDepartmentName());
        Department department = departmentSerive.getDepartmentById(vo.getDepartmentId());
        if(department != null){
            vo.setDepartmentName(department.getName());
        }else{
            vo.setDepartmentName("");
        }
        vo.setAttachmentInfoList(attachmentInfoLists);
        return vo;
    }

    /**
     * 工作动态 首页显示前五条
     *
     * @param page
     * @return
     */
    @Override
    public JPage<WorkDynamicesPageOutVO> selectWorkDynamicesForFront(SimplePageRequest page) {
        StringBuilder sql = publicSQL();
        Map<String, Object> params = new WeakHashMap<String, Object>();
        sql.append(" order by  t.create_date desc ");

        page.setPageSize(3);
        params.put("isAvailable", Availability.available.ordinal());

        JPage<WorkDynamicesPageOutVO> pages = nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, WorkDynamicesPageOutVO.class);

        List<WorkDynamicesPageOutVO> outVOS = getWorkDynamicesPageOutVOS(pages);
        JPageUtil.replaceConent(pages,outVOS);

        return pages;
    }

    private List<WorkDynamicesPageOutVO> getWorkDynamicesPageOutVOS(JPage<WorkDynamicesPageOutVO> pages) {
        List<WorkDynamicesPageOutVO> list = pages.getContent();
        List<WorkDynamicesPageOutVO> outVOS =new ArrayList<>();
        for(WorkDynamicesPageOutVO workDynamicesPageOutVO :list){
            WorkDynamicesPageOutVO out = Copy.simpleCopy(workDynamicesPageOutVO,WorkDynamicesPageOutVO.class);
            if(StringUtils.isNotBlank(out.getDepartmentName())){
                Department department = departmentSerive.getDepartmentById(workDynamicesPageOutVO.getDepartmentName());
                if(department != null){
                    out.setDepartmentName(department.getName());
                }else{
                    out.setDepartmentName(null);
                }
            }
            outVOS.add(out);
        }
        return outVOS;
    }


    private StringBuilder publicSQL() {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(" t.id as id, t.department_name as departmentName, " +
                "t.content as content, DATE_FORMAT(t.create_date,'%Y-%m-%d') as createDateFormat," +
                " t.img_name as imgName, t.img_url as imgUrl");
        sql.append("  from t_work_dynamics t ");
        sql.append(" where t.is_available = :isAvailable ");
        return sql;
    }
}
