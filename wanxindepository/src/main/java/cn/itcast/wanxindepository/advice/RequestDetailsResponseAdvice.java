package cn.itcast.wanxindepository.advice;

import cn.itcast.wanxindepository.entity.RequestDetails;
import cn.itcast.wanxindepository.service.RequestDetailsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * <P>
 * 统一拦截请求处理结果保存
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-23
 */
@ControllerAdvice
public class RequestDetailsResponseAdvice implements ResponseBodyAdvice {

	@Autowired
	private RequestDetailsService requestDetailsService;

	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass,
			ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
		if (body == null) {
			return body;
		}

		if (serverHttpRequest.getURI().getPath().contains("swagger")) {
			return body;
		}

		// 获取respData
		JSONObject respData = (JSONObject) JSON.parseObject(JSON.toJSONString(body), Map.class).get("respData");

		if (respData != null) {
			//更新处理结果
			RequestDetails requestDetails = new RequestDetails();
			requestDetails.setRequestNo(respData.getString("requestNo"));
			requestDetails.setStatus(respData.getInteger("status"));
			requestDetails.setResponseData(respData.toJSONString());
			requestDetailsService.modifyByRequestNo(requestDetails);
		}
		return body;
	}
}
