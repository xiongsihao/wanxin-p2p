package cn.itcast.wanxinp2p.transaction.common.utils;

import cn.itcast.wanxinp2p.transaction.model.LoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {

	/**
	 * 获取当前登录用户
	 *
	 * @author zhupeiyuan
	 * @date 2019-05-14
	 */
	public static LoginUser getUser() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		if (servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();

			Object jwt = request.getAttribute("jsonToken");
			if (jwt instanceof LoginUser) {
				return (LoginUser) jwt;
			}
		}

		return new LoginUser();
	}

}
