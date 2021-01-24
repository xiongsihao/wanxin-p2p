package cn.itcast.wanxinp2p.transaction.agent;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:43
 * @describe: C端服务代理
 */
@FeignClient(value = "consumer-service")
public interface ConsumerApiAgent {
    @GetMapping("/consumer/l/currConsumer/{mobile}")
    public RestResponse<ConsumerDTO> getCurrConsumer(@PathVariable("mobile") String mobile);
}
