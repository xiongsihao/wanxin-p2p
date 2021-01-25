package cn.itcast.wanxinp2p.transaction;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectQueryDTO;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:37
 * @describe: 交易中心服务API
 */
public interface TransactionApi {

    /**
     * 借款人发标
     *
     * @param projectDTO
     * @return
     */
    RestResponse<ProjectDTO> createProject(ProjectDTO projectDTO);

    /**
     * 后台系统 检索标的信息
     * @param projectQueryDTO 封装查询条件
     * @param order
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    RestResponse<PageVO<ProjectDTO>> queryProjects(
            ProjectQueryDTO projectQueryDTO,
            String order, Integer pageNo, Integer pageSize, String sortBy);


    /**
     * 管理员审核标的信息
     * @param id
     * @param approveStatus
     * @return
     */
    RestResponse<String> projectsApprovalStatus(Long id, String approveStatus);

    /**
     * 标的信息快速检索
     * @param projectQueryDTO
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param order
     * @return
     */
    RestResponse<PageVO<ProjectDTO>> queryProjects(ProjectQueryDTO projectQueryDTO, Integer pageNo, Integer pageSize, String sortBy,String order);
}
