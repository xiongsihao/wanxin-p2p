package cn.itcast.wanxinp2p.consumer;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;

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
}
