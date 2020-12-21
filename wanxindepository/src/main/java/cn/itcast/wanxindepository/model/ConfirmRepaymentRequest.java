package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <P>
 * 还款确认请求信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/28
 */
@Data
@ApiModel(value = "ConfirmRepaymentRequest", description = "还款确认请求信息")
public class ConfirmRepaymentRequest extends BaseRequest {

    @ApiModelProperty("预处理业务流水号")
    private String preRequestNo;

    @ApiModelProperty("标的编码")
    private String projectNo;

    @ApiModelProperty("还款总额")
	private BigDecimal amount;

    @ApiModelProperty("平台佣金")
    private BigDecimal commission;

    @ApiModelProperty("放款明细")
    private List<ConfirmRepaymentDetailRequest> details;

}
