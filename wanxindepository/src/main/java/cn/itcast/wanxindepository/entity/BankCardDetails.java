package cn.itcast.wanxindepository.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 银行卡明细
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("bank_card_details")
public class BankCardDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 银行卡ID
     */
    @TableField("BANK_CARD_ID")
    private Long bankCardId;

    /**
     * 账户变动类型
     */
    @TableField("CHANGE_TYPE")
    private Integer changeType;

    /**
     * 变动金额
     */
    @TableField("MONEY")
    private BigDecimal money;

    /**
     * 当前余额
     */
    @TableField("BALANCE")
    private BigDecimal balance;

    /**
     * 账户变动时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

}
