package cn.itcast.wanxindepository.common.domain;

/**
 * <p>
 * 自定义业务异常
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 5565760508056698922L;

	private String requestNo;
	private ReturnCode returnCode;

	public BusinessException() {
		super();
	}

	public BusinessException(ReturnCode returnCode) {
		this("", returnCode);
	}

	public BusinessException(String requestNo, ReturnCode returnCode) {
		super();
		this.requestNo = requestNo;
		this.returnCode = returnCode;
	}

	public BusinessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BusinessException(String requestNo, ReturnCode returnCode, String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.requestNo = requestNo;
		this.returnCode = returnCode;
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(String requestNo, ReturnCode returnCode, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.requestNo = requestNo;
		this.returnCode = returnCode;
	}

	public BusinessException(String arg0) {
		super(arg0);
	}

	public BusinessException(String requestNo, ReturnCode returnCode, String arg0) {
		super(arg0);
		this.requestNo = requestNo;
		this.returnCode = returnCode;
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}

	public BusinessException(String requestNo, ReturnCode returnCode, Throwable arg0) {
		super(arg0);
		this.requestNo = requestNo;
		this.returnCode = returnCode;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

}
