/**
 * 
 */
package com.fykj._b._core.element.controller;

import com.fykj._b._core.element.model.PageElement;
import com.fykj._b._core.element.service.PageElementService;
import com.fykj._b._core.element.vo.ElementAddInVO;
import com.fykj._b._core.element.vo.ElementEditInVO;
import com.fykj._b._core.element.vo.ElementPageInVO;
import com.fykj._b._core.element.vo.ElementPageOutVO;
import com.fykj._b._core.element.vo.SysUserElementOutVO;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel._c.ServerSessionHolder;
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
@RequestMapping("/element")
public class PageElementController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private PageElementService elementService;

	
	/**
	 * 保存元素
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/saveElement",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveElement(ElementAddInVO vo) {
		PageElement pageElement = elementService.saveElement(Copy.simpleCopy(vo, PageElement.class));
		return InvokeResult.success(pageElement);
	}

	/**
	 * 获取元素列表
	 * 
	 * @param vo
	 * @param pageVo
	 * @return
	 */
	@RequestMapping(path="/getElementPage",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getElementPage(ElementPageInVO vo, SimplePageRequestVO pageVo) {
		JPage<ElementPageOutVO> page = elementService.getElementPage(vo,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	/**
	 * 根据ID获取元素
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getElementById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getElementById(String id) {
		ElementPageOutVO pageElement = elementService.getElementById(id);
		return InvokeResult.success(pageElement);
	}

	/**
	 * 编辑元素
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/editElement",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editElement(ElementEditInVO vo) {
		elementService.editElement(Copy.simpleCopy(vo, PageElement.class));
		return InvokeResult.success(vo.getId());
	}

	/**
	 * 删除元素
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/deleteElement",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteElement(String ids) {
		if (!StringUtils.isNotBlank(ids)) {
			return InvokeResult.bys("未获取页面元素信息");
		}
		String[] arr = ids.split(",");
		elementService.deleteElement(arr);
		return InvokeResult.success(true);
		
	}
	
	/**
	 * 根据元素标识获取元素
	 * @param funcId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/getElementByFuncId")
	public InvokeResult getElementByFuncId(String funcId){
		PageElement element = elementService.getElementByFuncId(funcId);
		return InvokeResult.success(element);
	}
	
	/**
	 *  根据用户获取元素
	 *  @return_type:InvokeResult
	 *  @author fxl  
	 *  @return
	 */
	@ResponseBody
	@RequestMapping(path = "/getElementByUser")
	public InvokeResult getElementByUser(){
		String userId = ServerSessionHolder.getSessionUser().getId();
		List<SysUserElementOutVO> elementList=elementService.getElementByUser(userId);
		return InvokeResult.success(elementList);
	}
	
}
