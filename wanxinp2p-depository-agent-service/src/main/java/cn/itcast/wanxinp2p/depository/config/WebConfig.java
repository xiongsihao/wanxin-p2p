package cn.itcast.wanxinp2p.depository.config;

import cn.itcast.wanxinp2p.depository.interceptor.DepositoryNotifyVerificationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Resource
	private DepositoryNotifyVerificationInterceptor notifyVerificationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(notifyVerificationInterceptor).addPathPatterns("/gateway/**");
	}
}
