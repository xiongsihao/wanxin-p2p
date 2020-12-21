package cn.itcast.wanxinp2p.consumer.service;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.BusinessException;
import cn.itcast.wanxinp2p.common.domain.CodePrefixCode;
import cn.itcast.wanxinp2p.common.domain.CommonErrorCode;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.consumer.agent.AccountApiAgent;
import cn.itcast.wanxinp2p.consumer.common.ConsumerErrorCode;
import cn.itcast.wanxinp2p.consumer.entity.Consumer;
import cn.itcast.wanxinp2p.consumer.mapper.ConsumerMapper;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xsh
 * @create : 2020-12-13 - 15:54
 * @describe:
 */
@Service
@Slf4j
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {


    @Autowired
    private AccountApiAgent accountApiAgent;

    /**
     * 检测用户是否存在
     *
     * @param mobile
     * @return
     */
    @Override
    public Integer checkMobile(String mobile) {
        return getByMobile(mobile) != null ? 1 : 0;
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
    @Hmily(confirmMethod = "confirmRegister", cancelMethod = "cancelRegister")
    public void register(ConsumerRegisterDTO consumerRegisterDTO) {
        if (checkMobile(consumerRegisterDTO.getMobile()) == 1) {
            throw new BusinessException(ConsumerErrorCode.E_140107);
        }

        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerRegisterDTO, consumer);
        //人工赋初值
        consumer.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumer.setIsBindCard(0);
        consumer.setUsername(CodeNoUtil.getNo(CodePrefixCode.CODE_NO_PREFIX));
        consumerRegisterDTO.setUsername(consumer.getUsername());
        save(consumer);

        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO();
        BeanUtils.copyProperties(consumerRegisterDTO, accountRegisterDTO);
        //远程调用account服务
        RestResponse<AccountDTO> restResponse = accountApiAgent.register(accountRegisterDTO);
        if (restResponse.getCode() != CommonErrorCode.SUCCESS.getCode()) {
            throw new BusinessException(ConsumerErrorCode.E_140106);
        }
    }

    public void confirmRegister(ConsumerRegisterDTO consumerRegisterDTO) {
        log.info("execute confirmRegister");
    }

    public void cancelRegister(ConsumerRegisterDTO consumerRegisterDTO) {
        log.info("execute cancelRegister");
        remove(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getMobile,
                consumerRegisterDTO.getMobile()));
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
