package cn.itcast.wanxinp2p.repayment;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.repayment.model.ProjectWithTendersDTO;

/**
 * @author : xsh
 * @create : 2021-02-13 - 20:24
 * @describe:
 */
public interface RepaymentApi {

    /**
     * 启动还款
     * @param projectWithTendersDTO
     * @return
     */
    public RestResponse<String> startRepayment(ProjectWithTendersDTO projectWithTendersDTO);
}
