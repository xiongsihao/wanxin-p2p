package cn.itcast.wanxinp2p.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticsearchConfig {

    @Value("${wanxinp2p.es.host}")
    private String es_host;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        //创建RestHighLevelClient客户端
        String ip=es_host.split(":")[0];
        int port=Integer.parseInt(es_host.split(":")[1]);
        HttpHost httpHost=new HttpHost(ip,port);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

}
