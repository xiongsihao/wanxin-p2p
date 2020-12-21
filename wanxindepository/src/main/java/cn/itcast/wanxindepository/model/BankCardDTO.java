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
 * 银行用户银行卡信息
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-15
 */
@Data
@ApiModel(value = "BankCardDTO", description = "银行用户银行卡信息")
public class BankCardDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "用户标识")
	private Long userId;

	@ApiModelProperty(value = "银行编码")
	private String bankCode;

	@ApiModelProperty("银行名称")
	private String bankName;

	@ApiModelProperty(value = "银行卡号")
	private String cardNumber;

	@ApiModelProperty(value = "银行卡密码")
	private String password;


}
