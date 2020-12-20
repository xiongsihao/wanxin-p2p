package cn.itcast.wanxintx.hmilydemo.bank2.controller;

import cn.itcast.wanxintx.hmilydemo.bank2.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Bank2Controller {
	@Autowired
    AccountInfoService accountInfoService;
	
   @RequestMapping("/transfer")
    public Boolean transfer(@RequestParam("amount") Double amount) {
    	this.accountInfoService.updateAccountBalance("2", amount);
        return true;
    }
	   
}
