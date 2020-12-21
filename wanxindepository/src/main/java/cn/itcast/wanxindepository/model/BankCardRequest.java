package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <P>
 * 银行卡注册信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-18
 */
@Data
@ApiModel(value = "BankCardRequest", description = "银行卡注册信息")
public class BankCardRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "真实姓名")
	private String fullname;

	@ApiModelProperty(value = "身份证号")
	private String idNumber;

	@ApiModelProperty(value = "银行预留手机号")
	private String mobile;

	@ApiModelProperty(value = "用户类型,个人or企业，预留")
	private Integer userType;

	@ApiModelProperty(value = "银行编码")
	private String bankCode;

	@ApiModelProperty(value = "银行名称")
	private String bankName;

	@ApiModelProperty(value = "银行卡号")
	private String cardNumber;

	@ApiModelProperty(value = "银行卡密码")
	private String password;

	@ApiModelProperty(value = "当前余额")
	private BigDecimal balance;
}
