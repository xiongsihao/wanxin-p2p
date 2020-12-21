package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "UserAutoPreTransactionRequest", description = "预授权处理请求信息")
public class UserAutoPreTransactionRequest extends BaseRequest {

    @ApiModelProperty("出款人平台用户编号")
    private String userNo;

    @ApiModelProperty("预处理业务类型")
    private String bizType;

	@ApiModelProperty("冻结金额")
	private BigDecimal amount;

    @ApiModelProperty("红包金额")
    private BigDecimal preMarketingAmount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("标的号")
    private String projectNo;

    @ApiModelProperty("债权出让流水号，购买债权时需填此参数")
    private String creditsaleRequestNo;

}