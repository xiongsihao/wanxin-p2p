package cn.itcast.wanxindepository.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 银行用户信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "BankUserDTO", description = "银行用户信息表")
public class BankUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "真实姓名")
	private String fullname;

	@ApiModelProperty(value = "身份证号")
	private String idNumber;

	@ApiModelProperty(value = "银行预留手机号")
	private String mobile;

	@ApiModelProperty(value = "用户类型,个人or企业，预留")
	private Integer userType;


}
