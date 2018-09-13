package com.fykj.pds.work.personWork.controller;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.pds.work.personWork.model.PersonWork;
import com.fykj.pds.work.personWork.service.PersonWorkService;
import com.fykj.pds.work.personWork.vo.PersonWorkAddInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageInVO;
import com.fykj.pds.work.personWork.vo.PersonWorkPageOutVO;
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
 * Date: 2017/12/1
 * Time: 15:15
 * Description:
 **/
@Controller
@RequestMapping("/personWork")
public class PersonWorkController extends baseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonWorkService personWorkService;

    /**
     * 保存个人办公任务
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/savePersonWork", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult savePersonWork(PersonWorkAddInVO vo) {
        PersonWork personWork = personWorkService.addPersonWork(vo);
        return InvokeResult.success(personWork);
    }

    /**
     * 获取个人办公任务列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getPersonWorkPage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getPersonWorkPage(PersonWorkPageInVO vo, SimplePageRequestVO pageVo) {
        JPage<PersonWorkPageOutVO> page = personWorkService.getPersonWorkPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 删除个人办公任务
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deletePersonWork", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deletePersonWork(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        personWorkService.deletePersonWorkById(arr);
        return InvokeResult.success(true);
    }

    /**
     * 个人办公任务详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailPersonWork", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailPersonWork(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        PersonWorkPageOutVO outVO = personWorkService.getPersonWorkById(id);
        return InvokeResult.success(outVO);
    }

    /**
     * 个人办公任务状态
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/updateStatusById", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult updateStatusById(String id) {
        personWorkService.updateStatusById(id);
        return InvokeResult.success(true);

    }
}
