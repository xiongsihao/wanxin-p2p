package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.WanxindepositoryServer;
import cn.itcast.wanxindepository.bank.BankCardNumberGenerator;
import cn.itcast.wanxindepository.bank.ChineseIDCardNumberGenerator;
import cn.itcast.wanxindepository.bank.ChineseMobileNumberGenerator;
import cn.itcast.wanxindepository.bank.ChineseNameGenerator;
import cn.itcast.wanxindepository.entity.BankCard;
import cn.itcast.wanxindepository.model.BankCardRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WanxindepositoryServer.class})
public class BankCardServiceTest {

	@Autowired
	private BankCardService bankCardService;

	@Test
	public void testAddBankCard() {
		BankCardRequest bankCardRequest = new BankCardRequest();

		for (int i = 0; i < 100; i++) {
			bankCardRequest.setFullname(ChineseNameGenerator.getInstance().generate());
			bankCardRequest.setIdNumber(ChineseIDCardNumberGenerator.getInstance().generate());
			bankCardRequest.setMobile(ChineseMobileNumberGenerator.getInstance().generate());
			bankCardRequest.setBalance(new BigDecimal(100000));
			BankCard bankCard = BankCardNumberGenerator.getInstance().generateAll();
			BeanUtils.copyProperties(bankCard, bankCardRequest);
			bankCardRequest.setPassword("888888");
			bankCardRequest.setUserType(1);
			System.out.println(bankCardRequest);
			bankCardService.createBankCard(bankCardRequest);
		}
	}


}
