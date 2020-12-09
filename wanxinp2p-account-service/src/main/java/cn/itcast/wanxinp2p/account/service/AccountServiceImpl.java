package cn.itcast.wanxinp2p.account.service;


import cn.itcast.wanxinp2p.common.domain.RestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private SmsService smsService;

    @Value("${sms.enable}")
    private Boolean smsEnable;

    @Override
    public RestResponse getSMSCode(String mobile) {
        return smsService.getSMSCode(mobile);
    }


}
