package com.fykj.pds.interfaceManage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.interfaceManage.vo.OAMessageOutVO;
import com.fykj.pds.project.ProjectCodesTable;
import com.fykj.pds.project.model.AttractProject;
import com.fykj.pds.project.vo.AttractProjectRejectOutVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fykj.kernel._Cfg;
import com.fykj.pds.interfaceManage.vo.SsoReturnVo;
import com.fykj.util.http.HttpClientComponent;
import com.fykj.util.http.model.HttpResult;

@Component
public class InterfaceManager {
	
	@Autowired
	private _Cfg cfg;

	private SingleEntityManager<AttractProject> internalAttractProjectServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(AttractProject.class);

	public InterfaceManager() {
	
	}
	
	public SsoReturnVo validateToken(String token) {
		SsoReturnVo result = null ;
		String validTokenUrl=cfg.getSso().getHost()+cfg.getSso().getValidTokenUrl()+token;
		try {
			Map<String, String> headers=new HashMap<String,String>();
			headers.put("content-type", "application/json");
			
			HttpResult httpResult = HttpClientComponent.getInstance().doPost(validTokenUrl,null, headers, 0) ;
			if(httpResult.getData() != null) {
				JSONObject jsonObject = JSONObject.parseObject(httpResult.getData()) ;
				result = convertResult(jsonObject) ;
				return result ;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return result ;
		} 
		return result ;
	}
	
	public OAMessageOutVO sendOAMessage(String sendUser, String message) {
		OAMessageOutVO result = null;
		String url=cfg.getOam().getUrl();
		Map<String, String> params=new HashMap<String, String>();
		params.put("uid", sendUser);
		params.put("strkey", cfg.getOam().getStrkey());
		params.put("biz", message);
		try {
			Map<String, String> headers=new HashMap<String,String>();
			
			HttpResult httpResult = HttpClientComponent.getInstance().doGet(url, params, headers, 0);
			if(httpResult.getData() != null) {
				JSONObject jsonObject = JSONObject.parseObject(httpResult.getData()) ;
				result = convertOAMessageResult(jsonObject) ;
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		return result ;
	}

	private OAMessageOutVO convertOAMessageResult(JSONObject jsonObject) {
		OAMessageOutVO oaMessageOutVO=new OAMessageOutVO();
		oaMessageOutVO.setSuccess(jsonObject.getString("success"));
		oaMessageOutVO.setMsg(jsonObject.getString("msg"));
		return oaMessageOutVO;
	}

	public static void main(String[] args) {
		InterfaceManager in=new InterfaceManager();
		JSONArray array=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("content", "您有新的业务需要办理测试");
		jsonObject.put("remind_user", "test");
		jsonObject.put("biz_system", "项目管理系统");
		jsonObject.put("URL", "");
		array.add(jsonObject);
		
		in.sendOAMessage("admin", array.toString());
	}
	
	
	private SsoReturnVo convertResult(JSONObject jsonObject) {
		SsoReturnVo sso=new SsoReturnVo();
		sso.setCode(jsonObject.getString("code"));
		sso.setMessage(jsonObject.getString("message"));
		if("0".equals(sso.getCode())){
			JSONObject data=jsonObject.getJSONObject("data");
			sso.setFlag(true);
			sso.setUserId(data.getString("userId"));
			sso.setUserName(data.getString("userName"));
			sso.setLoginTime(data.getString("loginTime"));
			sso.setExpireTime(data.getString("expireTime"));
		}else{
			sso.setFlag(false);
		}
		return sso;
	}

	public AttractProjectRejectOutVO attractProjectReject(String id) {

		AttractProject attractProject = internalAttractProjectServiceImpl.getById(id);

		AttractProjectRejectOutVO result = null ;
		String attractProjectRejectUrl=cfg.getApr().getHost()+cfg.getApr().getRejectUrl();
		try {
			Map<String, String> headers=new HashMap<String,String>();
			headers.put("content-type", "application/json");
			//Authorization授权头
			headers.put("Authorization", cfg.getApr().getAuthorization());

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", attractProject.getSpecialNumber());
            jsonParam.put("reason", ProjectCodesTable.RejectAttractProjectReason.REASON);

            HttpResult httpResult = HttpClientComponent.getInstance().doPostJson(attractProjectRejectUrl,jsonParam.toString(), headers, 0) ;
			if(httpResult.getData() != null) {
				JSONObject jsonObject = JSONObject.parseObject(httpResult.getData()) ;
				result = convertAttractProjectResult(jsonObject) ;
				return result ;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return result ;
		}
		return result ;
	}

	private AttractProjectRejectOutVO convertAttractProjectResult(JSONObject jsonObject) {
		AttractProjectRejectOutVO sso=new AttractProjectRejectOutVO();
		sso.setCode(jsonObject.getString("code"));
		sso.setMessage(jsonObject.getString("message"));
		return sso;
	}
}
