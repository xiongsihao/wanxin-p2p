package cn.itcast.wanxinp2p.account.controller;


import cn.itcast.wanxinp2p.account.AccountAPI;
import cn.itcast.wanxinp2p.account.service.AccountService;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "统一账户服务的API")
public class AccountController implements AccountAPI {

    @Autowired
    private AccountService accountService;

    @ApiOperation("测试")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    @Override
    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name="mobile",value ="手机号",dataType = "String")
    @GetMapping("/sms/{mobile}")
    public RestResponse getSMSCode(@PathVariable String mobile) {
        return accountService.getSMSCode(mobile);
    }


}
