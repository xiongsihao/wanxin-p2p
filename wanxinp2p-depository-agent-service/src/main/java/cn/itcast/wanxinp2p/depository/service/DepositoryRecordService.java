package cn.itcast.wanxinp2p.depository.service;

import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.GatewayRequest;
import cn.itcast.wanxinp2p.depository.entity.DepositoryRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : xsh
 * @create : 2021-01-04 - 1:52
 * @describe:
 */
public interface DepositoryRecordService extends IService<DepositoryRecord> {

    GatewayRequest createConsumer(ConsumerRequest consumerRequest);
}
