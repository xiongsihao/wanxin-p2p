package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "RechargeDetailsDTO", description = "充值记录表")
public class RechargeDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "请求流水号")
	private String requestNo;

	@ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
	private String userNo;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "应用编码")
	private String appCode;


}
