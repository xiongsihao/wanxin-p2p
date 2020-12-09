package cn.xsh.sailing.generator;

/**
 * 认证码生成器
 */
public interface VerificationCodeGenerator {

    /**
     * 认证码生成
     * @return
     */
    String generate();

}
