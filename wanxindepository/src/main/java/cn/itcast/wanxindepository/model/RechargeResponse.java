package cn.itcast.wanxindepository.model;

import cn.itcast.wanxindepository.common.constant.TransactionStatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RechargeResponse", description = "用户充值请求返回信息")
public class RechargeResponse extends BaseResponse {

	@ApiModelProperty("交易状态")
	private String transactionStatus;

	@Override
	public void setSuccess() {
		super.setSuccess();
		this.transactionStatus = TransactionStatusCode.SUCCESS.getCode();
	}

	@Override
	public void setFailure() {
		super.setFailure();
		this.transactionStatus = TransactionStatusCode.FAIL.getCode();
	}
}
