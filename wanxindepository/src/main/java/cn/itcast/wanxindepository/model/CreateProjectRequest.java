package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <P>
 * 创建标的请求数据
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/20
 */
@Data
@ApiModel(value = "CreateProjectRequest", description = "创建标的请求数据")
public class CreateProjectRequest extends BaseRequest {

	@ApiModelProperty("平台用户编号")
    private String userNo;

	@ApiModelProperty("标的号")
    private String projectNo;

	@ApiModelProperty("标的金额")
    private BigDecimal projectAmount;

	@ApiModelProperty("标的名称")
    private String projectName;

	@ApiModelProperty("标的描述")
    private String projectDesc;

	@ApiModelProperty("标的产品类型")
    private String projectType;

	@ApiModelProperty("标的期限")
    private Integer projectPeriod;

	@ApiModelProperty("年化利率( 5%只传5 )")
    private BigDecimal annualRate;

	@ApiModelProperty("还款方式")
    private String repaymentWay;

	@ApiModelProperty("是否是债权出让标")
	private Integer isAssignment;
}
