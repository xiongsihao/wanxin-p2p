package cn.itcast.wanxindepository.mapper;

import cn.itcast.wanxindepository.entity.BankCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 银行用户银行卡信息 Mapper 接口
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Repository
public interface BankCardMapper extends BaseMapper<BankCard> {

//	Page<BankCard> queryBankCards(Page page, @Param(value = "card") BankCard bankCard);

}
