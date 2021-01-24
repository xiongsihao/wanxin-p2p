package cn.itcast.wanxinp2p.transaction.service;

import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:41
 * @describe: 交易中心service接口
 */
public interface ProjectService {

    /**
     * 创建标的
     * @param project
     * @return
     */
    ProjectDTO createProject(ProjectDTO project);
}
