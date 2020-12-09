package cn.xsh.sailing.controller;


import cn.xsh.sailing.common.domain.RestResponse;
import cn.xsh.sailing.dto.VerificationInfo;
import cn.xsh.sailing.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "验证码服务接口")
@RestController
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @ApiOperation(value="生成验证信息", notes="生成验证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "业务名称", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "payload", value = "业务携带参数，如手机号 ，邮箱", required = true, paramType="body"),
            @ApiImplicitParam(name = "effectiveTime", value = "验证信息有效期(秒)", required = false, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/generate")
    public RestResponse<VerificationInfo> generateVerificationInfo(@RequestParam("name")String name,
                                                                   @RequestBody Map<String,Object> payload,
                                                                   @RequestParam("effectiveTime")int effectiveTime){
        VerificationInfo verificationInfo = verificationService.generateVerificationInfo(name, payload, effectiveTime);
        return RestResponse.success(verificationInfo);
    }


    @ApiOperation(value="校验", notes="校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "业务名称", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "verificationKey", value = "验证key", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/verify")
    public RestResponse<Boolean> verify(String name, String verificationKey, String verificationCode){
        Boolean isSuccess = verificationService.verify(name,verificationKey,verificationCode);
        return RestResponse.success(isSuccess);
    }


}
