package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 存管用户信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编码,生成唯一,用户在存管系统标识
     */
    @TableField("USER_NO")
    private String userNo;

    /**
     * 真实姓名
     */
    @TableField("FULLNAME")
    private String fullname;

    /**
     * 身份证号
     */
    @TableField("ID_NUMBER")
    private String idNumber;

    /**
     * 存管支付密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 存管预留手机号
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 用户类型,个人or企业，预留
     */
    @TableField("USER_TYPE")
    private Integer userType;

    /**
     * 用户角色
     */
    @TableField("ROLE")
    private String role;

    /**
     * 授权列表
     */
    @TableField("AUTH_LIST")
    private String authList;

    /**
     * 是否已绑定银行卡
     */
    @TableField("IS_BIND_CARD")
    private Integer isBindCard;

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

	/**
	 * 产生时间
	 */
	@TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
	private LocalDateTime createDate;


}
