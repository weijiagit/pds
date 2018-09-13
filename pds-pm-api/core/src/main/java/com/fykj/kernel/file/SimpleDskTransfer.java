package com.fykj.kernel.file;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._fn.Try;
import com.fykj.util.JDateUtils;
import com.fykj.util.JUniqueUtils;

@Component
public class SimpleDskTransfer implements FileTransfer {

	private static final Logger LOGGER=LoggerFactory.getLogger(SimpleDskTransfer.class);
	
	@Autowired
	private _Cfg cfg;
	
	private String _path(){
		String _path= cfg.getFile().getDskPath();
		if(!_path.endsWith("/")){
			_path=_path+"/";
		}
		return _path;
	}
	
	@Autowired
	private VirtualPathStrategy virtualPathStrategy;
	
	@Override
	public List<FileResponse> transfer(List<FileRequest> fileRequests) {
		Objects.requireNonNull(fileRequests);
		List<FileResponse> fileResponses=new ArrayList<>();
		fileRequests.forEach(Try.apply(t->{
			FileRequest fileRequest=t;
			String vPath=virtualPathStrategy.path(fileRequest);
			FileResponse fileResponse=new FileResponse();
			fileResponse.set_i_d(fileRequest.get_i_d());
			fileResponse.setName(fileRequest.getName());
			fileResponse.setUri(vPath);
			
			// 
			fileResponse.setFileRequest(fileRequest);
			fileResponses.add(fileResponse);
		}));
		
		fileResponses.forEach(Try.apply(t->{
			FileResponse fileResponse=t;
			File file=new File(_path()+fileResponse.getUri());
			if(file.exists()){
				LOGGER.info("file alrady exists (ignore) : "+fileResponse.getUri());
			}else{
				File parent=file.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
				try( FileOutputStream outputStream=new FileOutputStream(file);
						FileChannel fileChannel=outputStream.getChannel()){
					fileChannel.write(ByteBuffer.wrap(fileResponse.getFileRequest().getBytes()));
				}
			}
		}));
		
		return fileResponses;
		
		
	}
	
	
	public static interface VirtualPathStrategy{
		String path(FileRequest fileRequest);
	}

	@Component
	public static class _Simple_Date implements VirtualPathStrategy{

		@Override
		public String path(FileRequest fileRequest) {
			String _path0=JDateUtils.format(new Date(),"yyyyMMdd");
			int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			String uuid=JUniqueUtils.unique();
			return _path0+"/"+hour+"/"+uuid+"."+fileRequest.getName();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
