package cn.itcast.wanxindepository.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 标的信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "ProjectDTO", description = "标的信息表")
public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using= ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "发标人用户编码")
	private String userNo;

	@ApiModelProperty(value = "标的编码")
	private String projectNo;

	@ApiModelProperty(value = "标的名称")
	private String name;

	@ApiModelProperty(value = "标的描述")
	private String description;

	@ApiModelProperty(value = "标的类型")
	private Integer type;

	@ApiModelProperty(value = "标的期限(单位:天)")
	private Integer period;

	@ApiModelProperty(value = "年化利率(5%只要传5)")
	private Integer annualRate;

	@ApiModelProperty(value = "还款方式")
	private Integer repaymentWay;

	@ApiModelProperty(value = "募集金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "标的状态")
	private Integer projectStatus;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createDate;

	@ApiModelProperty(value = "应用编码")
	private String appCode;

	@ApiModelProperty(value = "是否是债权出让标")
	private Integer isDebentureSale;

	@ApiModelProperty(value = "投标信息标识(转让来源)")
	private String sourceTenderRequestNo;

	@ApiModelProperty(value = "原始标的编码(冗余)")
	private String rootProjectNo;

	@ApiModelProperty(value = "债权转让 请求流水号 ,债权转让时ProjectNO为空")
	private String debentureSaleRequestNo;


}
