package cn.itcast.wanxinp2p.transaction.service;

import cn.itcast.wanxinp2p.common.domain.*;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.common.util.CommonUtil;
import cn.itcast.wanxinp2p.consumer.model.BalanceDetailsDTO;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import cn.itcast.wanxinp2p.depository.model.UserAutoPreTransactionRequest;
import cn.itcast.wanxinp2p.transaction.agent.ConsumerApiAgent;
import cn.itcast.wanxinp2p.transaction.agent.ContentSearchApiAgent;
import cn.itcast.wanxinp2p.transaction.agent.DepositoryAgentApiAgent;
import cn.itcast.wanxinp2p.transaction.common.constant.TradingCode;
import cn.itcast.wanxinp2p.transaction.common.constant.TransactionErrorCode;
import cn.itcast.wanxinp2p.transaction.common.utils.IncomeCalcUtil;
import cn.itcast.wanxinp2p.transaction.common.utils.SecurityUtil;
import cn.itcast.wanxinp2p.transaction.entity.Project;
import cn.itcast.wanxinp2p.transaction.entity.Tender;
import cn.itcast.wanxinp2p.transaction.mapper.ProjectMapper;
import cn.itcast.wanxinp2p.transaction.mapper.TenderMapper;
import cn.itcast.wanxinp2p.transaction.model.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:42
 * @describe:
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ConsumerApiAgent consumerApiAgent;
    @Autowired
    private ConfigService configService;
    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;
    @Autowired
    private ContentSearchApiAgent contentSearchApiAgent;
    @Autowired
    private TenderMapper tenderMapper;

    /**
     * 创建标的
     *
     * @param projectDTO
     * @return
     */
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(SecurityUtil.getUser().getMobile());
        // 设置用户编码
        projectDTO.setUserNo(restResponse.getResult().getUserNo());
        // 设置用户id
        projectDTO.setConsumerId(restResponse.getResult().getId());
        // 生成标的编码
        projectDTO.setProjectNo(CodeNoUtil.getNo(CodePrefixCode.CODE_PROJECT_PREFIX));
        // 标的状态修改
        projectDTO.setProjectStatus(ProjectCode.COLLECTING.getCode());
        // 标的可用状态修改, 未同步
        projectDTO.setStatus(StatusCode.STATUS_OUT.getCode());
        // 设置标的创建时间
        projectDTO.setCreateDate(LocalDateTime.now());
        // 设置还款方式
        projectDTO.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.getCode());
        // 设置标的类型
        projectDTO.setType("NEW");
        Project project = convertProjectDTOToEntity(projectDTO);

        project.setBorrowerAnnualRate(configService.getBorrowerAnnualRate());
        project.setAnnualRate(configService.getAnnualRate());
        // 年化利率(平台佣金，利差)
        project.setCommissionAnnualRate(configService.getCommissionAnnualRate());
        // 债权转让
        project.setIsAssignment(0);
        // 设置标的名字, 姓名+性别+第N次借款
        // 判断男女
        String sex = Integer.parseInt(restResponse.getResult().getIdNumber()
                .substring(16, 17)) % 2 == 0 ? "女士" : "先生";
        // 构造借款次数查询条件
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getConsumerId,
                restResponse.getResult().getId());
        project.setName(restResponse.getResult().getFullname() + sex
                + "第" + (count(queryWrapper) + 1) + "次借款");
        save(project);
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }

    /**
     * 根据分页条件检索标的信息
     *
     * @param projectQueryDTO
     * @param order
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    public PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {

        //带条件
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        // 标的类型
        if (StringUtils.isNotBlank(projectQueryDTO.getType())) {
            queryWrapper.lambda().eq(Project::getType, projectQueryDTO.getType());
        } // 起止年化利率(投资人) -- 区间
        if (null != projectQueryDTO.getStartAnnualRate()) {
            queryWrapper.lambda().ge(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        }
        if (null != projectQueryDTO.getEndAnnualRate()) {
            queryWrapper.lambda().le(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        } // 借款期限 -- 区间
        if (null != projectQueryDTO.getStartPeriod()) {
            queryWrapper.lambda().ge(Project::getPeriod,
                    projectQueryDTO.getStartPeriod());
        }
        if (null != projectQueryDTO.getEndPeriod()) {
            queryWrapper.lambda().le(Project::getPeriod,
                    projectQueryDTO.getEndPeriod());
        } // 标的状态
        if (StringUtils.isNotBlank(projectQueryDTO.getProjectStatus())) {
            queryWrapper.lambda().eq(Project::getProjectStatus,
                    projectQueryDTO.getProjectStatus());
        }
        //分页
        // 构造分页对象
        Page<Project> page = new Page<>(pageNo, pageSize);

        //排序
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortBy)) {
            if (order.toLowerCase().equals("asc")) {
                queryWrapper.orderByAsc(sortBy);
            } else if (order.toLowerCase().equals("desc")) {
                queryWrapper.orderByDesc(sortBy);
            }
        } else {
            queryWrapper.lambda().orderByDesc(Project::getCreateDate);
        }

        //执行查询
        IPage<Project> iPage = page(page, queryWrapper);

        //封装结果
        List<ProjectDTO> projectDTOList = convertProjectEntityListToDTOList(iPage.getRecords());
        return new PageVO<>(projectDTOList, iPage.getTotal(), pageNo, pageSize);

    }

    /**
     * 管理员审核标的信息
     *
     * @param id
     * @param approveStatus
     * @return
     */
    @Override
    public String projectsApprovalStatus(Long id, String approveStatus) {
        //1.根据id查询标的信息并转换为DTO对象
        Project project = getById(id);
        ProjectDTO projectDTO = convertProjectEntityToDTO(project);
        //2.生成流水号(不存在才生成)
        if (StringUtils.isBlank(project.getRequestNo())) {
            projectDTO.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            update(Wrappers.<Project>lambdaUpdate().set(Project::getRequestNo,
                    projectDTO.getRequestNo()).eq(Project::getId, id));
        }

        //3.通过feign远程访问存管代理服务，把标的信息传输过去
        RestResponse<String> restResponse = depositoryAgentApiAgent.createProject(projectDTO);

        if (DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(restResponse.getResult())) {
            //4.根据结果修改状态
            update(Wrappers.<Project>lambdaUpdate().set(Project::getStatus, Integer.parseInt(approveStatus)).eq(Project::getId, id));
            return "success";
        }

        //5.如果失败就抛异常
        throw new BusinessException(TransactionErrorCode.E_150113);
    }

    @Override
    public PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {
        RestResponse<PageVO<ProjectDTO>> esResponse = contentSearchApiAgent.queryProjectIndex(projectQueryDTO, pageNo, pageSize, sortBy, order);
        if (!esResponse.isSuccessful()) {
            throw new BusinessException(CommonErrorCode.UNKOWN);
        }
        return esResponse.getResult();
    }

    /**
     * 通过ids获取多个标的
     *
     * @param ids
     * @return
     */
    @Override
    public List<ProjectDTO> queryProjectsIds(String ids) {
        //1. 查询标的信息
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        List<Long> list = new ArrayList<>();
        Arrays.asList(ids.split(",")).forEach(str -> {
            list.add(Long.parseLong(str));
        });
        queryWrapper.lambda().in(Project::getId, list); // .... where  id  in  (1,2,3,4,5)
        List<Project> projects = list(queryWrapper);
        List<ProjectDTO> dtos = new ArrayList<>();
        //2.转换为DTO对象
        for (Project project : projects) {
            ProjectDTO projectDTO = convertProjectEntityToDTO(project);
            // 3. 获取剩余额度
            projectDTO.setRemainingAmount(getProjectRemainingAmount(project));
            //4. 查询出借人数
            projectDTO.setTenderCount(
                    tenderMapper.selectCount(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, project.getId()))
            );
            dtos.add(projectDTO);
        }
        return dtos;
    }

    /**
     * 根据标的id查询投标记录
     *
     * @param id
     * @return
     */
    @Override
    public List<TenderOverviewDTO> queryTendersByProjectId(Long id) {
        List<Tender> tenderList = tenderMapper.selectList(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, id));
        List<TenderOverviewDTO> tenderOverviewDTOList = new ArrayList<>();
        tenderList.forEach(tender -> {
            TenderOverviewDTO tenderOverviewDTO = new TenderOverviewDTO();
            BeanUtils.copyProperties(tender, tenderOverviewDTO);
            tenderOverviewDTO.setConsumerUsername(CommonUtil.hiddenMobile(tenderOverviewDTO.getConsumerUsername()));
            tenderOverviewDTOList.add(tenderOverviewDTO);
        });
        return tenderOverviewDTOList;
    }

    /**
     * 用户投标
     *
     * @param projectInvestDTO
     * @return
     */
    @Override
    public TenderDTO createTender(ProjectInvestDTO projectInvestDTO) {
        //1.前置条件判断
        // 1.1 判断投标金额是否大于最小投标金额
        // 获得投标金额
        BigDecimal amount = new BigDecimal(projectInvestDTO.getAmount());
        //获得最小投标金额
        BigDecimal miniInvestmentAmount = configService.getMiniInvestmentAmount();
        if (amount.compareTo(miniInvestmentAmount) < 0) {
            throw new BusinessException(TransactionErrorCode.E_150109);
        }
        //1.2 判断用户账户余额是否足够
        //得到当前登录用户
        LoginUser user = SecurityUtil.getUser();
        //通过手机号查询用户信息
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(user.getMobile());
        //通过用户编号查询账户余额
        RestResponse<BalanceDetailsDTO> balanceDetailsDTORestResponse = consumerApiAgent.getBalance(restResponse.getResult().getUserNo());
        BigDecimal myBalance = balanceDetailsDTORestResponse.getResult().getBalance();
        if (myBalance.compareTo(amount) < 0) {
            throw new BusinessException(TransactionErrorCode.E_150112);
        }
        //1.3 判断标的是否满标，标的状态为FULLY就表示满标
        Project project = getById(projectInvestDTO.getId());
        if (project.getProjectStatus().equalsIgnoreCase("FULLY")) {
            throw new BusinessException(TransactionErrorCode.E_150114);
        }
        //1.4 判断投标金额是否超过剩余未投金额
        BigDecimal remainingAmount = getProjectRemainingAmount(project);
        if (amount.compareTo(remainingAmount) < 1) {
            //1.5 判断此次投标后的剩余未投金额是否满足最小投标金额
            // 例如：借款人需要借1万 现在已经投标了8千 还剩2千 本次投标1950元
            // 公式：此次投标后的剩余未投金额 = 目前剩余未投金额 - 本次投标金额
            //还剩下50元投标金额小于最小投标金额，这个标的剩下的将会被最小投标金额校验住，所以应该提前检验；
            BigDecimal subtract = remainingAmount.subtract(amount);
            int result = subtract.compareTo(configService.getMiniInvestmentAmount());
            if (result < 0) {
                throw new BusinessException(TransactionErrorCode.E_150111);
            }
            //2. 保存投标信息并发送给存管代理服务
            //2.1 保存投标信息
            final Tender tender = new Tender();
            // 投资人投标金额( 投标冻结金额 )
            tender.setAmount(amount);
            // 投标人用户标识
            tender.setConsumerId(restResponse.getResult().getId());
            tender.setConsumerUsername(restResponse.getResult().getUsername());
            // 投标人用户编码
            tender.setUserNo(restResponse.getResult().getUserNo());
            // 标的标识
            tender.setProjectId(projectInvestDTO.getId());
            // 标的编码
            tender.setProjectNo(project.getProjectNo());
            // 投标状态
            tender.setTenderStatus(TradingCode.FROZEN.getCode());
            // 创建时间
            tender.setCreateDate(LocalDateTime.now());
            // 请求流水号
            tender.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            // 可用状态
            tender.setStatus(0);
            tender.setProjectName(project.getName());
            // 标的期限(单位:天)
            tender.setProjectPeriod(project.getPeriod());
            // 年化利率(投资人视图)
            tender.setProjectAnnualRate(project.getAnnualRate());
            // 保存到数据库
            tenderMapper.insert(tender);
            //2.2 发送数据给存管代理服务
            // 构造请求数据
            UserAutoPreTransactionRequest userAutoPreTransactionRequest = new
                    UserAutoPreTransactionRequest();
            // 冻结金额
            userAutoPreTransactionRequest.setAmount(amount);
            // 预处理业务类型
            userAutoPreTransactionRequest.setBizType(PreprocessBusinessTypeCode.TENDER.getCode());
            // 标的号
            userAutoPreTransactionRequest.setProjectNo(project.getProjectNo());
            // 请求流水号
            userAutoPreTransactionRequest.setRequestNo(tender.getRequestNo());
            // 投资人用户编码
            userAutoPreTransactionRequest.setUserNo(restResponse.getResult().getUserNo());
            // 设置 关联业务实体标识
            userAutoPreTransactionRequest.setId(tender.getId());
            // 远程调用存管代理服务
            RestResponse<String> response = depositoryAgentApiAgent.userAutoPreTransaction(userAutoPreTransactionRequest);

            //3.根据结果更新投标状态
            //3.1 判断结果
            if (response.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())) {
                //3.2 修改状态为：已同步
                tender.setStatus(1);
                tenderMapper.updateById(tender);
                //3.3 判断当前标的是否满标，如果满标，更新标的状态
                BigDecimal remainAmont = getProjectRemainingAmount(project);
                if (remainAmont.compareTo(new BigDecimal(0)) == 0) {
                    project.setProjectStatus(ProjectCode.FULLY.getCode());
                    updateById(project);
                }

                //3.4 转换为DTO对象，并封装相关数据
                TenderDTO tenderDTO = convertTenderEntityToDTO(tender);
                // 封装标的信息
                project.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.getDesc());
                tenderDTO.setProject(convertProjectEntityToDTO(project));
                // 封装预期收益
                // 根据标的期限计算还款月数
                final Double ceil = Math.ceil(project.getPeriod() / 30.0);
                Integer month = ceil.intValue();
                tenderDTO.setExpectedIncome(IncomeCalcUtil.getIncomeTotalInterest(new BigDecimal(projectInvestDTO.getAmount()), configService.getAnnualRate(), month));
                return tenderDTO;
            } else {
                throw new BusinessException(TransactionErrorCode.E_150113);
            }
        } else {
            throw new BusinessException(TransactionErrorCode.E_150110);
        }
    }

    private TenderDTO convertTenderEntityToDTO(Tender tender) {
        if (tender == null) {
            return null;
        }
        TenderDTO tenderDTO = new TenderDTO();
        BeanUtils.copyProperties(tender, tenderDTO);
        return tenderDTO;
    }

    private Project convertProjectDTOToEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        return project;
    }

    private List<ProjectDTO> convertProjectEntityListToDTOList(List<Project> projectList) {
        if (projectList == null) {
            return null;
        }
        List<ProjectDTO> dtoList = new ArrayList<>();
        projectList.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            BeanUtils.copyProperties(project, projectDTO);
            dtoList.add(projectDTO);
        });
        return dtoList;
    }

    private ProjectDTO convertProjectEntityToDTO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }

    /**
     * 获取标的剩余可投额度
     *
     * @param project
     * @return
     */
    private BigDecimal getProjectRemainingAmount(Project project) {
        // 根据标的id在投标表查询已投金额
        List<BigDecimal> decimalList =
                tenderMapper.selectAmountInvestedByProjectId(project.getId());
        // 求和结果集
        BigDecimal amountInvested = new BigDecimal("0.0");
        for (BigDecimal d : decimalList) {
            amountInvested = amountInvested.add(d);
        }
        // 得到剩余额度
        return project.getAmount().subtract(amountInvested);
    }
}
