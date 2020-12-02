package cn.itcast.wanxinp2p.consumer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
@EnableSwagger2  //开启swagger注解支持
public class SwaggerConfiguration {

	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo())
				.select()
				// 要扫描的API(Controller)基础包
				.apis(RequestHandlerSelectors.basePackage("cn.itcast.wanxinp2p"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo buildApiInfo() {
		Contact contact = new Contact("黑马程序员","","");
		return new ApiInfoBuilder()
				.title("万信金融P2P平台-用户服务API文档")
				.description("包含用户服务api")
				.contact(contact)
				.version("1.0.0").build();
	}
}
