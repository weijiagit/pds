/**
 * 
 */
package com.fykj;

/**
 * @author zhengzw
 *
 */
public interface CodesTable {

	// 标签是否启用
	public interface LabelUseableState {
		String code = "LABEL_USEABLE";
		String enable = "1"; // 启用
		String disable = "0"; // 禁用
	}
	
	// 技术领域是否启用
	public interface TecUseableState {
		String code = "TEC_USEABLE";
		String enable = "1";
		String disable = "0";
	}
	
	// 敏感词是否启用
	public interface SenUseableState {
		String code = "SEN_USEABLE";
		String enable = "1";
		String disable = "0";
	}

	// 性别
	public interface Gender {
		String CODE = "GENDER";
		String MALE = "M";
		String FEMALE = "F";
	}
}
