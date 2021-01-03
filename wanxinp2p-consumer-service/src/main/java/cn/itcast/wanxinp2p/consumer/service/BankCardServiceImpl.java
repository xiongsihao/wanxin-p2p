package cn.itcast.wanxinp2p.consumer.service;

import cn.itcast.wanxinp2p.consumer.model.BankCardDTO;
import cn.itcast.wanxinp2p.consumer.entity.BankCard;
import cn.itcast.wanxinp2p.consumer.mapper.BankCardMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard> implements BankCardService {
    @Override
    public BankCardDTO getByConsumerId(Long consumerId) {
        BankCard bankCard=getOne(new QueryWrapper<BankCard>().lambda().eq(BankCard::getConsumerId,consumerId));
        return convertBankCardEntityToDTO(bankCard);
    }

    @Override
    public BankCardDTO getByCardNumber(String cardNumber) {
        BankCard bankCard=getOne(new QueryWrapper<BankCard>().lambda().eq(BankCard::getCardNumber,cardNumber));
        return convertBankCardEntityToDTO(bankCard);
    }

    private BankCardDTO convertBankCardEntityToDTO(BankCard bankCard){
       if(bankCard==null){
           return null;
       }
        BankCardDTO bankCardDTO=new BankCardDTO();
        BeanUtils.copyProperties(bankCard,bankCardDTO);
        return bankCardDTO;
    }
}
