package cn.itcast.wanxinp2p.transaction;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:37
 * @describe:  交易中心服务API
 */
public interface TransactionApi {

    /**
     *  借款人发标
     *  @param projectDTO
     *  @return
     */
    RestResponse<ProjectDTO> createProject(ProjectDTO projectDTO);
}
