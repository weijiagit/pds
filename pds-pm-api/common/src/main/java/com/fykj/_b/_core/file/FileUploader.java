package com.fykj._b._core.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fykj._b._core.attachment.vo.AttachmentRecordVO;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._fn.Try;
import com.fykj.kernel.file.FileResponseListener;
import com.fykj.kernel.file.FileTransfer;
import com.fykj.kernel.file.FileTransfer.FileRequest;
import com.fykj.kernel.file.FileTransfer.FileResponse;
import com.fykj.kernel.mvc.SpringCallable;
import com.fykj.util.JStringUtils;
import com.fykj.util.JUniqueUtils;

/**
 * 
 * @author J
 *
 */
@Controller
@RequestMapping("/file")
public class FileUploader {
	
	@Autowired
	private FileTransfer fileTransfer;
	
	@Autowired
	private FileResponseListener<AttachmentRecordVO> fileResponseListener;
	
	@ResponseBody
	@RequestMapping(path="/upload",method=RequestMethod.POST)
	public SpringCallable<InvokeResult> upload(@RequestParam(name="name",required=false) String name,
            @RequestParam("file") MultipartFile file) throws Exception {
		
		return new SpringCallable<InvokeResult>() {
			@Override
			public InvokeResult doCall() throws Exception {
				if (!file.isEmpty()) {
					FileRequest fileRequest=new FileRequest();
					fileRequest.set_i_d(JUniqueUtils.unique());
					fileRequest.setName(JStringUtils.isNullOrEmpty(name) ? file.getOriginalFilename(): name);
					fileRequest.setBytes(file.getBytes());
					List<FileResponse> fileResponse=fileTransfer.transfer(Arrays.asList(fileRequest));
					List<AttachmentRecordVO> attachmentRecordVOs=new ArrayList<>();
					fileResponse.forEach(Try.apply(response->{
						AttachmentRecordVO attachmentRecordVO=fileResponseListener.on(response);
						attachmentRecordVOs.add( attachmentRecordVO);
					}));
					return InvokeResult.success(attachmentRecordVOs);
				}
				return InvokeResult.bys("file is missing.");
			}
		};
	}
	
	@RequestMapping(value="/batch/upload", method= RequestMethod.POST)
	@ResponseBody
    public SpringCallable<InvokeResult> batchUpload(HttpServletRequest request) throws Exception{
		
		return new SpringCallable<InvokeResult>(){
			@Override
			public InvokeResult doCall() throws Exception {
				Map<String,MultipartFile> map  =
						((MultipartHttpServletRequest)request).getFileMap();
				List<FileRequest> fileRequests=new ArrayList<>();
				map.forEach(Try.<String,MultipartFile>accept((name,file)->{
					if (!file.isEmpty()) {
		            	FileRequest fileRequest=new FileRequest();
		    			fileRequest.set_i_d(JUniqueUtils.unique());
		    			fileRequest.setName(name.contains(".")?name:file.getOriginalFilename());
		    			fileRequest.setBytes(file.getBytes());
		    			fileRequests.add(fileRequest);
		            }
				}));
		        if(!fileRequests.isEmpty()){
		        	List<FileResponse> fileResponses=fileTransfer.transfer(fileRequests);
		        	List<AttachmentRecordVO> attachmentRecordVOs=new ArrayList<>();
		        	fileResponses.forEach(Try.apply(response->{
						AttachmentRecordVO attachmentRecordVO=fileResponseListener.on(response);
						attachmentRecordVOs.add( attachmentRecordVO);
					}));
		        	return InvokeResult.success(attachmentRecordVOs);
		        }
		        return InvokeResult.bys("file is missing.");
			}
		};
		
    }
	
	
}
