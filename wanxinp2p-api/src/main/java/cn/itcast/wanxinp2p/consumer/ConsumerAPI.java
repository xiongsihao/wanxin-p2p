package cn.itcast.wanxinp2p.consumer;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.GatewayRequest;

/**
 * @author : xsh
 * @create : 2020-12-12 - 2:04
 * @describe: 用户中心接口api
 */
public interface ConsumerAPI {

    /**
     * 用户注册 保存用户信息
     * @param consumerRegisterDTO
     * @return
     */
    RestResponse register(ConsumerRegisterDTO consumerRegisterDTO);


    /**
     * 生成开户请求数据
     * @param consumerRequest 开户信息
     * @return
     */
    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);
}
