package cn.itcast.wanxintx.hmilydemo.bank1.service;

import cn.itcast.wanxintx.hmilydemo.bank1.dao.AccountInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {


	@Autowired
	private AccountInfoDao accountInfoDao;

	@Autowired
	private AccountInfoTcc  accountInfoTcc;


	@Override
	public void updateAccountBalance(String accountNo, Double amount) {
		System.out.println("******** Bank1 Service Begin ... " );
		accountInfoDao.updateAccountBalance(accountNo ,amount* -1);//转出
		accountInfoTcc.prepare(accountNo,amount);
	}
	


}
