package cn.itcast.wanxindepository.common.constant;

/**
 * <P>
 * 投标状态枚举类
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/28
 */
public enum TenderStatusCode {
    /**
     * 已冻结
     */
    FROZEN("FROZEN", "已冻结"),
    /**
     * 已放款
     */
    LOAN("LOAN", "已放款"),
    /**
     * 已退款
     */
    REFUNDED("REFUNDED", "已退款");


    private String code;
    private String desc;

    TenderStatusCode(String code, String desc) {
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
