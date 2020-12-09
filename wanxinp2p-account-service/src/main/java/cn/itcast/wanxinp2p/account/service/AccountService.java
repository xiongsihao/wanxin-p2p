package cn.itcast.wanxinp2p.account.service;

import cn.itcast.wanxinp2p.common.domain.RestResponse;


public interface AccountService {

    RestResponse getSMSCode(String mobile) ;

}
