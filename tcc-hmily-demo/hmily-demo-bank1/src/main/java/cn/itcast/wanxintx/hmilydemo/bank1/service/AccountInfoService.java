package cn.itcast.wanxintx.hmilydemo.bank1.service;

public interface AccountInfoService {
	//更新帐号余额
	void updateAccountBalance(String accountNo, Double amount);
}
