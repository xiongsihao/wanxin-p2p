package cn.itcast.wanxindepository.mapper;

import cn.itcast.wanxindepository.entity.BankCardDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 银行卡明细 Mapper 接口
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Repository
public interface BankCardDetailsMapper extends BaseMapper<BankCardDetails> {
	/**
	 * 根据银行卡ID获取余额
	 * @param bankCardId
	 * @return
	 */
	BankCardDetails selectByBankCardId(Long bankCardId);
}
