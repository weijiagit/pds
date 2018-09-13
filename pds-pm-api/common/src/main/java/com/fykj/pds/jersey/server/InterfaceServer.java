package com.fykj.pds.jersey.server;

import com.fykj.pds.project.ProjectCodesTable;
import com.fykj.pds.project.service.ProjectService;
import com.fykj.pds.project.vo.AttractProjectRecordVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class InterfaceServer {

	@Autowired
	private ProjectService projectService;

	/*
	 * 测试
	 */
	@Path("project/add")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public String addProject(){
		return "true";
	}


	/*
 	 * 测试get
 	 * http://localhost/api/jk/project/getCity
 	 */
	@Path("project/getCity")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public City city(){
		City city = new City();
		city.setId("1");
		city.setName("suzhou");
		return city;
	}


	/*
 	 * 测试Post
 	 * http://localhost/api/jk/project/insertAttract
 	 */
	@Path("project/insertAttract")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public City insertAttract(@FormParam("id") String id, @FormParam("name") String name){
		City city = new City();
		city.setName(name);
		city.setId(id);
		System.out.println(city);
		return city;
	}

	/*
	 * 测试Post
	 * http://localhost/api/jk/project/insertAttractObject
	 */
	@Path("project/insertAttractObject")
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes("application/x-www-form-urlencoded")
	public City insertAttractObject(@BeanParam City city){
		System.out.println(city);
		return city;
	}

	/*
	 * 从招商系统获得招商前期项目信息
	 * http://localhost/api/jk/project/insertAttractJson
{
	"protocolScanAttachmentList": [{"name":"test1.doc","path":"path1"},{"name":"test2.pdf","path":"path2"}],
    "projectName": "拟项目名称",
    "projectDescribe": "项目简介",
    "investSurvey":"投资概况",
    "landAcquisition":"征地",
    "floorSpace":"13.21",
    "totalInvestment":"99.91",
    "investmentCompany":"投资单位",
    "companyName":"公司全称",
    "siteLocation":"选址位置",
    "chargeName":"项目负责人姓名",
    "chargePhone":"项目负责人电话",
    "contactName":"项目联系人姓名",
    "contactPhone":"项目联系人电话",
    "specialNumber":"79993770-3499-463d-b83c-5c8f0d1aa417"
}
	 *
	 *
	 *
	 *
 	 */
	@Path("project/insertAttractJson")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insertAttractJson(AttractProjectRecordVO attractProjectRecordVO){
		JSONObject json = new  JSONObject();
		try{
			String flag = projectService.insertAttractJson(attractProjectRecordVO);
			if(flag.equals(ProjectCodesTable.GetAttractProjectFlag.YES)){//录入成功
				json.put("code", ProjectCodesTable.GetAttractProjectFlag.YES);
				json.put("message", "录入成功！");
			}else{//
				json.put("code", ProjectCodesTable.GetAttractProjectFlag.DUPLICATE);
				json.put("message", "录入失败，已录入系统！");
			}
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", ProjectCodesTable.GetAttractProjectFlag.NO);
			json.put("message", "录入失败，系统错误！");
		}
		return json.toJSONString();
	}



}
