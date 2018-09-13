package com.fykj._b._core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj._b._core.dictionary.service.DictionaryService;
import com.fykj._b._core.dictionary.vo.DictionaryCacheVO;
import com.fykj.kernel._c.cache.EhCacheService;

@Component
public class DictionaryCache {
	
	private static final String DICTIONARY_CACHE_NAME = "dict_cache";
	
	@Autowired(required=false)
	private EhCacheService<String,Object> ehCacheService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	public void load(){
		Map<String, List<Map<String, String>>> dictionary = new HashMap<String, List<Map<String, String>>>();
		List<DictionaryCacheVO> list = dictionaryService.loadDictonary();
		if(CollectionUtils.isNotEmpty(list)){
			String code = "";
			List<Map<String, String>> dataList = null;
			Map<String, String> data = null;
			for(DictionaryCacheVO vo : list){
				if(!code.equals(vo.getDictCode())){
					if(!"".equals(code)){
						dictionary.put(code, dataList);
					}
					code = vo.getDictCode();
					dataList = new ArrayList<Map<String, String>>();
				}
				data = new HashMap<String, String>();
				data.put("value", vo.getDictDataValue());
				data.put("name", vo.getDictDataName());
				dataList.add(data);
			}
			dictionary.put(code, dataList);
		}
		ehCacheService.put(DICTIONARY_CACHE_NAME, dictionary);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDictData(String dictCode){
		Object cache = ehCacheService.get(DICTIONARY_CACHE_NAME);
		if(cache != null){
			Map<String, List<Map<String, String>>> dictionary = (Map<String, List<Map<String, String>>>) cache;
			//TODO FOR TEST ONLY  - START 
			if(!dictionary.containsKey(dictCode)){
				load();
				return ((Map<String, List<Map<String, String>>>)ehCacheService.get(DICTIONARY_CACHE_NAME)).get(dictCode);
			}
			// END  
			return dictionary.get(dictCode);
		}
		return null;
	}
	
	public String getDictDataName(String dictCode ,String dictDataValue){
		List<Map<String, String>> list = getDictData(dictCode);
		if(CollectionUtils.isNotEmpty(list)){
			for(Map<String, String> map : list){
				if(map.containsValue(dictDataValue)){
					return map.get("name");
				}
			}
		}
		return null;
	}

}
