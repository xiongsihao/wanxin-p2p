package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.BankUser;
import cn.itcast.wanxindepository.mapper.BankUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 银行用户信息表 服务实现类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Slf4j
@Service
public class BankUserServiceImpl extends ServiceImpl<BankUserMapper, BankUser> implements BankUserService {

	@Override
	public BankUser getUser(String fullname, String idNumber) {
		return getOne(new QueryWrapper<BankUser>().lambda().eq(BankUser::getFullname, fullname)
				.eq(BankUser::getIdNumber, idNumber), false);
	}
}
