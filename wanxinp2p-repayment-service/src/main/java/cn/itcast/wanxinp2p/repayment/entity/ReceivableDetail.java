package cn.itcast.wanxinp2p.repayment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 投资人实收明细
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReceivableDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private Long id;

    /**
     * 应收项标识
     */
    @TableField("RECEIVABLE_ID")
    private Long receivableId;

    /**
     * 实收本息
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 实收时间
     */
    @TableField("RECEIVABLE_DATE")
    private LocalDateTime receivableDate;


}
