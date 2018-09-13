package com.fykj._b._core.attachment.service;

import com.fykj._b._core.attachment.model.Attachment;
import com.fykj._b._core.attachment.vo.AttachmentRecordVO;
import com.fykj._b._core.attachment.vo.AttachmentInfo;

import java.util.List;

public interface AttachmentService {

	String saveAttachment(AttachmentInfo attachmentInfo);
	
	AttachmentRecordVO getAttachment(String id);

	void deleteAttachmentById(String id);

	void deleteAttachmentByFkId(String fkId);

	void deleteAttachById(String id);


	List<Attachment> selectAttachmentList(String fkId);

	List<AttachmentInfo> getAttachmentList(String fkId);

	public void uploadFiles(String attachmentIds,String pkId);
	
}
