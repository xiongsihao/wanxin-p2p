package cn.itcast.wanxindepository.model;

import cn.itcast.wanxindepository.common.constant.TransactionStatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "WithdrawResponse", description = "用户提现请求返回信息")
public class WithdrawResponse extends BaseResponse {

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
