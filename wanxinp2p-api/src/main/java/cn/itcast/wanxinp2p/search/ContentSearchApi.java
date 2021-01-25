package cn.itcast.wanxinp2p.search;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.search.model.ProjectQueryParamsDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;

/**
 * @author : xsh
 * @create : 2021-01-26 - 2:54
 * @describe: 内容检索服务API
 */
public interface ContentSearchApi {

    /**
     * 检索标的
     * @param projectQueryParamsDTO
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param order
     * @return
     */
    RestResponse<PageVO<ProjectDTO>> queryProjectIndex(ProjectQueryParamsDTO projectQueryParamsDTO,
                                                       Integer pageNo, Integer pageSize, String sortBy, String order);
}
