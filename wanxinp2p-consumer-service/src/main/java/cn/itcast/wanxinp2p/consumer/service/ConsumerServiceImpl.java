package cn.itcast.wanxinp2p.consumer.service;

import cn.itcast.wanxinp2p.account.model.AccountDTO;
import cn.itcast.wanxinp2p.account.model.AccountRegisterDTO;
import cn.itcast.wanxinp2p.common.domain.*;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.consumer.agent.AccountApiAgent;
import cn.itcast.wanxinp2p.consumer.agent.DepositoryAgentApiAgent;
import cn.itcast.wanxinp2p.consumer.common.ConsumerErrorCode;
import cn.itcast.wanxinp2p.consumer.entity.BankCard;
import cn.itcast.wanxinp2p.consumer.entity.Consumer;
import cn.itcast.wanxinp2p.consumer.mapper.ConsumerMapper;
import cn.itcast.wanxinp2p.consumer.model.BankCardDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRegisterDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerRequest;
import cn.itcast.wanxinp2p.depository.GatewayRequest;
import cn.itcast.wanxinp2p.depository.model.DepositoryConsumerResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;

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

    /**
     * 生成开户数据
     *
     * @param consumerRequest
     * @return
     */
    @Override
    @Transactional
    public RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest) {
        //1. 判断当前用户是否已经开户
        ConsumerDTO consumerDTO = getByMobile(consumerRequest.getMobile());
        if (consumerDTO == null) {
            throw new BusinessException(ConsumerErrorCode.E_140109);
        }

        if (consumerDTO.getIsBindCard() == 1) {
            throw new BusinessException(ConsumerErrorCode.E_140105);
        }

        //2.判断提交过来的银行卡是否已被绑定
        BankCardDTO bankCardDTO = bankCardService.getByCardNumber(consumerRequest.getCardNumber());
        if (bankCardDTO != null && bankCardDTO.getStatus() == StatusCode.STATUS_IN.getCode()) {
            throw new BusinessException(ConsumerErrorCode.E_140151);
        }

        //3. 更新用户的信息
        consumerRequest.setId(consumerDTO.getId());
        //产生请求流水号和用户编号
        consumerRequest.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumerRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        //设置查询条件和需要更新的数据
        UpdateWrapper<Consumer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Consumer::getMobile, consumerDTO.getMobile());
        updateWrapper.lambda().set(Consumer::getUserNo, consumerRequest.getUserNo());
        updateWrapper.lambda().set(Consumer::getRequestNo, consumerRequest.getRequestNo());
        updateWrapper.lambda().set(Consumer::getFullname, consumerRequest.getFullname());
        updateWrapper.lambda().set(Consumer::getIdNumber, consumerRequest.getIdNumber());
        updateWrapper.lambda().set(Consumer::getAuthList, "ALL");
        update(updateWrapper);

        //4. 保存银行卡信息
        BankCard bankCard = new BankCard();
        bankCard.setConsumerId(consumerDTO.getId());
        bankCard.setBankCode(consumerRequest.getBankCode());
        bankCard.setCardNumber(consumerRequest.getCardNumber());
        bankCard.setMobile(consumerRequest.getMobile());
        bankCard.setStatus(StatusCode.STATUS_OUT.getCode());
        BankCardDTO existBankCard = bankCardService.getByConsumerId(bankCard.getConsumerId());
        if (existBankCard != null) {
            bankCard.setId(existBankCard.getId());
        }
        bankCardService.saveOrUpdate(bankCard);

        //5. 准备数据，发起远程调用 把数据发到存管代理服务
        return depositoryAgentApiAgent.createConsumer(consumerRequest);
    }

    /**
     * 更新开户结果
     *
     * @param response
     * @return
     */
    @Override
    @Transactional
    public Boolean modifyResult(DepositoryConsumerResponse response) {
        //1.获取数据（状态）
        int status = response.getRespCode().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode()) ? StatusCode.STATUS_IN.getCode() : StatusCode.STATUS_FAIL.getCode();
        //2.更新用户信息
        Consumer consumer = getByRequestNo(response.getRequestNo());
        update(Wrappers.<Consumer>lambdaUpdate().eq(Consumer::getId, consumer.getId()).set(Consumer::getIsBindCard, status).set(Consumer::getStatus, status));
        //3.更新银行卡信息
        return bankCardService.update(Wrappers.<BankCard>lambdaUpdate()
                .eq(BankCard::getConsumerId, consumer.getId())
                .set(BankCard::getStatus, status).set(BankCard::getBankCode, response.getBankCode())
                .set(BankCard::getBankName, response.getBankName()));

    }

    private Consumer getByRequestNo(String requestNo) {
        return getOne(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getRequestNo, requestNo));
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
