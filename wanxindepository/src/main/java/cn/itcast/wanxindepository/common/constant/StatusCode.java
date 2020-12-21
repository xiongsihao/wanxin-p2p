package cn.itcast.wanxindepository.common.constant;

/**
 * <P>
 * 标的 可用状态 枚举类
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019/5/16
 */
public enum StatusCode {

    /**
     * 发/同布失败
     */
    STATUS_FAIL(2, "发/同布失败"),
    /**
     * 已发/同布
     */
    STATUS_IN(1, "已发/同布"),
    /**
     * 未发/同布
     */
    STATUS_OUT(0,"未发/同布");

    private Integer code;
    private String desc;
    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
