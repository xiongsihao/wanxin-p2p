package cn.itcast.wanxinp2p.depository.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 * Mybatis-Plus 配置
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-10
 */
@Configuration
@MapperScan("cn.itcast.wanxinp2p.**.mapper")
public class MybatisPlusConfig {

	/**
	 * 分页插件，自动识别数据库类型
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 启用性能分析插件
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor(){
		return new PerformanceInterceptor();
	}
}