package com.fykj.pds.notice.controller;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel.controller.baseController;
import com.fykj.pds.notice.model.Notice;
import com.fykj.pds.notice.service.NoticeService;
import com.fykj.pds.notice.vo.NoticeAddInVO;
import com.fykj.pds.notice.vo.NoticeEditInVO;
import com.fykj.pds.notice.vo.NoticePageInVO;
import com.fykj.pds.notice.vo.NoticePageOutVO;
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
 * Date: 2017/11/8
 * Time: 10:47
 * Description:
 **/
@Controller
@RequestMapping("/notice")
public class NoticeController extends baseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private NoticeService noticeService;

    /**
     * 保存通知公告
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/saveNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveNotice(NoticeAddInVO vo) {
        Notice notice = noticeService.addNotice(vo);
        return InvokeResult.success(notice);
    }

    /**
     * 获取通知公告列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getNoticePage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getNoticePage(NoticePageInVO vo, SimplePageRequestVO pageVo) {
        JPage<NoticePageOutVO> page = noticeService.getNoticePage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 编辑通知公告
     *
     * @param vo
     * @return
     */
    @RequestMapping(path = "/editNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult editNotice(NoticeEditInVO vo) {
        noticeService.editNotice(vo);
        return InvokeResult.success(vo.getId());
    }


    /**
     * 发布通知公告
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/publishNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult publishNotice(String id) {
        noticeService.publishNotice(id);
        return InvokeResult.success(true);
    }

    /**
     * 通知公告置顶
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/isTopNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult isTopNotice(String id) {
        noticeService.topNotice(id);
        return InvokeResult.success(true);
    }

    /**
     * 通知公告取消置顶
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/cancelTopNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult cancelTopNotice(String id) {
        noticeService.cancelTopNotice(id);
        return InvokeResult.success(true);
    }


    /**
     * 删除通知公告
     *
     * @param ids
     * @return
     */
    @RequestMapping(path = "/deleteNotice", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteNotice(String ids) {
        if (!StringUtils.isNotBlank(ids)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        String[] arr = ids.split(",");

        noticeService.deleteNoticeById(arr);
        return InvokeResult.success(true);

    }

    /**
     * 通知公告详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/detailNotice", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult detailNotice(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        NoticePageOutVO noticePageOutVO = noticeService.getNoticeById(id);
        return InvokeResult.success(noticePageOutVO);
    }


    /**
     * 通知公告 首页显示前五条
     *
     * @return
     */
    @RequestMapping(path = "/queryNoticeFront", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult queryNoticeFront(SimplePageRequestVO pageVo) {

        JPage<NoticePageOutVO> page = noticeService.selectNoticeForFront(
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));

        return InvokeResult.success(page);
    }


}
