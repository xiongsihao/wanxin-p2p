package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.Tender;
import cn.itcast.wanxindepository.model.ConfirmLoanResponse;
import cn.itcast.wanxindepository.model.UserAutoPreTransactionRequest;
import cn.itcast.wanxindepository.model.UserAutoPreTransactionResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 投标信息表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface TenderService extends IService<Tender> {

	/**
	 * 投标预授权处理
	 * @param preTransactionRequest
	 * @return
	 */
	UserAutoPreTransactionResponse autoPreTransactionForTender(UserAutoPreTransactionRequest preTransactionRequest);

	/**
	 * 放款确认
	 * @param reqData
	 * @return
	 */
	ConfirmLoanResponse confirmLoan(String reqData);

}
