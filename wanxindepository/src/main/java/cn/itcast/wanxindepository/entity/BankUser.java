package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 银行用户信息表
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Data
@TableName("bank_user")
public class BankUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

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
     * 银行预留手机号
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 用户类型,个人or企业，预留
     */
    @TableField("USER_TYPE")
    private Integer userType;


}
