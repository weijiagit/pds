
package com.fykj.pds.project.vo;

import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj.kernel._c.model.JOutputModel;

import java.util.List;

public class AttractProjectRecordVO extends AttractProjectInfo implements JOutputModel {

	/**
	 * 序号
	 */
	private int sequence;

	/**
	 * 创建时间字符串
	 */
	private String createDateStr;

	//附件列表
	private List<AttachmentInfo> attachmentInfoList;

	public List<AttachmentInfo> getAttachmentInfoList() {
		return attachmentInfoList;
	}

	public void setAttachmentInfoList(List<AttachmentInfo> attachmentInfoList) {
		this.attachmentInfoList = attachmentInfoList;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
}
