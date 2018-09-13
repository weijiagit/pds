package com.fykj.kernel._c.model;




/**
 * Created by J on 2016/3/9.
 */
@SuppressWarnings("serial")
public class InvokeResult implements JModel {

	private Object data;

    private boolean success=true;
    
    private ErrorModel errorModel;
    
    /**
     * 业务成功
     * success:
     * <pre>
     *
     * </pre>
     * @param data
     * @return
     * @author 张军
     */
    public static InvokeResult success(Object data) {
        InvokeResult result = new InvokeResult();
        result.data = data;
        return result;
    }

    /**
     * 业务异常
     * failureBusiness:
     * <pre>
     *
     * </pre>
     * @param message
     * @return
     * @author 张军
     */
    public static InvokeResult bys(String code) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.setErrorModel(ErrorModel.bys(code));
        return result;
    }
    
    /**
     * 系统异常
     * sys:
     * <pre>
     *
     * </pre>
     * @param code
     * @return
     * @author 张军
     */
    public static InvokeResult sys(String code) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.setErrorModel(ErrorModel.sys(code));
        return result;
    }
    
    /**
     * 登录异常
     *
     * @param code
     * @return
     * @author zhangj
     */
    public static InvokeResult LYS(String message) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.setErrorModel(ErrorModel.lys(message));
        return result;
    }

    
    /**
     * 登录异常
     *
     * @param code
     * @return
     * @author zhangj
     */
    public static InvokeResult LYS(String code,String message) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.setErrorModel(ErrorModel.lys(code,message));
        return result;
    }

    public Object getData() {
        return data;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public ErrorModel getErrorModel() {
		return errorModel;
	}

	public void setErrorModel(ErrorModel errorModel) {
		this.errorModel = errorModel;
	}

	
}
