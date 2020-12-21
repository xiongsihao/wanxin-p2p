package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <P>
 * 封装提现请求数据
 * </p>
 *
 * @author zhangdongxu
 * @since 2019/6/5
 */
@Data
@ApiModel("用户提现请求信息")
public class WithdrawRequest extends BaseRequest {

	@ApiModelProperty("标识")
	private Long id;

	@ApiModelProperty("用户编号")
	private String userNo;

	@ApiModelProperty("提现金额")
	private BigDecimal amount;

	@ApiModelProperty("平台佣金")
	private BigDecimal commission;

	@ApiModelProperty("页面回调URL")
	private String callbackUrl;

	@ApiModelProperty("银行卡号")
	private String cardNumber;

	@ApiModelProperty("银行预留手机号")
	private String mobile;

	@ApiModelProperty("银行编码")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;
}
