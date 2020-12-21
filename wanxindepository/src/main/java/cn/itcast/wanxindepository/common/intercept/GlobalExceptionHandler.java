package cn.itcast.wanxindepository.common.intercept;


import cn.itcast.wanxindepository.common.domain.BusinessException;
import cn.itcast.wanxindepository.common.domain.RemoteReturnCode;
import cn.itcast.wanxindepository.model.BaseResponse;
import cn.itcast.wanxindepository.model.DepositoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <P>
 * 统一拦截异常处理
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019/5/22
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public DepositoryResponse<BaseResponse> exceptionGet(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		log.info(e.getMessage(), e);
		if (e instanceof BusinessException) {
			BusinessException businessException = (BusinessException) e;
			return new DepositoryResponse<>(new BaseResponse(businessException.getRequestNo(), businessException.getReturnCode()));

		} else if (e instanceof NoHandlerFoundException) {
			return new DepositoryResponse<>(new BaseResponse("404", "找不到资源"));
		} else if (e instanceof HttpRequestMethodNotSupportedException) {
			return new DepositoryResponse<>(new BaseResponse("405", "method 方法不支持"));
		} else if (e instanceof HttpMediaTypeNotSupportedException) {
			return new DepositoryResponse<>(new BaseResponse("415", "不支持媒体类型"));
		}
		return new DepositoryResponse<>(new BaseResponse(RemoteReturnCode.UNKNOWN));
	}

}
