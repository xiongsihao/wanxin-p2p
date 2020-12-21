package cn.itcast.wanxindepository.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("recharge_details")
public class RechargeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 请求流水号
     */
    @TableField("REQUEST_NO")
    private String requestNo;

    /**
     * 用户编码,生成唯一,用户在存管系统标识
     */
    @TableField("USER_NO")
    private String userNo;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 触发时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 执行结果
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 执行返回时间
     */
    @TableField(value = "FINISH_DATE", fill = FieldFill.UPDATE)
    private LocalDateTime finishDate;

    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;


}
