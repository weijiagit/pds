/**
 * 
 */
package com.fykj._b._core.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fykj._b._core.sms.model.SmsParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ClassName: SendSmsService
 * <pre>
 * Function: 发送短信Service
 * </pre>
 * @author 张军
 * @Date 2017年5月27日 下午2:56:55
 *
 */

public class SendSmsService {
	protected final static Logger LOGGER= LoggerFactory.getLogger(SendSmsService.class);

	private	static String  smsSendUrl ="http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private	static String longName ="cf_kechuang";
	private	static String passWord = "123456";
       public static String  sendMessage(SmsParams smsParams , SmsContent smsContent) {
               CloseableHttpClient httpClient = HttpClients.createDefault();
               HttpPost request = new HttpPost(smsSendUrl);
               List<NameValuePair> formParams = new ArrayList<NameValuePair>();
               formParams.add(new BasicNameValuePair("account", StringUtils.isNotEmpty(smsParams.getAccount())?smsParams.getAccount():longName));
               formParams.add(new BasicNameValuePair("password", StringUtils.isNotEmpty(smsParams.getPassword())?smsParams.getPassword():passWord));
               formParams.add(new BasicNameValuePair("mobile", smsParams.getMobile()));
               formParams.add(new BasicNameValuePair("content", smsContent.getContent(smsParams.getContent())));
               
               String msg = null;
               try {
                       UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
                       request.setEntity(requestEntity);
                       CloseableHttpResponse response = httpClient.execute(request);
                       try{
                               // 获取响应实体    
                               HttpEntity responseEntity = response.getEntity();
                               if (responseEntity != null) {  
                                       String SubmitResult = EntityUtils.toString(responseEntity);
                                       Document doc = DocumentHelper.parseText(SubmitResult); 
                                       Element root = doc.getRootElement();
                                       msg = root.elementText("msg");  
                               }  
                       }finally{
                               response.close();
                       }
               }catch(Exception e){
                       LOGGER.error("短信发送失败:" + e.getMessage(), e);
                       throw new RuntimeException(e.getMessage());
               }finally{
                        // 关闭连接,释放资源    
           try {  
               httpClient.close();  
           } catch (IOException e) {  
               LOGGER.error("httpclient关闭失败:" + e.getMessage(), e);
                       throw new RuntimeException(e.getMessage()); 
           }  
               }
               return msg;
       }
       
/*       public static void main(String[] args) {
    	   SmsParams sps = new SmsParams();
    	   String[] array = {"12Dx"};
    	   sps.setMobile("18621725101");
    	   sps.setContent(array);
    	   SendSmsService.sendMessage(sps, new SmsDxyzmImpl());
	}
*/
}
