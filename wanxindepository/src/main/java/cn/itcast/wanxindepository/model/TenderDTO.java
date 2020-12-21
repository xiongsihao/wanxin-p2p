package cn.itcast.wanxindepository.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 投标信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "TenderDTO", description = "投标信息表")
public class TenderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using= ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "请求流水号")
	private String requestNo;

	@ApiModelProperty(value = "投标人用户编码")
	private String userNo;

	@ApiModelProperty(value = "标的编码")
	private String projectNo;

	@ApiModelProperty(value = "投标冻结金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "投标状态")
	private Integer tenderStatus;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createDate;

	@ApiModelProperty(value = "应用编码")
	private String appCode;


}
