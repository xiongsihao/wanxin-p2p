package cn.itcast.wanxinp2p.transaction.service;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.transaction.model.*;

import java.util.List;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:41
 * @describe: 交易中心service接口
 */
public interface ProjectService {

    /**
     * 创建标的
     *
     * @param project
     * @return
     */
    ProjectDTO createProject(ProjectDTO project);

    /** 根据分页条件检索标的信息
     *  @param projectQueryDTO
     *  @param order
     *  @param pageNo
     *  @param pageSize
     *  @param sortBy
     *  @return
     */
    PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy);


    /**
     * 管理员审核标的信息
     * @param id
     * @param approveStatus
     * @return
     */
    String projectsApprovalStatus(Long id, String approveStatus);

    PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy);

    /**
     * 通过ids获取多个标的
     * @param ids
     * @return
     */
    List<ProjectDTO> queryProjectsIds(String ids);

    /**
     * 根据标的id查询投标记录
     * @param id
     * @return
     */
    List<TenderOverviewDTO> queryTendersByProjectId(Long id);

    /**
     * 用户投标
     * @param projectInvestDTO
     * @return
     */
    TenderDTO createTender(ProjectInvestDTO projectInvestDTO);

    /**
     * 审核标的满标放款
     * @param id
     * @param approveStatus
     * @param commission
     * @return
     */
    String loansApprovalStatus(Long id, String approveStatus, String commission);
}

