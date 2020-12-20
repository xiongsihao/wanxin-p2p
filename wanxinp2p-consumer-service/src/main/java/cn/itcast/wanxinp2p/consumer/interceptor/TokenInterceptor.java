package cn.itcast.wanxinp2p.consumer.interceptor;

import cn.itcast.wanxinp2p.account.model.LoginUser;
import cn.itcast.wanxinp2p.common.util.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token拦截处理
 */
public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
		String jsonToken = httpServletRequest.getParameter("jsonToken");
		if (StringUtils.isNotBlank(jsonToken)) {
			LoginUser loginUser = JSON
					.parseObject(EncryptUtil.decodeUTF8StringBase64(jsonToken), new TypeReference<LoginUser>() {
					});
			httpServletRequest.setAttribute("jsonToken", loginUser);
		}
		return true;
	}

}
