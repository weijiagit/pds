package com.fykj._b._core.departGovernment.controller;

import com.fykj._b._core.departGovernment.model.DepartGovernment;
import com.fykj._b._core.departGovernment.service.DepartGovernmentService;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentAddInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentEditInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageInVO;
import com.fykj._b._core.departGovernment.vo.DepartGovernmentPageOutVO;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.util.Copy;
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
 * Created by weijia on 2017/12/21.
 *	行政审批管理
 */
@Controller
@RequestMapping("/departgovernment")
public class DepartGovernmentController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private DepartGovernmentService departGovernmentService;

	/**
	 * 查询部门行政审批列表
	 *
	 * @param vo
	 * @param pageVo
	 * @return
	 */
	@RequestMapping(path="/getDepartGovernmentPage",method= RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDepartGovernmentPage(DepartGovernmentPageInVO vo, SimplePageRequestVO pageVo) {
		JPage<DepartGovernmentPageOutVO> page = departGovernmentService.getDepartGovernmentPage(vo,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	/**
	 * 保存部门行政审批
	 *
	 * @param departGovernmentAddInVO
	 * @return
	 */
	@RequestMapping(path="/saveDepartGovernment",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveDepartGovernment(DepartGovernmentAddInVO departGovernmentAddInVO) {
		DepartGovernment departGovernment = Copy.simpleCopy(departGovernmentAddInVO, DepartGovernment.class);
		departGovernmentService.saveDepartGovernment(departGovernment);
		return InvokeResult.success(true);
	}

	/**
	 * 逻辑删除部门行政审批
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/removeDepartGovernment",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult removeDepartGovernment(String id) {
		if (StringUtils.isBlank(id)) {
			return InvokeResult.bys("未获取行政审批信息");
		}
		String[] ids = id.split(",");
		departGovernmentService.removeSysUser(ids);
		return InvokeResult.success(true);
	}

	/**
	 * 根据id获取部门审批信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getDepartGovernmentById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDepartGovernmentById(String id) {
		DepartGovernmentPageOutVO departGovernmentPageOutVO = departGovernmentService.getDepartGovernmentById(id);
		return InvokeResult.success(departGovernmentPageOutVO);
	}

	/**
	 * 编辑部门审批信息初始化页面
	 */
	@RequestMapping(path="/toDepartGovernmentEdit",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult toDepartGovernmentEdit(String id) {
		DepartGovernmentPageOutVO departGovernmentPageOutVO = departGovernmentService.getDepartGovernmentById(id);
		return InvokeResult.success(departGovernmentPageOutVO) ;
	}

	/**
	 * 编辑部门审批信息
	 *
	 * @param InVO
	 * @return
	 */
	@RequestMapping(path="/editDepartGovernment",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editDepartGovernment(DepartGovernmentEditInVO InVO) {
		departGovernmentService.editDepartGovernment(InVO);
		return InvokeResult.success(true);
	}
}
