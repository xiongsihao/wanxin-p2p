package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 银行卡检索信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-18
 */
@Data
@ApiModel(value = "BankCardQuery", description = "银行卡检索信息")
public class BankCardQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "真实姓名")
	private String fullname;

	@ApiModelProperty(value = "身份证号")
	private String idNumber;

	@ApiModelProperty(value = "银行预留手机号")
	private String mobile;

	@ApiModelProperty(value = "银行编码")
	private String bankCode;

	@ApiModelProperty(value = "银行卡号")
	private String cardNumber;
}
