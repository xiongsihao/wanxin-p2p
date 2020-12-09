package cn.xsh.sailing.common.domain;


public enum CommonErrorCode implements ErrorCode {

	SUCCESS(0, "成功"),

	CUSTOM(999998, "自定义异常"),

	UNKNOWN(999999, "未知错误");


	private int code;
	private String desc;

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	CommonErrorCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static CommonErrorCode setErrorCode(int code) {
		for (CommonErrorCode errorCode : CommonErrorCode.values()) {
			if (errorCode.getCode() == code) {
				return errorCode;
			}
		}
		return null;
	}
}
