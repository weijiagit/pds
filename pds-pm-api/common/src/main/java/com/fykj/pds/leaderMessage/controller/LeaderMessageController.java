package com.fykj.pds.leaderMessage.controller;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.pds.leaderMessage.constant.LeaderMessageConstant;
import com.fykj.pds.leaderMessage.model.LeaderMessage;
import com.fykj.pds.leaderMessage.service.LeaderMessageService;
import com.fykj.pds.leaderMessage.vo.LeaderMessageAddInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessageEditInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageInVO;
import com.fykj.pds.leaderMessage.vo.LeaderMessagePageOutVO;
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
 * Date: 2017/11/13
 * Time: 15:11
 * Description:
 **/
@Controller
@RequestMapping("/leaderMessage")
public class LeaderMessageController extends baseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private LeaderMessageService leaderMessageService;

    /**
     * 保存领导留言
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveLeaderMessage", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveLeaderMessage(LeaderMessageAddInVO vo) {
        LeaderMessage leaderMessage = leaderMessageService.addLeaderMessage(vo);
        return InvokeResult.success(leaderMessage);
    }

    /**
     * 获取领导留言列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getLeaderMessagePage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getLeaderMessagePage(LeaderMessagePageInVO vo, SimplePageRequestVO pageVo) {
        JPage<LeaderMessagePageOutVO> page = leaderMessageService.getLeaderMessagePage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 编辑领导留言
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/editLeaderMessage", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editLeaderMessage(LeaderMessageEditInVO vo) {
        leaderMessageService.editLeaderMessage(vo);
        return InvokeResult.success(vo.getId());
    }

    /**
     * 删除领导留言
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteLeaderMessage", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteLeaderMessage(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        leaderMessageService.deleteLeaderMessageById(arr);

        return InvokeResult.success(true);

    }

    /**
     * 删除领导留言
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteLeaderMessageForFront", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteLeaderMessageForFront(SimplePageRequestVO pageVo,String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        leaderMessageService.deleteLeaderMessageById(arr);

        JPage<LeaderMessagePageOutVO> page = leaderMessageService.selectLeaderMessageForFront(
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);

    }

    /**
     * 领导留言详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailLeaderMessage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailLeaderMessage(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        LeaderMessagePageOutVO leaderMessagePageOutVO = leaderMessageService.getLeaderMessageById(id);
        return InvokeResult.success(leaderMessagePageOutVO);
    }


    /**
     * 领导留言 首页显示前三条
     *
     * @return
     */
    @RequestMapping(path = "/queryLeaderMessageFront", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult queryLeaderMessageFront(SimplePageRequestVO pageVo) {

        JPage<LeaderMessagePageOutVO> page = leaderMessageService.selectLeaderMessageForFront(
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));

        return InvokeResult.success(page);
    }

    /**
     * 完成情况(未)
     * @param id
     * @return
     */
    @RequestMapping(path = "/updateFinishStateByIdOff", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult updateFinishStateByIdOff(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        leaderMessageService.updateFinishStateById(id, LeaderMessageConstant.LeaderMessageStatus.FINISH_OFF);
        return InvokeResult.success(true);

    }

    /**
     * 完成情况(已)
     * @param id
     * @return
     */
    @RequestMapping(path = "/updateFinishStateByIdOn", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult updateFinishStateByIdOn(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        leaderMessageService.updateFinishStateById(id, LeaderMessageConstant.LeaderMessageStatus.FINISH_ON);
        return InvokeResult.success(true);

    }

}
