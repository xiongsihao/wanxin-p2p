package cn.itcast.wanxinp2p.transaction.service;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectQueryDTO;

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
}
