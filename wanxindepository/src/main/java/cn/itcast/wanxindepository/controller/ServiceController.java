package cn.itcast.wanxindepository.controller;


import cn.itcast.wanxindepository.common.constant.PreTransactionCode;
import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.model.*;
import cn.itcast.wanxindepository.service.BalanceDetailsService;
import cn.itcast.wanxindepository.service.ProjectService;
import cn.itcast.wanxindepository.service.TenderService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直连接口
 */
@RestController
@Api(value = "银行存管系统直连接口", tags = "Service", description = "银行存管系统直连接口API")
public class ServiceController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TenderService tenderService;

	@Autowired
	private BalanceDetailsService balanceDetailsService;


	@ApiOperation("创建标的")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@GetMapping(value = "/service", params = "serviceName=CREATE_PROJECT")
	public DepositoryResponse<CreateProjectResponse> createProject(@RequestParam String serviceName,
			@RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
		CreateProjectResponse projectResponse = projectService.createProject(reqData);
		return new DepositoryResponse<>(projectResponse);
	}


	@ApiOperation("更改标的状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@GetMapping(value = "/service", params = "serviceName=MODIFY_PROJECT")
	public DepositoryResponse<ModifyProjectResponse> modifyProject(@RequestParam String serviceName,
			@RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
		ModifyProjectResponse projectResponse = projectService.modifyProject(reqData);
		return new DepositoryResponse<>(projectResponse);
	}

	@ApiOperation("预授权处理")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@GetMapping(value = "/service", params = "serviceName=USER_AUTO_PRE_TRANSACTION")
	public DepositoryResponse<UserAutoPreTransactionResponse> authorizeAutoPreTransaction(
			@RequestParam String serviceName, @RequestParam String platformNo, @RequestParam String signature,
			@RequestParam String reqData) {
		UserAutoPreTransactionResponse transactionResponse = new UserAutoPreTransactionResponse();

		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		UserAutoPreTransactionRequest request;
			request = JSON.parseObject(decodeReqData, UserAutoPreTransactionRequest.class);
			request.setAppCode(platformNo);

		//判断bizType
		if (PreTransactionCode.TENDER.getCode().equals(request.getBizType())) {
			//用户投标
			transactionResponse = tenderService.autoPreTransactionForTender(request);
		} else if (PreTransactionCode.REPAYMENT.getCode().equals(request.getBizType())) {
			//还款确认冻结金额
			balanceDetailsService.autoPreTransactionForRepayment(request);
		}
		return new DepositoryResponse<>(transactionResponse);
	}

	@ApiOperation(value = "预处理取消", notes = "取消已冻结的预处理金额")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "requestNo", value = "请求流水号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "preRequestNo", value = "预处理业务流水号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "amount", value = "取消金额", required = true, dataType = "String", paramType = "query")})
	@GetMapping("/l/cancel-pre-freeze")
	public DepositoryResponse<String> cancelPreFreeze(@RequestParam String requestNo, @RequestParam String preRequestNo,
			@RequestParam String amount) {
		return null;
	}

	@ApiOperation(value = "放款确认", notes = "标的满标放款确认")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@GetMapping(value = "/service", params = "serviceName=CONFIRM_LOAN")
	public DepositoryResponse<ConfirmLoanResponse> confirmLoan(@RequestParam String serviceName,
			@RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
		ConfirmLoanResponse confirmLoanResponse = tenderService.confirmLoan(reqData);
		return new DepositoryResponse<>(confirmLoanResponse);
	}

	@ApiOperation(value = "还款确认", notes = "将还款预处理冻结的资金划转至投资人账户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@GetMapping(value = "/service", params = "serviceName=CONFIRM_REPAYMENT")
	public DepositoryResponse<ConfirmRepaymentResponse> confirmRepayment(@RequestParam String serviceName,
			@RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
		ConfirmRepaymentResponse repaymentResponse = new ConfirmRepaymentResponse();
		return new DepositoryResponse<>(repaymentResponse);
	}


}
