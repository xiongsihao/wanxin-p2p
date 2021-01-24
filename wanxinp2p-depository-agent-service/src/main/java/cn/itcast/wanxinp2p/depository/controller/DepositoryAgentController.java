package cn.itcast.wanxinp2p.depository.controller;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.DepositoryAgentApi;
import cn.itcast.wanxinp2p.depository.GatewayRequest;
import cn.itcast.wanxinp2p.depository.model.DepositoryBaseResponse;
import cn.itcast.wanxinp2p.depository.model.DepositoryResponseDTO;
import cn.itcast.wanxinp2p.depository.service.DepositoryRecordService;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xsh
 * @create : 2021-01-03 - 22:14
 * @describe: 存管代理服务
 */
@Api(value = "存管代理服务", tags = "depository-agent")
@RestController
public class DepositoryAgentController implements DepositoryAgentApi {

    @Autowired
    private DepositoryRecordService depositoryRecordService;

    @Override
    @ApiOperation("生成开户请求数据")
    @ApiImplicitParam(name = "consumerRequest", value = "开户信息", required = true,
            dataType = "ConsumerRequest", paramType = "body")
    @PostMapping("/l/consumers")
    public RestResponse<GatewayRequest> createConsumer(@RequestBody ConsumerRequest consumerRequest) {
        return RestResponse.success(depositoryRecordService.createConsumer(consumerRequest));
    }

    /**
     * 向银行存管系统发送标的信息
     *
     * @param projectDTO
     * @return
     */
    @Override
    @ApiOperation(value = "向存管系统发送标的信息")
    @ApiImplicitParam(name = "projectDTO", value = "向存管系统发送标的信息", required = true, dataType = "ProjectDTO", paramType = "body")
    @PostMapping("/l/createProject")
    public RestResponse<String> createProject(@RequestBody ProjectDTO projectDTO) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse = depositoryRecordService.createProject(projectDTO);
        RestResponse<String> restResponse = new RestResponse<String>();
        restResponse.setResult(depositoryResponse.getRespData().getRespCode());
        restResponse.setMsg(depositoryResponse.getRespData().getRespMsg());
        return restResponse;
    }
}
