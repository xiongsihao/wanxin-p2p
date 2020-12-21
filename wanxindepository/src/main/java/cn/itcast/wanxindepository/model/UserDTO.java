package cn.itcast.wanxindepository.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 存管用户信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "UserDTO", description = "存管用户信息表")
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
	private String userNo;

	@ApiModelProperty(value = "真实姓名")
	private String fullname;

	@ApiModelProperty(value = "身份证号")
	private String idNumber;

	@ApiModelProperty(value = "存管支付密码")
	private String password;

	@ApiModelProperty(value = "存管预留手机号")
	private String mobile;

	@ApiModelProperty(value = "用户类型,个人or企业，预留")
	private Integer userType;

	@ApiModelProperty(value = "用户角色")
	private String role;

	@ApiModelProperty(value = "授权列表")
	private String authList;

	@ApiModelProperty(value = "是否已绑定银行卡")
	private Integer isBindCard;

	@ApiModelProperty(value = "应用编码")
	private String appCode;

	@ApiModelProperty(value = "请求流水号")
	private String requestNo;

	@ApiModelProperty("产生时间")
	private LocalDateTime createDate;

}
