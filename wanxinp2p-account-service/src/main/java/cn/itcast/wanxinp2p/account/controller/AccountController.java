package cn.itcast.wanxinp2p.account.controller;


import cn.itcast.wanxinp2p.account.AccountAPI;
import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.account.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.account.service.AccountService;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation("校验手机号和验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "key", value = "校验标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String")})
    @Override
    @GetMapping("/mobiles/{mobile}/key/{key}/code/{code}")
    public RestResponse<Integer> checkMobile(@PathVariable String mobile,
                                             @PathVariable String key,
                                             @PathVariable String code) {

        return RestResponse.success(accountService.checkMobile(mobile,key,code));
    }

    @Override
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "accountRegisterDTO", value = "账户注册信息", required = true,
            dataType = "AccountRegisterDTO", paramType = "body")
    @PostMapping(value = "/l/accounts")
    public RestResponse<AccountDTO> register(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        return RestResponse.success(accountService.register(accountRegisterDTO));
    }


    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "accountLoginDTO", value = "登录信息", required = true,
            dataType = "AccountLoginDTO", paramType = "body")
    @PostMapping(value = "/l/accounts/session")
    @Override
    public RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO) {
        return RestResponse.success(accountService.login(accountLoginDTO));
    }
}
