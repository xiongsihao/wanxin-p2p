package cn.itcast.wanxinp2p.consumer.agent;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : xsh
 * @create : 2020-12-14 - 23:48
 * @describe: 代理 调用其它微服务接口
 */
@FeignClient(value = "account-service")
public interface AccountApiAgent {

    /**
     * 注册
     */
    @Hmily
    @PostMapping(value = "/account/l/accounts")
    RestResponse<AccountDTO> register(@RequestBody AccountRegisterDTO accountRegisterDTO);
}
