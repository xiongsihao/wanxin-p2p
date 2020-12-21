package cn.itcast.wanxinp2p.account.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.dromara.hmily.common.config.HmilyDbConfig;
import org.dromara.hmily.core.bootstrap.HmilyTransactionBootstrap;
import org.dromara.hmily.core.service.HmilyInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class HmilyConfig {

    @Autowired
    private Environment env;

    @Bean
    public HmilyTransactionBootstrap hmilyTransactionBootstrap(HmilyInitService hmilyInitService){
        HmilyTransactionBootstrap hmilyTransactionBootstrap = new HmilyTransactionBootstrap(hmilyInitService);
        hmilyTransactionBootstrap.setSerializer(env.getProperty("org.dromara.hmily.serializer"));
        hmilyTransactionBootstrap.setRetryMax(Integer.parseInt(env.getProperty("org.dromara.hmily.retryMax")));
        hmilyTransactionBootstrap.setRepositorySupport(env.getProperty("org.dromara.hmily.repositorySupport"));
        hmilyTransactionBootstrap.setStarted(Boolean.parseBoolean(env.getProperty("org.dromara.hmily.started")));
        HmilyDbConfig hmilyDbConfig = new HmilyDbConfig();
        hmilyDbConfig.setDriverClassName(env.getProperty("org.dromara.hmily.hmilyDbConfig.driverClassName"));
        hmilyDbConfig.setUrl(env.getProperty("org.dromara.hmily.hmilyDbConfig.url"));
        hmilyDbConfig.setUsername(env.getProperty("org.dromara.hmily.hmilyDbConfig.username"));
        hmilyDbConfig.setPassword(env.getProperty("org.dromara.hmily.hmilyDbConfig.password"));
        hmilyTransactionBootstrap.setHmilyDbConfig(hmilyDbConfig);
        return hmilyTransactionBootstrap;
    }

}
