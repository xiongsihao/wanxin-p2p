package cn.itcast.wanxindepository.controller;

import cn.itcast.wanxindepository.common.util.CommonUtil;
import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.entity.BankCard;
import cn.itcast.wanxindepository.entity.DepositoryBankCard;
import cn.itcast.wanxindepository.model.PersonalRegisterRequest;
import cn.itcast.wanxindepository.model.RechargeRequest;
import cn.itcast.wanxindepository.model.WithdrawRequest;
import cn.itcast.wanxindepository.service.BankCardService;
import cn.itcast.wanxindepository.service.UserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 网关接口
 */
@Slf4j
@Controller
@Api(value = "网关接口", tags = "Gateway", description = "网关接口API")
public class GatewayController {

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private UserService userService;

	@ApiOperation("开户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=PERSONAL_REGISTER")
	public ModelAndView create(@RequestParam("serviceName") String serviceName,
			@RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
			@RequestParam("reqData") String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		PersonalRegisterRequest registerRequest = JSON.parseObject(decodeReqData, PersonalRegisterRequest.class);
		registerRequest.setAppCode(platformNo);
		registerRequest.setRole("B");
		//获取银行卡信息
		BankCard bankCard = bankCardService.getByCardNumber(registerRequest.getCardNumber());
		if (bankCard != null) {
			registerRequest.setBankName(bankCard.getBankName());
			registerRequest.setBankCode(bankCard.getBankCode());
		}
		log.debug("开户数据：{}", JSON.toJSONString(registerRequest));

		ModelAndView modelAndView = new ModelAndView("create");
		modelAndView.addObject("consumer", registerRequest);
		return modelAndView;
	}

	@ApiOperation("充值")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=RECHARGE")
	public ModelAndView recharge(@RequestParam("serviceName") String serviceName,
			@RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
			@RequestParam("reqData") String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		RechargeRequest rechargeRequest = JSON.parseObject(decodeReqData, RechargeRequest.class);
		rechargeRequest.setAppCode(platformNo);

		//获取存管系统绑定银行卡信息
		DepositoryBankCard bankCard = userService.getDepositoryBankCardByUserNo(rechargeRequest.getUserNo());
		rechargeRequest.setBankCode(bankCard.getBankCode());
		rechargeRequest.setBankName(bankCard.getBankName());
		rechargeRequest.setCardNumber(bankCard.getCardNumber());
		rechargeRequest.setMobile(bankCard.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		rechargeRequest.setMobile(CommonUtil.hiddenMobile(bankCard.getMobile()));
		log.debug("充值数据：{}", JSON.toJSONString(rechargeRequest));

		ModelAndView modelAndView = new ModelAndView("recharge");
		modelAndView.addObject("recharge", rechargeRequest);
		return modelAndView;
	}

	@ApiOperation("提现")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceName", value = "接口名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "platformNo", value = "接入平台编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "signature", value = "针对请求数据reqData的签名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "reqData", value = "业务数据报文，JSON 格式", required = true, dataType = "String", paramType = "query"),})
	@RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=WITHDRAW")
	public ModelAndView withdraw(@RequestParam("serviceName") String serviceName,
			@RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
			@RequestParam("reqData") String reqData) {

		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		WithdrawRequest withdrawRequest = JSON.parseObject(decodeReqData, WithdrawRequest.class);
		withdrawRequest.setAppCode(platformNo);

		//获取存管系统绑定银行卡信息
		DepositoryBankCard bankCard = userService.getDepositoryBankCardByUserNo(withdrawRequest.getUserNo());
		withdrawRequest.setBankCode(bankCard.getBankCode());
		withdrawRequest.setBankName(bankCard.getBankName());
		withdrawRequest.setCardNumber(bankCard.getCardNumber());
		withdrawRequest.setMobile(bankCard.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

		log.debug("提现数据：{}", JSON.toJSONString(withdrawRequest));

		ModelAndView modelAndView = new ModelAndView("withdraw");
		modelAndView.addObject("withdraw", withdrawRequest);
		return modelAndView;
	}

}
