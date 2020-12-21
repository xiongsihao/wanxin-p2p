package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.DepositoryBankCard;
import cn.itcast.wanxindepository.entity.User;
import cn.itcast.wanxindepository.model.PersonalRegisterRequest;
import cn.itcast.wanxindepository.model.PersonalRegisterResponse;
import cn.itcast.wanxindepository.model.UserDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存管用户信息表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface UserService extends IService<User> {

	/**
	 * 用户绑卡注册
	 * @param personalRegisterRequest
	 * @return
	 */
	PersonalRegisterResponse createUser(PersonalRegisterRequest personalRegisterRequest);

	/**
	 * 根据用户编码获取绑定银行卡信息
	 * @param userNo
	 * @return
	 */
	DepositoryBankCard getDepositoryBankCardByUserNo(String userNo);

	/**
	 * 校验用户交易密码
	 * @param userNo
	 * @param password
	 * @return
	 */
	Boolean verifyPassword(String userNo, String password);

}
