package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.RequestDetails;
import cn.itcast.wanxindepository.model.BaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存管系统请求信息表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-23
 */
public interface RequestDetailsService extends IService<RequestDetails> {

	/**
	 * 保存请求信息
	 * @param requestDetails
	 * @return
	 */
	RequestDetails create(RequestDetails requestDetails);

	/**
	 * 修改请求信息结果
	 * @param requestDetails
	 * @return
	 */
	Boolean modifyByRequestNo(RequestDetails requestDetails);

	/**
	 * 修改网关请求信息结果
	 * @param response
	 * @return
	 */
	Boolean modifyGatewayByRequestNo(BaseResponse response);

	/**
	 * 根据请求流水号获取
	 * @param requestNo
	 * @return
	 */
	RequestDetails getByRequestNo(String requestNo);

}
