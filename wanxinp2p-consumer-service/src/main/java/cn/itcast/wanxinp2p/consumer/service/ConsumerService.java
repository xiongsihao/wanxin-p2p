package cn.itcast.wanxinp2p.consumer.service;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.entity.Consumer;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.GatewayRequest;
import cn.itcast.wanxinp2p.depository.model.DepositoryConsumerResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : xsh
 * @create : 2020-12-13 - 15:54
 * @describe:
 */
public interface ConsumerService extends IService<Consumer> {

    /**
     * 检测用户是否存在
     * @param mobile
     * @return
     */
    Integer checkMobile(String mobile);

    /**
     * 用户注册
     * @param consumerRegisterDTO
     */
    void register(ConsumerRegisterDTO consumerRegisterDTO);

    /**
     * 生成开户数据
     * @param consumerRequest
     * @return
     */
    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    /**
     * 更新开户结果
     * @param response
     * @return
     */
    Boolean modifyResult(DepositoryConsumerResponse response);
}
