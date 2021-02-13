package cn.itcast.wanxinp2p.depository.service;

import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.GatewayRequest;
import cn.itcast.wanxinp2p.depository.entity.DepositoryRecord;
import cn.itcast.wanxinp2p.depository.model.DepositoryBaseResponse;
import cn.itcast.wanxinp2p.depository.model.DepositoryResponseDTO;
import cn.itcast.wanxinp2p.depository.model.UserAutoPreTransactionRequest;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : xsh
 * @create : 2021-01-04 - 1:52
 * @describe:
 */
public interface DepositoryRecordService extends IService<DepositoryRecord> {
    /**
     * 开通存管账户
     *
     * @param consumerRequest 开户信息
     * @return
     */
    GatewayRequest createConsumer(ConsumerRequest consumerRequest);

    /**
     * 根据请求流水号更新请求状态
     *
     * @param requestNo
     * @param requestsStatus
     * @return
     */
    Boolean modifyRequestStatus(String requestNo, Integer requestsStatus);

    /**
     * 保存标的
     *
     * @param projectDTO
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> createProject(ProjectDTO projectDTO);

    /**
     * 投标预处理
     * @param userAutoPreTransactionRequest
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest);


}
