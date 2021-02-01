package cn.itcast.wanxinp2p.depository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <P>
 *  预授权处理返回信息
 * </p>
 */
@Data
@ApiModel(value = "UserAutoPreTransactionResponse", description = "预授权处理返回信息")
public class UserAutoPreTransactionResponse {

	@ApiModelProperty("交易状态")
	private String status;

	@ApiModelProperty("预处理业务类型")
	private String bizType;
}
