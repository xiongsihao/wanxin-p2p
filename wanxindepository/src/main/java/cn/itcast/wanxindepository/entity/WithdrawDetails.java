package cn.itcast.wanxindepository.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 用户余额明细表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("withdraw_details")
public class WithdrawDetails implements Serializable {

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
     * 平台佣金
     */
    @TableField("COMMISSION")
    private BigDecimal commission;

    /**
     * 触发时间
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 执行结果
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 执行返回时间
     */
    @TableField("FINISH_DATE")
    private LocalDateTime finishDate;

    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;


}
