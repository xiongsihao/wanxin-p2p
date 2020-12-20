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
			//TODO 成功案例需要把以下这行去掉
			accountInfoDao=null; //测试使用，模拟事务回滚 失败情况
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
