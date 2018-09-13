package com.fykj.pds.jersey.util;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.IOException;

/**
 * Author: songzhonglin
 * Date: 2018/4/8
 * Time: 15:57
 * Description: 微信授权api接口
 **/
public class SnsApi {
    /**
     * 请求微信服务器返回code
     *
     * @param code 重定向换来的code
     * @return openId
     */
    public static WebChartUtil  getOpenId(String code) {
        WebChartUtil webChartUtil = new WebChartUtil();
        if (StringUtils.isEmpty(code)) {
            webChartUtil.setCode("0");
            webChartUtil.setMsg("无法从腾讯服务器获取数据");
            return webChartUtil;
        }
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WeChatConfig.APP_ID + "&secret=" + WeChatConfig.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
        try {
            webChartUtil.setOpenId(WeChatReturnCheckKit.check(HttpUtil.get(url)).getString("openid"));
            webChartUtil.setCode("1");
        } catch (IOException e) {
            webChartUtil.setCode("0");
            webChartUtil.setMsg("无法从腾讯服务器获取数据");
        } catch (JSONException e) {
            e.printStackTrace();
            webChartUtil.setCode("0");
            webChartUtil.setMsg("无法从腾讯服务器获取数据");
        }
        return webChartUtil;
    }
}
