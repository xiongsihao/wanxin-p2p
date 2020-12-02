package cn.itcast.wanxinp2p.common.util;

import cn.itcast.wanxinp2p.common.domain.CodePrefixCode;

import java.util.UUID;

/**
 * <P>
 * 标的编码, 请求流水号生成工具
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019/5/16
 */
public class CodeNoUtil {
    /**
     * 使用UUID使用编码
     *
     * @param prefixCode 前缀用与标识不同业务, 已用枚举类型规定业务名称
     * @return java.lang.String
     */
    public static String getNo(CodePrefixCode prefixCode) {

        return prefixCode.getCode() + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
