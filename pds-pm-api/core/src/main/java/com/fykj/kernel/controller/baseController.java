package com.fykj.kernel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: songzhonglin
 * Date: 2017/12/8
 * Time: 15:21
 * Description:
 **/
@Controller
@RequestMapping("/base")
public class baseController {
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        /**
         * 防止XSS、SQL攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false,true));
    }
}
