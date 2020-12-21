package cn.itcast.wanxindepository.common.constant;

/**
 * <P>
 * 标的状态枚举类
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/16
 */
public enum ProjectStatusCode {

    /**
     * 借款人新建发标状态为: 募集中
     */
    RAISE("COLLECTING", "募集中"),

    /**
     * 投资人完成投标状态为: 已满标
     */
    FULLY("FULLY", "已满标"),

    /**
     * 借款人已开始还款状态为: 还款中
     */
    REPAYMENT("REPAYING", "还款中"),

    /**
     * 借款人标的已被投资完成状态为: 已截标
     */
    TRUNCATE("FINISH", "已截标"),

    /**
     * 借款人标的未投资完成状态为: 流标
     */
    FAILED_AUCTION("MISCARRY", "流标");

    private String code;
    private String desc;

    ProjectStatusCode(String code, String desc) {
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
