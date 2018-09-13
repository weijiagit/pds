package com.fykj.kernel.file;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface FileTransfer {

	public class FileResponse{
		
		@JsonIgnore
		private transient FileRequest fileRequest;
		
		private String _i_d;
		
		protected String uri;
		
		protected String name;

		@JsonIgnore
		public FileRequest getFileRequest() {
			return fileRequest;
		}

		public void setFileRequest(FileRequest fileRequest) {
			this.fileRequest = fileRequest;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String get_i_d() {
			return _i_d;
		}

		public void set_i_d(String _i_d) {
			this._i_d = _i_d;
		}
		
	}
	
	public class FileRequest{
		
		private String _i_d;
		
		protected String name;
		
		protected byte[] bytes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}

		public String get_i_d() {
			return _i_d;
		}

		public void set_i_d(String _i_d) {
			this._i_d = _i_d;
		}
	}
	
	
	/**
	 * 
	 * @param name file name
	 * @param bytes file content
	 * @return
	 */
	List<FileResponse> transfer(List<FileRequest> fileRequests);
	
}
