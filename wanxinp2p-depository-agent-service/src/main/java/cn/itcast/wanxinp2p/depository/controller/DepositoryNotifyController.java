package cn.itcast.wanxinp2p.depository.controller;

import cn.itcast.wanxinp2p.common.util.EncryptUtil;
import cn.itcast.wanxinp2p.depository.message.GatewayMessageProducer;
import cn.itcast.wanxinp2p.depository.model.DepositoryConsumerResponse;
import cn.itcast.wanxinp2p.depository.service.DepositoryRecordService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xsh
 * @create : 2021-01-06 - 3:18
 * @describe:
 */
@Api(value = "银行存管系统通知服务", tags = "depository-agent")
@RestController
public class DepositoryNotifyController {

    @Autowired
    private DepositoryRecordService depositoryRecordService;

    @Autowired
    private GatewayMessageProducer gatewayMessageProducer;


    @ApiOperation("接受银行存管系统开户回调结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", value = "请求的存管接口名",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", value = "平台编号，平台与存管系统签约时获取",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "对reqData参数的签名",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", value = "业务数据报文，json格式",
                    required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/gateway", method = RequestMethod.GET,params = "serviceName=PERSONAL_REGISTER")
    public String receiveDepositoryCreateConsumerResult(@RequestParam("serviceName") String serviceName,
                                                        @RequestParam("platformNo") String platformNo,
                                                        @RequestParam("signature") String signature,
                                                        @RequestParam("reqData") String reqData){
        //1.更新数据
        DepositoryConsumerResponse depositoryConsumerResponse= JSON.parseObject(EncryptUtil.decodeUTF8StringBase64(reqData),DepositoryConsumerResponse.class);
        depositoryRecordService.modifyRequestStatus(depositoryConsumerResponse.getRequestNo(),depositoryConsumerResponse.getStatus());

        //2.发送消息给用户中心
        gatewayMessageProducer.personalRegister(depositoryConsumerResponse);

        //3.返回结果
        return "OK";
    }
}
