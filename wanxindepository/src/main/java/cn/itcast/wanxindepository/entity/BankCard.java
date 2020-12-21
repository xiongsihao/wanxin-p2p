package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 银行用户银行卡信息
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("bank_card")
public class BankCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户标识
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 银行编码
     */
    @TableField("BANK_CODE")
    private String bankCode;

	/**
	 * 银行名称
	 */
	@TableField("BANK_NAME")
	private String bankName;

    /**
     * 银行卡号
     */
    @TableField("CARD_NUMBER")
    private String cardNumber;

    /**
     * 银行卡密码
     */
    @TableField("PASSWORD")
    private String password;


}
