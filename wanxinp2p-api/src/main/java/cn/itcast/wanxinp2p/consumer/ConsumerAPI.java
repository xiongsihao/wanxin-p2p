package cn.itcast.wanxinp2p.consumer;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.*;
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

    /**
     * 获得当前登录用户（微服务调用）
     * @return
     */
    RestResponse<ConsumerDTO> getCurrConsumer(String mobile);

    /**
     * 获取当前登录用户 (前端调用)
     * @return
     */
    RestResponse<ConsumerDTO> getMyConsumer();

    /**
     * 获取借款人用户信息
     * @param id
     * @return
     */
    RestResponse<BorrowerDTO> getBorrower(Long id);

    /**
     * 获取当前登录用户余额信息
     * @param userNo
     * @return
     */
    RestResponse<BalanceDetailsDTO> getBalance(String userNo);
}
