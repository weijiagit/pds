/**
 * 
 */
package com.fykj._b._core.dictionary.controller;

import java.util.List;

import com.fykj._b._core.cache.DictionaryCacheHelper;
import com.fykj._b._core.dictionary.model.Dictionary;
import com.fykj._b._core.dictionary.model.DictionaryData;
import com.fykj._b._core.dictionary.service.DictionaryService;
import com.fykj._b._core.dictionary.vo.DictDataAddInVO;
import com.fykj._b._core.dictionary.vo.DictDataOutVO;
import com.fykj._b._core.dictionary.vo.DictionaryAddInVO;
import com.fykj._b._core.dictionary.vo.DictionaryEditInVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.dictionary.vo.DictDataEditInVO;
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
@RequestMapping("/dictionary")
public class DictionaryController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(path="/getDictionaryByName",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDictionaryByName(String name, SimplePageRequestVO pageVo) {
		JPage<Dictionary> page = dictionaryService.getDictionaryByName(name,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	@RequestMapping(path="/getAllDictionarys",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getAllDictionarys() {
		List<Dictionary> list = dictionaryService.getAllDictionarys();
		return InvokeResult.success(list);
	}

	@RequestMapping(path="/saveDictionary",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveDictionary(DictionaryAddInVO vo) {
		Dictionary dict = dictionaryService.saveDictionary(Copy.simpleCopy(vo, Dictionary.class));
		return InvokeResult.success(dict);
	}

	@RequestMapping(path="/getDictionaryById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDictionaryById(String id) {
		Dictionary dict = dictionaryService.getDictionaryById(id);
		return InvokeResult.success(dict);
	}

	@RequestMapping(path="/editDictionary",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editDictionary(DictionaryEditInVO vo) {
		dictionaryService.editDictionary(Copy.simpleCopy(vo, Dictionary.class));
		return InvokeResult.success(vo.getId());
	}

	@RequestMapping(path="/deleteDictionaryById",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteDictionaryById(String ids) {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("未获取字典类型信息");
		}
		String[] arr = ids.split(",");
		dictionaryService.deleteDictionarys(arr);
		return InvokeResult.success(true);
		
	}

	@RequestMapping(path="/getDictionaryDataPage",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDictionaryDataPage(String code, SimplePageRequestVO pageVo) {
		JPage<DictDataOutVO> page = dictionaryService.getDictionaryDataPage(code,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	@RequestMapping(path="/saveDictionaryData",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveDictionaryData(DictDataAddInVO vo) {
		dictionaryService.saveDictionaryData(Copy.simpleCopy(vo, DictionaryData.class));
		return InvokeResult.success(true);
	}

	@RequestMapping(path="/editDictionaryData",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editDictionaryData(DictDataEditInVO vo) {
		dictionaryService.editDictionaryData(Copy.simpleCopy(vo, DictionaryData.class));
		return InvokeResult.success(true);
	}

	@RequestMapping(path="/getDictionaryDataById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDictionaryDataById(String id) {
		List<DictDataOutVO> list = dictionaryService.getDictionaryDataById(id);
		return InvokeResult.success(list.get(0));
	}

	@RequestMapping(path="/deleteDictionaryData",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteDictionaryData(String ids) {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("未获取字典明细信息");
		}
		String[] arr = ids.split(",");
		dictionaryService.deleteDictionaryDatas(arr);
		return InvokeResult.success(true);
	}

	/**
	 * 重新加载缓存
	 * 
	 * @return
	 */
	@RequestMapping(path="/loadCache",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult loadCache() {
		DictionaryCacheHelper.load();
		return InvokeResult.success(true);
	}
	
	/**
	 * 根据code获取字典信息
	 * @param code
	 * @return
	 */
	@RequestMapping(path="/getDictionaryByCode",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getDictionaryByCode(String code) {
		return InvokeResult.success(DictionaryCacheHelper.getDictData(code));
	}
	
}
