package cn.itcast.wanxintx.hmilydemo.bank1.controller;

import cn.itcast.wanxintx.hmilydemo.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Bank1Controller {
	@Autowired
    AccountInfoService accountInfoService;
	
   @RequestMapping("/transfer")
    public String test(@RequestParam("amount") Double amount) {
    	this.accountInfoService.updateAccountBalance("1", amount);
        return "bank1向bank2转账:" + amount;
    }
   
}
