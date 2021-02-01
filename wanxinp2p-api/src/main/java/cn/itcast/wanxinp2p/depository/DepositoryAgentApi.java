package cn.itcast.wanxinp2p.depository;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.model.UserAutoPreTransactionRequest;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;

/**
 * @author : xsh
 * @create : 2021-01-03 - 22:10
 * @describe: 银行存管系统代理服务API
 */
public interface DepositoryAgentApi {

    /**
     * 开通存管账户
     * @param consumerRequest 开户信息
     * @return
     */
    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    /**
     * 向银行存管系统发送标的信息
     * @param projectDTO
     * @return
     */
    RestResponse<String> createProject(ProjectDTO projectDTO);

    /**
     * 预授权处理
     * @param userAutoPreTransactionRequest 预授权处理信息
     * @return
     */
    RestResponse<String> userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest);
}
