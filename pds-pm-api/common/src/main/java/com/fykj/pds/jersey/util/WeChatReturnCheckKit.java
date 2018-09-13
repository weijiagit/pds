package com.fykj.pds.jersey.util;

import com.fykj.kernel.BusinessException;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: songzhonglin
 * Date: 2018/4/8
 * Time: 15:54
 * Description: 检查微信返回内容是否正确
 **/
public class WeChatReturnCheckKit {
    private static final Logger logger = LoggerFactory.getLogger(WeChatReturnCheckKit.class);

    public static JSONObject check(JSONObject jsonObject){
        JSONObject result = null;
        try {
            String retStr = jsonObject.getString("result");
            if(jsonObject.getInt("code") != HttpStatus.SC_OK){
                logger.error("请求用户信息出错，http状态码为：["+ jsonObject.getInt("code") +"],请求返回的内容为:["+ retStr +"]");
                throw new BusinessException("无法从腾讯服务器获取数据");
            }
             result = new JSONObject(retStr);
            if(result.has("errcode") && result.getInt("errcode") != 0){
                logger.error("微信返回错误内容为:" + retStr);
                throw new BusinessException("无法从腾讯服务器获取数据");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
