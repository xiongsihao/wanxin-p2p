package cn.itcast.wanxindepository.common.constant;

/**
 * <P>
 * 交易状态
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-23
 */
public enum TransactionStatusCode {

	ACCEPT("ACCEPT", "已受理"),
	SUCCESS("SUCCESS", "交易成功"),
	FAIL("FAIL", "交易失败"),
	ONSALE("ONSALE", "债权出让中"),
	COMPLETED("COMPLETED", "已结束"),
	INIT("INIT", "初始状态"),
	PROCESSING("PROCESSING", "处理中"),
	REFUNDED("REFUNDED", "已退款"),
	;

	private String code;
	private String desc;

	TransactionStatusCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
