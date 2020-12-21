package cn.itcast.wanxindepository.common.util;

/**
 * <P>
 * 通用工具类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-06-25
 */
public class CommonUtil {

	public static String hiddenMobile(String mobile) {
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}

}
