package cn.itcast.wanxinp2p.account.service;


import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.util.OkHttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${sms.url}")
    private String smsURL;

    @Value("${sms.enable}")
    private Boolean smsEnable;


    public RestResponse getSMSCode(String mobile) {
        if (smsEnable) {
            return OkHttpUtil.post(smsURL + "/generate?effectiveTime=300&name=sms", "{\"mobile\":" + mobile + "}");

        }
        return RestResponse.success();
    }
}
