package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.BankUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 银行用户信息表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface BankUserService extends IService<BankUser> {

	/**
	 * 根据姓名和ID获取用户信息
	 * @param fullname 姓名
	 * @param idNumber 身份证号
	 * @return
	 */
	BankUser getUser(String fullname, String idNumber);

}
