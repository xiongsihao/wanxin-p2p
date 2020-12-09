package cn.itcast.wanxinp2p.account;

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
}
