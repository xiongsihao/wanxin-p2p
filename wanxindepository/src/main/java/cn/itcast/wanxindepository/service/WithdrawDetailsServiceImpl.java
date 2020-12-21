package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.common.constant.StatusCode;
import cn.itcast.wanxindepository.common.domain.BusinessException;
import cn.itcast.wanxindepository.common.domain.RemoteReturnCode;
import cn.itcast.wanxindepository.entity.DepositoryBankCard;
import cn.itcast.wanxindepository.entity.WithdrawDetails;
import cn.itcast.wanxindepository.mapper.WithdrawDetailsMapper;
import cn.itcast.wanxindepository.message.GatewayMessageProducer;
import cn.itcast.wanxindepository.model.WithdrawRequest;
import cn.itcast.wanxindepository.model.WithdrawResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户余额明细表 服务实现类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Slf4j
@Service
public class WithdrawDetailsServiceImpl extends ServiceImpl<WithdrawDetailsMapper, WithdrawDetails>
		implements WithdrawDetailsService {

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private GatewayMessageProducer producer;

	/**
	 * 用户提现
	 * @param withdrawRequest
	 * @return
	 */
	@Override
	@Transactional
	public WithdrawResponse withDraw(WithdrawRequest withdrawRequest) {

		WithdrawResponse response = new WithdrawResponse();
		response.setRequestNo(withdrawRequest.getRequestNo());

		//保存提现记录
		WithdrawDetails withdrawDetails = new WithdrawDetails();
		BeanUtils.copyProperties(withdrawRequest, withdrawDetails);
		withdrawDetails.setStatus(StatusCode.STATUS_OUT.getCode());
		save(withdrawDetails);

		try {
			//增加用户实体银行卡余额
			DepositoryBankCard depositoryBankCard = userService
					.getDepositoryBankCardByUserNo(withdrawRequest.getUserNo());
			bankCardService.increaseBalance(depositoryBankCard.getCardNumber(), withdrawRequest.getAmount());

			//减少用户在P2P平台可用余额信息
			balanceDetailsService.withDraw(withdrawRequest);

			//更新提现记录结果
			withdrawDetails.setStatus(StatusCode.STATUS_IN.getCode());
			updateById(withdrawDetails);

			//产生提现成功消息
			response.setSuccess();
			//producer.withdraw(withdrawRequest.getAppCode(), response);
		} catch (Exception e) {
			log.error(e.getMessage());

			//更新提现记录结果
			withdrawDetails.setStatus(StatusCode.STATUS_FAIL.getCode());
			updateById(withdrawDetails);

			//产生提现成功消息
			response.setFailure();
			//producer.withdraw(withdrawRequest.getAppCode(), response);
			throw new BusinessException(response.getRequestNo(), RemoteReturnCode.EXCEPTION);
		}
		return response;

	}
}
