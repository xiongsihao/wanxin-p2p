package cn.itcast.wanxindepository.model;

import cn.itcast.wanxindepository.common.constant.AuditStatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PersonalRegisterResponse", description = "个人绑卡注册返回信息")
public class PersonalRegisterResponse extends BaseResponse {

	@ApiModelProperty("银行代码")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty("审核状态")
	private String auditStatus;

	@Override
	public void setSuccess() {
		super.setSuccess();
		this.setAuditStatus(AuditStatusCode.PASSED.getCode());
	}

	@Override
	public void setFailure() {
		super.setFailure();
		this.setAuditStatus(AuditStatusCode.REFUSED.getCode());
	}
}
