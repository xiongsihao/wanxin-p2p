package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.RechargeDetails;
import cn.itcast.wanxindepository.model.RechargeRequest;
import cn.itcast.wanxindepository.model.RechargeResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 充值记录表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface RechargeDetailsService extends IService<RechargeDetails> {

	/**
	 * 用户充值
	 * @param rechargeRequest
	 * @return
	 */
	RechargeResponse recharge(RechargeRequest rechargeRequest);

}
