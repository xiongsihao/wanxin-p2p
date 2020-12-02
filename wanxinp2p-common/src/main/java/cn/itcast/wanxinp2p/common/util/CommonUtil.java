package cn.itcast.wanxinp2p.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * <P>
 * 通用工具类
 * </p>
 */
public class CommonUtil {

	public static String hiddenMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return "";
		}
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}

}

