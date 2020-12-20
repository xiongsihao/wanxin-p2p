package cn.itcast.wanxintx.hmilydemo.bank1.service;

import cn.itcast.wanxintx.hmilydemo.bank1.dao.AccountInfoDao;
import cn.itcast.wanxintx.hmilydemo.bank1.feignClient.Bank2Client;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoTccImpl implements AccountInfoTcc {

    @Autowired
    private AccountInfoDao accountInfoDao;

    @Autowired
    private Bank2Client bank2Client;

    @Override
    @Hmily(confirmMethod = "commit", cancelMethod = "rollback")
    public void prepare( String accountNo, double amount) {
        System.out.println("******** Bank1 Service  prepare...  " );
        if(!bank2Client.transfer(amount)){
            throw new RuntimeException("bank2 exception");
        }
    }

    @Override
    public void commit( String accountNo, double amount) {
        System.out.println("******** Bank1 Service commit..." );
    }

    @Override
    public void rollback( String accountNo, double amount) {
        accountInfoDao.updateAccountBalance(accountNo ,amount );
        System.out.println("******** Bank1 Service rollback...  " );
    }
}
