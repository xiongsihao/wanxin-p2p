package cn.itcast.wanxintx.hmilydemo.bank2.service;

import cn.itcast.wanxintx.hmilydemo.bank2.dao.AccountInfoDao;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
	@Autowired
	private AccountInfoDao accountInfoDao;

	@Override
	@Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
	public Boolean updateAccountBalance(String accountNo, Double amount) {
		System.out.println("******** Bank2 Service Begin ...");
		try{
			accountInfoDao=null; //测试使用
			accountInfoDao.updateAccountBalance(accountNo ,amount);
		}catch(Exception e){
			throw new RuntimeException( e.getMessage() );
		}
		return true;
	}
	public Boolean confirmMethod(String accountNo, Double amount) {
		System.out.println("******** Bank2 Service commit...  " );
		return true;
	}

	public Boolean cancelMethod(String accountNo, Double amount) {
		accountInfoDao.updateAccountBalance(accountNo ,amount * -1);
		System.out.println("******** Bank2 Service rollback...  " );
		return true;
	}
}
