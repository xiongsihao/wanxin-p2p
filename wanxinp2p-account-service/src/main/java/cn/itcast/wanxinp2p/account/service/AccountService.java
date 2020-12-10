package cn.itcast.wanxinp2p.account.service;

import cn.itcast.wanxinp2p.account.entity.Account;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AccountService extends IService<Account > {

    RestResponse getSMSCode(String mobile) ;

    /**
     *  校验手机号和验证码
     *  @param mobile 手机号
     *  @param key 校验标识
     *  @param code 验证码
     *  @return
     */
    Integer checkMobile(String mobile, String key, String code);
}
