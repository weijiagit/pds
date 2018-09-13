package com.fykj._b._core.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj._b._core.attachment.service.AttachmentService;
import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj._b._core.attachment.vo.AttachmentRecordVO;
import com.fykj.kernel.file.FileResponseListener;
import com.fykj.kernel.file.FileTransfer.FileResponse;

@Component
public class _FileResponse2DatabaseListener implements FileResponseListener<AttachmentRecordVO> {

	@Autowired
	private AttachmentService attachmentService;
	
	@Override
	public AttachmentRecordVO on(FileResponse fileResponse) throws Exception {
		AttachmentInfo attachmentInfo=new AttachmentInfo();
		attachmentInfo.setPath(fileResponse.getUri());
		String fileName=fileResponse.getName();
		int p=-1;
		if((p=fileName.lastIndexOf("."))!=-1){
			attachmentInfo.setName(fileName.substring(0, p));
			attachmentInfo.setSuffix(fileName.substring(p+1));
		}else{
			attachmentInfo.setName(fileName);
		}
		String id=attachmentService.saveAttachment(attachmentInfo);
		return attachmentService.getAttachment(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
