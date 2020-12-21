package cn.itcast.wanxindepository;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.itcast.wanxindepository.mapper")
public class WanxindepositoryServer {

    public static void main(String[] args) {
        SpringApplication.run(WanxindepositoryServer.class, args);

    }
}
