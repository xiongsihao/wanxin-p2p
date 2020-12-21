package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 存管用户绑定银行卡信息
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("depository_bank_card")
public class DepositoryBankCard implements Serializable {

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
	@TableField("BANK_Name")
	private String bankName;

    /**
     * 银行卡号
     */
    @TableField("CARD_NUMBER")
    private String cardNumber;

    /**
     * 银行预留手机号
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;

    /**
     * 请求流水号
     */
    @TableField("REQUEST_NO")
    private String requestNo;


}
