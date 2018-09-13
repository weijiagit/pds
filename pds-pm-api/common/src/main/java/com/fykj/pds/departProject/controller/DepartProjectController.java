package com.fykj.pds.departProject.controller;

import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.pds.departProject.model.DepartProject;
import com.fykj.pds.departProject.service.DepartProjectService;
import com.fykj.pds.departProject.vo.DepartProjectAddInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageInVO;
import com.fykj.pds.departProject.vo.DepartProjectPageOutVO;
import com.fykj.pds.project.vo.ProjectSaveVO;
import com.fykj.pds.task.service.TaskService;
import com.fykj.web.model.SimplePageRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/
@Controller
@RequestMapping("/departproject")
public class DepartProjectController extends baseController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartProjectService departProjectService;

    @Autowired
    private TaskService taskService;
    /**
     * 保存工作动态
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveDepartProject", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveDepartProject(DepartProjectAddInVO vo) {
        DepartProject departProject = departProjectService.saveDepartProject(vo);
        return InvokeResult.success(departProject);
    }

    /**
     * 根据部门Id查找用户
     *
     * @return
     */
    @RequestMapping(path = "/selectUserInfoByDepart", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult selectUserInfoByDepart(String departId) {
        List<SysUserPageOutVO> sysUserPageOutList = taskService.selectUserInfo(departId);
        return InvokeResult.success(sysUserPageOutList);
    }

    /**
     * 获取部门上报项目列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getDepartProjectPage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getDepartProjectPage(DepartProjectPageInVO vo, SimplePageRequestVO pageVo) {
        JPage<DepartProjectPageOutVO> page = departProjectService.getDepartProjectPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 部门上报项目详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getDepartProjectById", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getDepartProjectById(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        DepartProjectPageOutVO departProjectPageOutVO = departProjectService.getDepartProjectById(id);
        return InvokeResult.success(departProjectPageOutVO);
    }

    /**
     * 编辑部门上报项目
     *
     * @param departProjectAddInVO
     * @return
     */
    @RequestMapping(path = "/editDepartProject", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editDepartProject(DepartProjectAddInVO departProjectAddInVO) {
        departProjectService.editDepartProject(departProjectAddInVO);
        return InvokeResult.success(true);
    }

    /**
     * 删除部门上报项目
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteDepartProject", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteDepartProject(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        departProjectService.deleteDepartProject(arr);
        return InvokeResult.success(true);
    }
}
