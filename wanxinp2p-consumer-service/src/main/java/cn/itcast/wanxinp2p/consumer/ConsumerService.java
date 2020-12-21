package cn.itcast.wanxinp2p.consumer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = MongoAutoConfiguration.class, scanBasePackages = {"org.dromara.hmily", "cn.itcast.wanxinp2p.consumer"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.itcast.wanxinp2p.consumer.agent"})
@MapperScan("cn.itcast.wanxinp2p.consumer.mapper") //设置mapper接口的扫描包
public class ConsumerService {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerService.class, args);
    }
}