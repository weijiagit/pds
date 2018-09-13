package com.fykj._b._core.sysrole;

import com.fykj.CodesTable;

public interface SysRoleCodesTable extends CodesTable {

	// 树节点选择状态
	public interface MenuCheckState {
		String SELECTED = "SELECTED"; // 全选
		String UNDETERMINED = "UNDETERMINED"; // 半选
	}
		
}
