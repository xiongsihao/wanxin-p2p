package cn.itcast.wanxindepository.interceptor;

import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.common.util.RSAUtil;
import cn.itcast.wanxindepository.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 * okHttp3拦截器, 用于数据签名及验签
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019-05-17
 */
@Slf4j
@Service
public class OKHttpSignatureInterceptor implements Interceptor {

	private final String METHOD_GET = "GET";

	@Autowired
	private ConfigService configService;

	/**
	 * 签名的编码
	 */
	private final String INPUT_CHARSET = "UTF-8";

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Response response = null;
		Request requestProcess = null;
		if (METHOD_GET.equals(request.method())) {
			// 获取原请求地址
			String url = request.url().toString();
			// 获取参数解码
			final String reqData = getParam(url, "reqData");
			String jsonString = EncryptUtil.decodeUTF8StringBase64(reqData);
			final String serviceName = getParam(url, "serviceName");
			final String platformNo = getParam(url, "platformNo");
			//
			String base = url.substring(0, url.indexOf("?") + 1);

			// 进行签名
			final String sign = RSAUtil.sign(jsonString, configService.getDepositoryPrivateKey(), INPUT_CHARSET);
			// 修改请求地址, 并添加签名参数
			url = base + "serviceName=" + URLEncoder.encode(serviceName, INPUT_CHARSET) + "&platformNo=" + URLEncoder
					.encode(platformNo, INPUT_CHARSET) + "&reqData=" + URLEncoder.encode(reqData, INPUT_CHARSET)
					+ "&signature=" + URLEncoder.encode(sign, INPUT_CHARSET);
			// 重新构造请求
			Request.Builder builder = request.newBuilder();
			builder.get().url(url);
			requestProcess = builder.build();
			// 执行请求
			response = chain.proceed(requestProcess);
		} else {
			// 其他请求不处理
			response = chain.proceed(request);
		}
		return response;
	}

	/**
	 * 获取url中参数
	 *
	 * @param url
	 * @param name
	 * @return
	 */
	public String getParam(String url, String name) {
		String params = url.substring(url.indexOf("?") + 1, url.length());
		final String[] splitter = params.split("&");
		for (String str : splitter) {
			if (str.startsWith(name + "=")) {
				return str.substring(name.length() + 1);
			}
		}
		return "";
	}

}