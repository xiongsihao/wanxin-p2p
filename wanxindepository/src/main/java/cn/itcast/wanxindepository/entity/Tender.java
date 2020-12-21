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
 * 投标信息表
 * </p>
 *
 * @author dev
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tender implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private Long id;


    /**
     * 投标人用户编码
     */
    @TableField("USER_NO")
    private String userNo;

    /**
     * 标的编码
     */
    @TableField("PROJECT_NO")
    private String projectNo;

    /**
     * 投标冻结金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 投标状态
     */
    @TableField("TENDER_STATUS")
    private String tenderStatus;

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
     * 投标/债权转让 请求流水号
     */
    @TableField("REQUEST_NO")
    private String requestNo;

	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;

}
