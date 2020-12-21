package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "RechargeRequest", description = "用户充值请求信息")
public class RechargeRequest extends BaseRequest {

	@ApiModelProperty("标识")
	private Long id;

	@ApiModelProperty("用户编号")
	private String userNo;

	@ApiModelProperty("充值金额")
	private BigDecimal amount;

	@ApiModelProperty("银行编码")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("银行卡号")
	private String cardNumber;

	@ApiModelProperty("银行预留手机号")
	private String mobile;

	@ApiModelProperty("交易密码")
	private String password;

	@ApiModelProperty("页面回跳 URL")
	private String callbackUrl;
}