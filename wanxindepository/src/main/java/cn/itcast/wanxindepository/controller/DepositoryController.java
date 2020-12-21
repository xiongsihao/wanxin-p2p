package cn.itcast.wanxindepository.controller;

import cn.itcast.wanxindepository.common.constant.AuditStatusCode;
import cn.itcast.wanxindepository.common.constant.TransactionStatusCode;
import cn.itcast.wanxindepository.common.domain.BusinessException;
import cn.itcast.wanxindepository.common.domain.PageVO;
import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.model.*;
import cn.itcast.wanxindepository.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * <p>
 * 银行存管系统API
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Slf4j
@RestController
@Api(value = "银行存管系统API", tags = "Depository", description = "银行存管系统API")
public class DepositoryController {

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private UserService userService;

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private RechargeDetailsService rechargeDetailsService;

	@Autowired
	private WithdrawDetailsService withdrawDetailsService;


	@ApiOperation("获取手机验证码")
	@ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String")
	@GetMapping("/smscode/{mobile}")
	public LocalResponse<String> getSMSCode(@PathVariable String mobile) {
		return LocalResponse.success();
	}

	@ApiOperation("银行卡开户")
	@ApiImplicitParam(name = "bankCardRequest", value = "银行卡信息", required = true, dataType = "BankCardDTO", paramType = "body")
	@PostMapping("/bank-cards")
	public LocalResponse<String> createBankCard(@RequestBody BankCardRequest bankCardRequest) {
		bankCardService.createBankCard(bankCardRequest);
		return LocalResponse.success();
	}

	@ApiOperation("查询银行卡余额")
	@ApiImplicitParam(name = "cardNumber", value = "卡号", required = true, dataType = "String")
	@GetMapping("/bank-cards/card-number/{cardNumber}")
	public LocalResponse<BigDecimal> getBalance(@PathVariable String cardNumber) {
		return LocalResponse.success(bankCardService.getBalance(cardNumber));
	}

	@ApiOperation("获取用户余额信息")
	@ApiImplicitParam(name = "userNo", value = "用户编码", required = true, dataType = "String")
	@GetMapping("/balance-details/{userNo}")
	public BalanceDetailsDTO getP2PBalanceDetails(@PathVariable String userNo) {
		BalanceDetailsDTO balanceDetailsDTO = balanceDetailsService.getP2PBalanceDetails(userNo);
		return balanceDetailsDTO;
	}

	@ApiOperation("检索银行卡记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bankCardQuery", value = "银行卡检索参数", dataType = "WithdrawRecordQueryDTO", paramType = "body"),
			@ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "sortBy", value = "排序字段", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "顺序", dataType = "String", paramType = "query")})
	@PostMapping("/bank-cards/q")
	public LocalResponse<PageVO<BankCardDTO>> queryBankCards(@RequestBody BankCardQuery bankCardQuery,
			@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy,
			@RequestParam String order) {
		PageVO<BankCardDTO> pageVO = bankCardService.queryBankCards(bankCardQuery, pageNo, pageSize, sortBy, order);
		return LocalResponse.success(pageVO);
	}


	@ApiOperation("获取用户余额信息")
	@ApiImplicitParam(name = "userDTO", value = "用户信息", required = true, dataType = "UserDTO", paramType = "body")
	@PostMapping("/users/password")
	public LocalResponse<Integer> verifyPassword(@RequestBody UserDTO userDTO) {
		return LocalResponse.success(userService.verifyPassword(userDTO.getUserNo(), userDTO.getPassword()) ? 1 : 0);
	}

	@ApiOperation("用户开户")
	@ApiImplicitParam(name = "personalRegisterRequest", value = "开户信息", required = true, dataType = "PersonalRegisterRequest", paramType = "form")
	@PostMapping(value = "/trans/users")
	public ModelAndView createUser(PersonalRegisterRequest personalRegisterRequest) {
		int code;
		String msg = "";
		try {
			PersonalRegisterResponse response = userService.createUser(personalRegisterRequest);
			code = response.getAuditStatus().equals(AuditStatusCode.PASSED.getCode()) ? 0 : 1;
		} catch (BusinessException e) {
			code = 2;
			msg = e.getMessage();
		}
		return new ModelAndView(
				"redirect:" + addRedirectAttributes(personalRegisterRequest.getCallbackUrl(), code, msg));
	}

	@ApiOperation("用户充值")
	@ApiImplicitParam(name = "rechargeRequest", value = "充值信息", required = true, dataType = "RechargeRequest", paramType = "form")
	@PostMapping(value = "/trans/recharge-details")
	public ModelAndView recharge(RechargeRequest rechargeRequest) {
		int code;
		String msg = "";
		try {
			RechargeResponse response = rechargeDetailsService.recharge(rechargeRequest);
			code = response.getTransactionStatus().equals(TransactionStatusCode.SUCCESS.getCode()) ? 0 : 1;
		} catch (BusinessException e) {
			code = 2;
			msg = e.getMessage();
		}
		return new ModelAndView("redirect:" + addRedirectAttributes(rechargeRequest.getCallbackUrl(), code, msg));
	}

	@ApiOperation("用户提现")
	@ApiImplicitParam(name = "withdrawRequest", value = "提现信息", required = true, dataType = "WithdrawRequest", paramType = "form")
	@PostMapping(value = "/trans/withdraw-details")
	public ModelAndView withdraw(WithdrawRequest withdrawRequest) {
		int code;
		String msg = "";
		try {
			WithdrawResponse response = withdrawDetailsService.withDraw(withdrawRequest);
			code = response.getTransactionStatus().equals(TransactionStatusCode.SUCCESS.getCode()) ? 0 : 1;
		} catch (BusinessException e) {
			code = 2;
			msg = e.getMessage();
		}
		return new ModelAndView("redirect:" + addRedirectAttributes(withdrawRequest.getCallbackUrl(), code, msg));
	}

	/**
	 * 添加url重定向参数
	 * @param url
	 * @param code
	 * @param msg
	 * @return
	 */
	private String addRedirectAttributes(String url, int code, String msg) {
		StringBuilder callbackUrl = new StringBuilder(url);
		callbackUrl.append("&code=").append(code);
		if (StringUtils.isNotBlank(msg)) {
			callbackUrl.append("&msg=").append(EncryptUtil.encodeURL(msg));
		}
		return callbackUrl.toString();
	}

}
