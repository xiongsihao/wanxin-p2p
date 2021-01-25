package cn.itcast.wanxinp2p.search.service;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.search.model.ProjectQueryParamsDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;

/**
 * @author : xsh
 * @create : 2021-01-26 - 3:14
 * @describe: 标的检索业务层接口
 */
public interface ProjectIndexService {

    PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO projectQueryParamsDTO, Integer pageNo,
                                         Integer pageSize, String sortBy, String order);

}
