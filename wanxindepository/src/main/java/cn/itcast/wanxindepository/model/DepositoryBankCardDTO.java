package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 存管用户绑定银行卡信息
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "DepositoryBankCardDTO", description = "存管用户绑定银行卡信息")
public class DepositoryBankCardDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "用户标识")
	private Long userId;

	@ApiModelProperty(value = "银行编码")
	private String bankCode;

	@ApiModelProperty(value = "银行卡号")
	private String cardNumber;

	@ApiModelProperty(value = "银行预留手机号")
	private String mobile;

	@ApiModelProperty(value = "应用编码")
	private String appCode;

	@ApiModelProperty(value = "请求流水号")
	private String requestNo;


}
