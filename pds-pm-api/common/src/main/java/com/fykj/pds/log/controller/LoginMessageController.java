package com.fykj.pds.log.controller;

import com.fykj.kernel._c.model.InvokeResult;

import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.departProject.vo.DepartProjectPageOutVO;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.log.vo.LoginMessagePageInVO;
import com.fykj.pds.log.vo.LoginMessagePageOutVO;
import com.fykj.web.model.SimplePageRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: songzhonglin
 * Date: 2018/1/4
 * Time: 9:13
 * Description:
 **/
@Controller
@RequestMapping("/loginMessage")
public class LoginMessageController {

    @Autowired
    private LoginMessageService loginMessageService;

    /**
     * 获取登录log日志列表
     *
     * @param vo
     * @param pageVo
     * @return
     */
    @RequestMapping(path = "/getLoginMessagePage", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getLoginMessagePage(LoginMessagePageInVO vo, SimplePageRequestVO pageVo) {
        JPage<LoginMessagePageOutVO> page = loginMessageService.getLoginMessagePage(vo,
                new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
        return InvokeResult.success(page);
    }

    /**
     * 部门日志详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/getLoginMessageById", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getLoginMessageById(String id) {
        if (!StringUtils.isNotBlank(id)) {
            return InvokeResult.bys("未获取页面元素信息");
        }
        LoginMessagePageOutVO loginMessagePageOutVO = loginMessageService.getLoginMessageById(id);
        return InvokeResult.success(loginMessagePageOutVO);
    }
}
