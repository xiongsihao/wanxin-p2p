package cn.itcast.wanxinp2p.transaction.agent;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author : xsh
 * @create : 2021-01-25 - 0:55
 * @describe:
 */
@FeignClient(value = "depository-agent-service")
public interface DepositoryAgentApiAgent {
    @PostMapping(value = "/depository-agent/l/createProject")
    public RestResponse<String> createProject(ProjectDTO projectDTO);
}
