package cn.itcast.wanxindepository.common.domain;

/**
 * <P>
 * 远程调用系统返回码
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/22
 */
public enum RemoteReturnCode implements ReturnCode {
    SUCCESS("00000", "成功"),
    EXCEPTION("00001", "系统异常"),
    INTERN_ERROR("00002", "系统内部错误"),
    PARAM_FAIL("00003", "参数校验不通过"),
    SIGN_FAIL("00004", "签名验证失败"),

	BANK_CARD_BALANCE_NOT_ENOUGH("30041", "银行卡余额不足"),
	BALANCE_NOT_ENOUGH("30042", "账户余额不足"),
	TENDER_NOT_EXIST("30010", "投标信息不存在"),

	CUSTOM("99998","自定义异常"),
	UNKNOWN("99999","未知错误");
    ;

	private String code;
	private String desc;

	RemoteReturnCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static RemoteReturnCode setErrorCode(String code) {
		for (RemoteReturnCode errorCode : RemoteReturnCode.values()) {
			if (errorCode.getCode() == code) {
				return errorCode;
			}
		}
		return null;
	}
}
