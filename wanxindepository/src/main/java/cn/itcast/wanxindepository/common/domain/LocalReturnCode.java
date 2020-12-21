
package cn.itcast.wanxindepository.common.domain;


/**
 * <P>
 * 本地调用系统返回码
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/22
 */
public enum LocalReturnCode implements ReturnCode {

	SUCCESS("00000", "成功"),
	EXCEPTION("00001", "系统异常"),
	E_200101("60101","银行卡号已存在"),
	E_200102("60102","银行卡号不存在"),
	E_200103("60103","银行卡信息错误"),

	E_200201("600201","用户编码不存在"),


	E_200301("60301","个人信息验证失败"),
	E_200302("60302","用户交易密码错误"),
	;

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}


	LocalReturnCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static LocalReturnCode setErrorCode(String code) {
		for (LocalReturnCode errorCode : LocalReturnCode.values()) {
			if (errorCode.getCode() == code) {
				return errorCode;
			}
		}
		return null;
	}
}
