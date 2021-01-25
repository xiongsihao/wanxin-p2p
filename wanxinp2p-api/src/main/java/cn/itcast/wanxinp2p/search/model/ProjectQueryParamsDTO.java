package cn.itcast.wanxinp2p.search.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "ProjectQueryParamsDTO", description = "标的检索条件")
public class ProjectQueryParamsDTO {

	@ApiModelProperty("标的标识")
	private Long id;

	@ApiModelProperty("数据标识列表")
	private Long [] ids;

	@ApiModelProperty("标的名称(分词匹配)")
	private String name;

	@ApiModelProperty("标的描述(分词匹配)")
	private String description;

	@ApiModelProperty("起止标的期限(单位:天)")
	private Integer startPeriod;

	@ApiModelProperty("起止标的期限(单位:天)")
	private Integer endPeriod;

	@ApiModelProperty("起止年化利率(投资人视图)")
	private BigDecimal startAnnualRate;

	@ApiModelProperty("起止年化利率(投资人视图)")
	private BigDecimal endAnnualRate;

	@ApiModelProperty("标的状态")
	private String projectStatus;

	@ApiModelProperty("可用状态")
	private Integer status;

	@ApiModelProperty("是否是债权出让标")
	private Integer isAssignment;
}
