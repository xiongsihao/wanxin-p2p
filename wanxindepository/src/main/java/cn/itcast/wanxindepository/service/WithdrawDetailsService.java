package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.WithdrawDetails;
import cn.itcast.wanxindepository.model.RechargeRequest;
import cn.itcast.wanxindepository.model.RechargeResponse;
import cn.itcast.wanxindepository.model.WithdrawRequest;
import cn.itcast.wanxindepository.model.WithdrawResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户余额明细表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface WithdrawDetailsService extends IService<WithdrawDetails> {

    /**
     * 用户提现
     * @param withdrawRequest
     * @return
     */
    WithdrawResponse withDraw(WithdrawRequest withdrawRequest);

}
