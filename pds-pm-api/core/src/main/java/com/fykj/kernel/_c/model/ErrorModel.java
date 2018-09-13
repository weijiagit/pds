/**
 * 
 */
package com.fykj.kernel._c.model;

/**
 * ClassName: ErrorModel
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月15日 下午4:27:49
 *
 */

public class ErrorModel  implements JModel{


    private String code;
    private String type; //sys bys
    private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
		
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ErrorModel  sys(String code){
	  ErrorModel errorModel = new ErrorModel();
	  errorModel.setCode(code);
	  errorModel.setType("SYS");
	return    errorModel;
	}
  
  public static ErrorModel  bys(String code){
	  ErrorModel errorModel = new ErrorModel();
	  errorModel.setCode(code);
	  errorModel.setType("BYS");
	return    errorModel;
  }
  
  public static ErrorModel  sys(String code,String message){
	  ErrorModel errorModel = new ErrorModel();
	  errorModel.setCode(code);
	  errorModel.setMessage(message);
	  errorModel.setType("BYS");
	return    errorModel;
  }
  
  public static ErrorModel lys(String message){
	  ErrorModel errorModel = new ErrorModel();
	  errorModel.setCode(message);
	  errorModel.setType("LYS");
	return    errorModel;
  }

  public static ErrorModel lys(String code,String message){
	  ErrorModel errorModel = new ErrorModel();
	  errorModel.setCode(code);
	  errorModel.setType("LYS");
	  errorModel.setMessage(message);
	return    errorModel;
  }
	
}
