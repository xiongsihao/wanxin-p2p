package cn.itcast.wanxindepository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 债权转让标的附加信息
 * </p>
 *
 * @author dev
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private Long id;

    /**
     * 标的标识
     */
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * 标的编码
     */
    @TableField("PROJECT_NO")
    private String projectNo;

    /**
     * 发标人用户标识(冗余)
     */
    @TableField("CONSUMER_ID")
    private Long consumerId;

    /**
     * 投标信息标识(转让来源)
     */
    @TableField("SOURCE_TENDER_ID")
    private Long sourceTenderId;

    /**
     * 原始标的标识(冗余)
     */
    @TableField("ROOT_PROJECT_ID")
    private Long rootProjectId;

    /**
     * 原始标的编码(冗余)
     */
    @TableField("ROOT_PROJECT_NO")
    private String rootProjectNo;

    /**
     * 债权转让 请求流水号
     */
    @TableField("ASSIGNMENT_REQUEST_NO")
    private String assignmentRequestNo;


}
