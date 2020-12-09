package cn.xsh.sailing.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证信息，客户端保存key，展示content
 */
@Data
@AllArgsConstructor
public class VerificationInfo {

    /**
     *
     */
    private String key;

    /**
     * 混淆后的内容
     * 举例：
     * 1.图片验证码为:图片base64编码
     * 2.短信验证码为:null
     * 3.邮件验证码为: null
     * 4.邮件链接点击验证为：null
     * ...
     */
    private String content;

}
