package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.common.constant.TenderStatusCode;
import cn.itcast.wanxindepository.common.domain.BusinessException;
import cn.itcast.wanxindepository.common.domain.RemoteReturnCode;
import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.entity.BalanceDetails;
import cn.itcast.wanxindepository.entity.Project;
import cn.itcast.wanxindepository.entity.Tender;
import cn.itcast.wanxindepository.mapper.TenderMapper;
import cn.itcast.wanxindepository.model.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 投标信息表 服务实现类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Slf4j
@Service
public class TenderServiceImpl extends ServiceImpl<TenderMapper, Tender> implements TenderService {

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private ProjectService projectService;

	@Override
	@Transactional
	public UserAutoPreTransactionResponse autoPreTransactionForTender(
			UserAutoPreTransactionRequest preTransactionRequest) {
		UserAutoPreTransactionResponse response = new UserAutoPreTransactionResponse();
		response.setRequestNo(preTransactionRequest.getRequestNo());
		response.setBizType(preTransactionRequest.getBizType());
		String requestNo = preTransactionRequest.getRequestNo();

		try {
			// 校验用户余额是否够用
			UserAutoPreTransactionResponse verifyResponse = balanceDetailsService
					.verifyEnough(preTransactionRequest.getUserNo(), preTransactionRequest.getAmount(), response);
			if (verifyResponse != null) {
				//返回余额不足错误信息
				return verifyResponse;
			}

			//保存投标信息
			Tender tender = new Tender();
			BeanUtils.copyProperties(preTransactionRequest, tender);
			tender.setTenderStatus(TenderStatusCode.FROZEN.getCode());
			save(tender);

			//冻结用户余额
			balanceDetailsService.freezeBalance(preTransactionRequest);
			response.setSuccess();
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setFailure();
			throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
		}
		return response;
	}

	@Override
	@Transactional
	public ConfirmLoanResponse confirmLoan(String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		ConfirmLoanRequest request = JSON.parseObject(decodeReqData, ConfirmLoanRequest.class);
		String requestNo = request.getRequestNo();

		ConfirmLoanResponse response = new ConfirmLoanResponse();
		response.setRequestNo(requestNo);

		List<ConfirmLoanDetailRequest> loanDetailList = request.getDetails();
		List<Tender> tenderList = new ArrayList<>();
		//获取投标记录
		for (ConfirmLoanDetailRequest loan : loanDetailList) {
			Tender tender = getByRequestNo(loan.getPreRequestNo());

			//判断投标信息是否存在
			if (tender == null) {
				log.warn("投标记录{}不存在", loan.getPreRequestNo());
				response.setResp(RemoteReturnCode.TENDER_NOT_EXIST);
				return response;
			}
			tenderList.add(tender);
		}

		try {
			//获取原始标的信息
			Project project = projectService.getByProjectNo(tenderList.get(0).getProjectNo());
			//获取借款人余额信息
			BalanceDetails loanBalanceDetails = balanceDetailsService.getByUserNo(project.getUserNo());
			BalanceDetails investBalanceDetails;
			for (Tender tender : tenderList) {
				//扣除投资人冻结金额
				investBalanceDetails = balanceDetailsService.getByUserNo(tender.getUserNo());
				investBalanceDetails.setAppCode(request.getAppCode());
				investBalanceDetails.setRequestContent(decodeReqData);
				balanceDetailsService.decreaseBalance(investBalanceDetails, tender.getAmount());

				//添加冻结金额到借款人账户
				loanBalanceDetails.setAppCode(request.getAppCode());
				loanBalanceDetails.setRequestContent(decodeReqData);
				balanceDetailsService.increaseBalance(loanBalanceDetails, tender.getAmount());

				//更新投标状态
				modifyTenderStatus(tender.getId(), TenderStatusCode.LOAN.getCode());
				response.setSuccess();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setFailure();
			throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
		}
		return response;
	}

	/**
	 * 根据请求流水号获取投标信息
	 * @param requestNo
	 * @return
	 */
	private Tender getByRequestNo(String requestNo) {
		return getOne(Wrappers.<Tender>lambdaQuery().eq(Tender::getRequestNo, requestNo), false);
	}

	/**
	 * 更新投标状态
	 * @param tenderStatus
	 * @return
	 */
	private Boolean modifyTenderStatus(Long id, String tenderStatus) {
		return update(Wrappers.<Tender>lambdaUpdate().eq(Tender::getId, id).set(Tender::getTenderStatus, tenderStatus));
	}

}
