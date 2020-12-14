package cn.itcast.wanxinp2p.consumer.service;

import cn.itcast.wanxinp2p.common.domain.BusinessException;
import cn.itcast.wanxinp2p.common.domain.CodePrefixCode;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.consumer.common.ConsumerErrorCode;
import cn.itcast.wanxinp2p.consumer.entity.Consumer;
import cn.itcast.wanxinp2p.consumer.mapper.ConsumerMapper;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;

/**
 * @author : xsh
 * @create : 2020-12-13 - 15:54
 * @describe:
 */
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {


    /**
     * 检测用户是否存在
     *
     * @param mobile
     * @return
     */
    @Override
    public Integer checkMobile(String mobile) {
        return getByMobile(mobile)!=null?1:0;
    }

    private ConsumerDTO getByMobile(String mobile) {
        Consumer entity = getOne(new QueryWrapper<Consumer>().lambda().eq(Consumer::getMobile, mobile));
        return convertConsumerEntityToDTO(entity);
    }

    /**
     * 用户注册
     *
     * @param consumerRegisterDTO
     */
    @Override
    public void register(ConsumerRegisterDTO consumerRegisterDTO) {
        if(checkMobile(consumerRegisterDTO.getMobile())==1){
            throw new BusinessException(ConsumerErrorCode.E_140107);
        }

        Consumer consumer=new Consumer();
        BeanUtils.copyProperties(consumerRegisterDTO, consumer);
        //人工赋初值
        consumer.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumer.setIsBindCard(0);
        consumer.setUsername(CodeNoUtil.getNo(CodePrefixCode.CODE_NO_PREFIX));
        save(consumer);
    }

    /**
     * entity转为dto
     *
     * @param entity
     * @return
     */
    private ConsumerDTO convertConsumerEntityToDTO(Consumer entity) {
        if (entity == null) {
            return null;
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
