package cn.itcast.wanxinp2p.account;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountLoginDTO;
import cn.itcast.wanxinp2p.account.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;

/**
 * @author : xsh
 * @create : 2020-12-10 - 0:09
 * @describe:
 */
public interface AccountAPI {

    /**
     *  获取手机验证码
     *  @param mobile 手机号
     *  @return
     */
    RestResponse getSMSCode(String mobile);

    /**
     *  校验手机号和验证码
     *  @param mobile 手机号
     *  @param key 校验标识
     *  @param code 验证码
     *  @return
     */
    RestResponse<Integer> checkMobile(String mobile, String key, String code);

    /**
     * 注册
     */
    RestResponse<AccountDTO> register(AccountRegisterDTO accountRegisterDTO);

    /**
     * 登录
     */
    RestResponse<AccountDTO> login(AccountLoginDTO accountLoginDTO);
}
