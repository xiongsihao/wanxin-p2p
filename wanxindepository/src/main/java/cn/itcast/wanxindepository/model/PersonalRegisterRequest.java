package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PersonalRegisterRequest", description = "个人绑卡注册信息")
public class PersonalRegisterRequest extends BaseRequest {

	@ApiModelProperty(value = "用户编码")
	private String userNo;

	@ApiModelProperty("真实姓名")
	private String fullname;

	@ApiModelProperty("身份证号")
	private String idNumber;

	@ApiModelProperty("银行编码")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("银行卡号")
	private String cardNumber;

	@ApiModelProperty("存管预留手机号")
	private String mobile;

	@ApiModelProperty("存管交易密码")
	private String password;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty(value = "用户类型,个人or企业，预留")
	private Integer userType;

	@ApiModelProperty(value = "授权列表")
	private String authList;

	@ApiModelProperty("页面回跳 URL")
	private String callbackUrl;

}
