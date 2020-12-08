package cn.itcast.wanxinp2p.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xsh
 * @create : 2020-12-09 - 0:37
 * @describe:
 */
@RestController
@Api(value = "统一账户服务的api")
public class AccountController {


    @ApiOperation("测试")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
