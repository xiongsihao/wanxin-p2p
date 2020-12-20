
package cn.itcast.wanxintx.hmilydemo.bank1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.itcast.wanxintx.hmilydemo.bank1.feignClient"})
@ComponentScan({"cn.itcast.wanxintx.hmilydemo.bank1","org.dromara.hmily"})
public class Bank1HmilyServer {
	public static void main(String[] args) {
		SpringApplication.run(Bank1HmilyServer.class, args);
	}
}
