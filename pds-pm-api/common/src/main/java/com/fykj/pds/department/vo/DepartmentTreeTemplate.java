/**
 *
 */
package com.fykj.pds.department.vo;

import com.fykj._b._core.tree.JTreeTemplate;

/**
 * songzhonglin
 */
public class DepartmentTreeTemplate implements JTreeTemplate {

    private String remoteId;

    private String remoteParentId;

    private String name;

    private String cls;

    private String layout;

    private Integer sequence;
    
    @Override
    public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	@Override
	public String getRemoteParentId() {
		return remoteParentId;
	}

	public void setRemoteParentId(String remoteParentId) {
		this.remoteParentId = remoteParentId;
	}

    /* (non-Javadoc)
     * @see com.fykj.platform.server.impl.menu.controller.JTreeTemplate#getText()
     */
    @Override
    public String getText() {
        return getName();
    }

    @Override
    public Integer getSequence() {
        return sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPid() {
		// TODO Auto-generated method stub
		return null;
	}
}
