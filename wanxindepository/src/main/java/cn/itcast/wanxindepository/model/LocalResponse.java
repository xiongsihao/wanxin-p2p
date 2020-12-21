package cn.itcast.wanxindepository.model;

import io.swagger.annotations.ApiModel;

/**
 * <P>
 * 本地请求响应信息
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-29
 */
@ApiModel(value = "LocalResponse<T>", description = "本地请求响应信息")
public class LocalResponse<T> extends BaseResponse {

	private T result;

	public void setResult(T result) {
		this.result = result;
	}

	public T getResult() {
		return result;
	}

	public static <T> LocalResponse<T> success() {
		return new LocalResponse<T>();
	}

	public static <T> LocalResponse<T> success(T result) {
		LocalResponse<T> response = new LocalResponse<T>();
		response.setResult(result);
		return response;
	}
}
