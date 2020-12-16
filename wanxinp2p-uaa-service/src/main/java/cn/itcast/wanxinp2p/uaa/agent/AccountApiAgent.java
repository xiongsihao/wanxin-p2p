package cn.itcast.wanxinp2p.uaa.agent;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : xsh
 * @create : 2020-12-17 - 0:50
 * @describe:
 */
@FeignClient(value = "account-service")
public interface AccountApiAgent {


    @PostMapping(value = "/account/l/accounts/session")
    public RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO);
}
