/**
 * 
 */
package com.fykj._b._core.dictionary.service.impl;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj._b._core.dictionary.model.Dictionary;
import com.fykj._b._core.dictionary.model.DictionaryData;
import com.fykj._b._core.dictionary.service.DictionaryService;
import com.fykj._b._core.dictionary.vo.DictDataOutVO;
import com.fykj._b._core.dictionary.vo.DictionaryCacheVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.kernel.springjpa.query2.JCondition;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class DictionaryServiceImpl extends ServiceSupport implements DictionaryService {

	private SingleEntityManager<Dictionary> internalDictionaryServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(Dictionary.class);

	private SingleEntityManager<DictionaryData> internalDictionaryDataServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(DictionaryData.class);

	@Override
	public JPage<Dictionary> getDictionaryByName(String name, SimplePageRequest page) {
		JCondition jc = internalDictionaryServiceImpl.singleEntityQuery2().conditionDefault();
		if (null != name && !"".equals(name)) {
			jc.likes("name", name);
		}
		return jc.ready().modelPage(page);
	}

	@Override
	public List<Dictionary> getAllDictionarys() {
		List<Dictionary> list = internalDictionaryServiceImpl.singleEntityQuery2().conditionDefault().ready().models();

		return list;
	}

	@Override
	public Dictionary saveDictionary(Dictionary dict) {
		String code = dict.getCode();

		if (dictionaryExists(code)) {
			throw new BusinessException("数据字典代码 [ " + code + " ] 已经存在!");
		}

		internalDictionaryServiceImpl.saveOnly(dict);
		return dict;
	}

	@Override
	public boolean dictionaryExists(String code) {
		Dictionary dict = internalDictionaryServiceImpl.singleEntityQuery2().conditionDefault().equals("code", code)
				.ready().model();

		return dict != null;
	}

	public Dictionary getDictionaryById(String id) {
		return internalDictionaryServiceImpl.getById(id);
	}

	public void editDictionary(Dictionary dict) {
		Dictionary dictionary = internalDictionaryServiceImpl.getById(dict.getId());
		dictionary.setName(dict.getName());
		internalDictionaryServiceImpl.updateOnly(dictionary);
	}

	public void deleteDictionaryById(String id) {
		if (StringUtils.isNotBlank(id)) {
			Dictionary model = internalDictionaryServiceImpl.getById(id);
			internalDictionaryServiceImpl.delete(model);
		}
	}

	public void deleteDictionarys(String[] ids) {
		for (String id : ids) {
			deleteDictionaryById(id);
		}
	}

	public JPage<DictDataOutVO> getDictionaryDataPage(String code, SimplePageRequest page) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(" t1.id as dictId, t1.name as dictName, t1.code as dictCode, ");
		sql.append(" t.id as id, t.name as name, t.value as value, t.sequence ");
		sql.append("  from t_sys_dictionary_data t ");
		sql.append("  left join t_sys_dictionary t1 on t1.code = t.code and t1.is_available = :isAvailable ");
		sql.append(" where t.is_available = :isAvailable");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());

		if (null != code && !"".equals(code)) {
			sql.append(" and t.code = :code");
			params.put("code", code);
		}

		sql.append(" order by t.code, t.sequence");

		return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, DictDataOutVO.class);
	}

	@Override
	public void saveDictionaryData(DictionaryData dictData) {
		String code = dictData.getCode();
		String value = dictData.getValue();

		if (dictDataExists(code, value)) {
			throw new BusinessException("当前字典类别下已经存在键值为[ " + value + " ] 的字典信息!");
		}

		internalDictionaryDataServiceImpl.saveOnly(dictData);
	}

	@Override
	public boolean dictDataExists(String code, String value) {
		DictionaryData dictData = this.internalDictionaryDataServiceImpl.singleEntityQuery2().conditionDefault()
				.equals("code", code).equals("value", value).ready().model();

		return dictData != null;
	}

	@Override
	public DictionaryData editDictionaryData(DictionaryData dictData) {
		DictionaryData data = internalDictionaryDataServiceImpl.getById(dictData.getId());
		data.setName(dictData.getName());
		data.setSequence(dictData.getSequence());

		internalDictionaryDataServiceImpl.updateOnly(data);

		return data;
	}

	@Override
	public List<DictDataOutVO> getDictionaryDataById(String id) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(" t1.id as dictId, t1.name as dictName, t1.code as dictCode, ");
		sql.append(" t.id as id, t.name as name, t.value as value, t.sequence ");
		sql.append("  from t_sys_dictionary_data t ");
		sql.append("  left join t_sys_dictionary t1 on t1.code = t.code and t1.is_available = :isAvailable ");
		sql.append(" where t.is_available = :isAvailable");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());

		if (null != id && !"".equals(id)) {
			sql.append(" and t.id = :id");
			params.put("id", id);
		}

		return nativeQuery().setSql(sql.toString()).setParams(params).models(DictDataOutVO.class);
	}

	@Override
	public void deleteDictionaryDataById(String id) {
		if (StringUtils.isNotBlank(id)) {
			DictionaryData model = internalDictionaryDataServiceImpl.getById(id);
			internalDictionaryDataServiceImpl.delete(model);
		}
	}

	@Override
	public void deleteDictionaryDatas(String[] ids) {
		for (String id : ids) {
			deleteDictionaryDataById(id);
		}
	}

	@Override
	public List<DictionaryCacheVO> loadDictonary() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT d.code as dictCode,d.name as dictName,dd.name as dictDataName,dd.value as dictDataValue");
		sql.append(" from t_sys_dictionary d JOIN t_sys_dictionary_data dd ON d.code = dd.code");
		sql.append(" WHERE d.is_available =:isAvailable AND dd.is_available =:isAvailable ORDER BY d.code,dd.sequence");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		return nativeQuery().setSql(sql.toString()).setParams(params).models(DictionaryCacheVO.class);
	}

	@Override
	public List<DictionaryData> getDictionaryDataByCode(String code) {
		return internalDictionaryDataServiceImpl.singleEntityQuery2().conditionDefault().equals("code", code).ready()
				.models();
	}
}
