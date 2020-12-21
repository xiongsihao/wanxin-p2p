package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 存管系统请求信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "RequestDetailsDTO", description = "存管系统请求信息表")
public class RequestDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty(value = "应用编码")
	private String appCode;

	private String requestNo;

	@ApiModelProperty(value = "请求类型:1.用户信息、2.绑卡信息")
	private String serviceName;

	private String requestData;

	@ApiModelProperty("返回数据")
	private String responseData;

	@ApiModelProperty(value = "执行结果")
	private Integer status;

	@ApiModelProperty(value = "请求时间")
	private LocalDateTime createDate;

	@ApiModelProperty(value = "执行返回时间")
	private LocalDateTime finishDate;

}
