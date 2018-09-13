package com.fykj.pds.workDynamics.controller;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.pds.workDynamics.model.WorkDynamics;
import com.fykj.pds.workDynamics.service.WorkDynamicesService;
import com.fykj.pds.workDynamics.vo.WorkDynamicesAddInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesEditInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageInVO;
import com.fykj.pds.workDynamics.vo.WorkDynamicesPageOutVO;
import com.fykj.web.model.SimplePageRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 13:56
 * Description:
 **/
@Controller
@RequestMapping("/workdynamice")
public class WorkDynamicesController extends baseController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private WorkDynamicesService workDynamicesService;

    /**
     * 保存工作动态
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveWorkDynamice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveWorkDynamice(WorkDynamicesAddInVO vo) {
        WorkDynamics workDynamics = workDynamicesService.addWorkDynamices(vo);
        return InvokeResult.success(workDynamics);
    }

    /**
     * 获取工作动态列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getWorkDynamicePage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getWorkDynamicePage(WorkDynamicesPageInVO vo, SimplePageRequestVO pageVo) {
        JPage<WorkDynamicesPageOutVO> page = workDynamicesService.getWorkDynamicesPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 编辑工作动态
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/editWorkDynamice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editWorkDynamice(WorkDynamicesEditInVO vo) {
        workDynamicesService.editWorkDynamices(vo);
        return InvokeResult.success(vo.getId());
    }

    /**
     * 删除工作动态
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteWorkDynamice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteWorkDynamice(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        workDynamicesService.deleteWorkDynamices(arr);
        return InvokeResult.success(true);
    }

    /**
     * 工作动态详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailWorkDynamice", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailWorkDynamice(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        WorkDynamicesPageOutVO workDynamicesPageOutVO = workDynamicesService.getWorkDynamicesById(id);
        return InvokeResult.success(workDynamicesPageOutVO);
    }

    /**
     * 工作动态首页显示前五条
     *
     * @return
     */
    @RequestMapping(path = "/queryWorkDynamiceFront", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult queryWorkDynamiceFront(SimplePageRequestVO pageVo) {

        JPage<WorkDynamicesPageOutVO> page = workDynamicesService.selectWorkDynamicesForFront(
                        new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));

        return InvokeResult.success(page);
    }
}
