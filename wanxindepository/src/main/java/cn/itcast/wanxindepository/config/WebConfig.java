package cn.itcast.wanxindepository.config;

import cn.itcast.wanxindepository.interceptor.LocalRequestInterceptor;
import cn.itcast.wanxindepository.interceptor.P2PRequestInterceptor;
import cn.itcast.wanxindepository.interceptor.VerificationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Resource
	private VerificationInterceptor verificationInterceptor;

	@Resource
	private P2PRequestInterceptor p2PRequestInterceptor;

	@Resource
	private LocalRequestInterceptor localRequestInterceptor;

	/**
	 * 添加静态资源文件，外部可以直接访问地址
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localRequestInterceptor).addPathPatterns("/trans/**");
		registry.addInterceptor(verificationInterceptor).addPathPatterns("/gateway/**").addPathPatterns("/service/**");
		registry.addInterceptor(p2PRequestInterceptor).addPathPatterns("/gateway/**")
				.addPathPatterns("/service/**");
	}
}
