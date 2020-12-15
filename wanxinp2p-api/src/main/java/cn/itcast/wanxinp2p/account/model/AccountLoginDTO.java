package cn.itcast.wanxinp2p.account.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 封装账户登录信息
 * </p>
 */
@Data
@ApiModel(value = "AccountLoginDTO", description = "账户登录信息")
public class AccountLoginDTO {

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("手机号")
	private String mobile;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("域(c：c端用户；b：b端用户)")
	private String domain;

}
