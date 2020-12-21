package cn.itcast.wanxindepository.common.constant;

/**
 * <P>
 * 银行代码枚举类
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/18
 */
public enum BankCode {

	ICBC("ICBC", "中国工商银行"),
	CCB("CCB", "中国建设银行"),
	ABC("ABC", "中国农业银行"),
	BOC("BOC", "中国银行"),
	BCM("BCM", "交通银行"),
	CMB("CMB", "招商银行"),
	CMBC("CMBC", "中国民生银行"),
	CIB("CIB", "兴业银行");


    private String code;
    private String desc;

    BankCode(String code, String desc) {
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
