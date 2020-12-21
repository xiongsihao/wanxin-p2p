package cn.itcast.wanxindepository.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户余额明细表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@Accessors(chain = true)
@TableName("balance_details")
public class BalanceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编码,生成唯一,用户在存管系统标识
     */
    @TableField("USER_NO")
    private String userNo;

    /**
     * 账户变动类型.1.增加.2.减少.3.冻结.4解冻
     */
    @TableField("CHANGE_TYPE")
    private Integer changeType;

    /**
     * 变动金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 冻结金额
     */
    @TableField("FREEZE_AMOUNT")
    private BigDecimal freezeAmount;

    /**
     * 可用余额
     */
    @TableField("BALANCE")
    private BigDecimal balance;

    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;

    /**
     * 账户变动时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 引起余额变动的冗余业务信息
     */
    @TableField("REQUEST_CONTENT")
    private String requestContent;


}
