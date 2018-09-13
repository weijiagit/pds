package com.fykj.kernel.file;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.file.FileTransfer.FileResponse;

public interface FileResponseListener<O extends JModel> {

	/**
	 * 
	 * @param fileResponse
	 * @return the identifier it's a URI or primary of certain table
	 * @throws Exception
	 */
	O on(FileResponse fileResponse) throws Exception;
	
	
}
