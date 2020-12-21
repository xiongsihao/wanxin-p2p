package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 标的信息表
 * </p>
 *
 * @author dev
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private Long id;

    /**
     * 发标人用户编码
     */
    @TableField("USER_NO")
    private String userNo;

    /**
     * 标的编码
     */
    @TableField("PROJECT_NO")
    private String projectNo;

    /**
     * 标的名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 标的描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 标的类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 标的期限(单位:天)
     */
    @TableField("PERIOD")
    private Integer period;

    /**
     * 年化利率(借款人视图)
     */
    @TableField("BORROWER_ANNUAL_RATE")
    private BigDecimal borrowerAnnualRate;

    /**
     * 还款方式
     */
    @TableField("REPAYMENT_WAY")
    private String repaymentWay;

    /**
     * 募集金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 标的状态
     */
    @TableField("PROJECT_STATUS")
    private String projectStatus;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFY_DATE", fill = FieldFill.UPDATE)
    private LocalDateTime modifyDate;

    /**
     * 是否是债权出让标
     */
    @TableField("IS_ASSIGNMENT")
    private Integer isAssignment;


	/**
	 * 请求流水号
	 */
	@TableField("REQUEST_NO")
	private String requestNo;

}
