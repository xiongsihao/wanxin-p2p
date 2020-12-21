package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <P>
 * 还款明细请求信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/28
 */
@Data
@ApiModel(value = "ConfirmRepaymentDetailRequest", description = "还款明细请求信息")
public class ConfirmRepaymentDetailRequest {

    @ApiModelProperty("投资人用户编码")
    private String userNo;

    @ApiModelProperty("向投资人收取的佣金")
    private BigDecimal commission;

    @ApiModelProperty("派息")
    private BigDecimal dividend;

    @ApiModelProperty("投资人应得本金")
    private BigDecimal amount;

    @ApiModelProperty("投资人应得利息")
    private BigDecimal interest;
}