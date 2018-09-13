package com.fykj.pds.work.backlogWork.controller;

import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
import com.fykj.pds.work.backlogWork.constant.BacklogWorkContant;
import com.fykj.pds.work.backlogWork.service.BacklogWorkService;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageInVO;
import com.fykj.pds.work.backlogWork.vo.BacklogWorkPageOutVO;
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
 * Date: 2017/12/7
 * Time: 16:08
 * Description:
 **/
@Controller
@RequestMapping("/backlogWork")
public class BacklogWorkController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BacklogWorkService backlogWorkService;

    /**
     * 获取待办工作列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getBacklogWorkPage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getBacklogWorkPage(BacklogWorkPageInVO vo, SimplePageRequestVO pageVo) {
        vo.setState(BacklogWorkContant.BacklogWorkStatus.STATE_OFF);
        // 留言人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        vo.setId(sessionUser.getId());
        JPage<BacklogWorkPageOutVO> page = backlogWorkService.getBacklogWorkPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 获取待办工作列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getHaveDonePage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getHaveDonePage(BacklogWorkPageInVO vo, SimplePageRequestVO pageVo) {
        vo.setState(BacklogWorkContant.BacklogWorkStatus.STATE_ON);
        // 留言人
        SessionUser sessionUser = ServerSessionHolder.getSessionUser();
        vo.setId(sessionUser.getId());
        JPage<BacklogWorkPageOutVO> page = backlogWorkService.getBacklogWorkPage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    @RequestMapping(path = "/updateStateById", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult updateStateById(String id) {
        backlogWorkService.updateStateById(id, BacklogWorkContant.BacklogWorkStatus.STATE_ON);
        return InvokeResult.success(true);
    }

    /**
     * 待办详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailBacklogWork", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailBacklogWork(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        BacklogWorkPageOutVO backlogWorkPageOutVO = backlogWorkService.getPendingWorkById(id);
        return InvokeResult.success(backlogWorkPageOutVO);
    }


}
