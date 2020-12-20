package cn.itcast.wanxintx.hmilydemo.bank2.service;

import org.dromara.hmily.annotation.Hmily;

public interface AccountInfoService {
	@Hmily
	Boolean updateAccountBalance(String accountNo, Double amount);
}
