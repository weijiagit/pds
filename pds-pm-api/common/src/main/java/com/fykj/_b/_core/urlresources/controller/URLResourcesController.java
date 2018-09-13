/**
 * 
 */
package com.fykj._b._core.urlresources.controller;

import com.fykj._b._core.urlresources.model.URLResources;
import com.fykj._b._core.urlresources.service.URLResourcesService;
import com.fykj._b._core.urlresources.vo.URLResourcesEidtInVO;
import com.fykj._b._core.urlresources.vo.URLResourcesPageInVO;
import com.fykj._b._core.urlresources.vo.URLResourcesSaveInVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.util.Copy;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * @author zhengzw
 *
 */
@Controller
@RequestMapping("/urlresources")
public class URLResourcesController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private URLResourcesService urlResourcesService;


	/**
	 * 获取资源列表
	 * 
	 * @param vo
	 * @param pageVo
	 * @return
	 */
	@RequestMapping(path="/getUrlResourcesPage",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getUrlResourcesPage(URLResourcesPageInVO vo, SimplePageRequestVO pageVo) {
		JPage<URLResources> page = urlResourcesService.getUrlResourcesPage(vo,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	/**
	 * 根据ID获取资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getUrlResourcesById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getUrlResourcesById(String id) {
		URLResources res = urlResourcesService.getUrlResourcesById(id);
		return InvokeResult.success(res);
	}

	/**
	 * 保存资源
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/saveUrlResources",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveUrlResources(URLResourcesSaveInVO vo) {
		URLResources res = urlResourcesService.saveUrlResources(Copy.simpleCopy(vo, URLResources.class));
		return InvokeResult.success(res);
	}

	/**
	 * 编辑资源
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/editUrlResources",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editUrlResources(URLResourcesEidtInVO vo) {
		urlResourcesService.editUrlResources(Copy.simpleCopy(vo, URLResources.class));
		return InvokeResult.success(vo.getId());
	}

	/**
	 * 删除资源
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/deleteUrlResources",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteUrlResources(String ids) {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("获取资源信息失败!");
		}
		String[] arr = ids.split(",");
		urlResourcesService.deleteUrlResources(arr);
		return InvokeResult.success(true);
	}
	
}
