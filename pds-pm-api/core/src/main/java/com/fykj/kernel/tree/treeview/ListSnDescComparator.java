package com.fykj.kernel.tree.treeview;

import java.util.Comparator;

import com.fykj.kernel.tree.model.JTreeNode;

public class ListSnDescComparator implements Comparator<JTreeNode> {

	@Override
	public int compare(JTreeNode o1, JTreeNode o2) {
	int n1 = null==o1.getSequence()?0:o1.getSequence();
	int n2 = null==o2.getSequence()?0:o2.getSequence();
		
		return n1>n2?1:(n1 == n2 ? 0 : -1);
	}


}
