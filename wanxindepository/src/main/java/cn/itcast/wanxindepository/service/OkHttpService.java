package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.interceptor.OKHttpSignatureInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <P>
 * okHttp3请求工具类
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019/5/17
 */
@Slf4j
@Service
public class OkHttpService {

	@Autowired
	private OKHttpSignatureInterceptor signatureInterceptor;

	public String sendHttpGet(String p2pNotifyUrl, String serviceName, String platformNo, String reqData) {
		String url = p2pNotifyUrl + "gateway/?serviceName=" + serviceName + "&platformNo=" + platformNo + "&reqData="
				+ EncryptUtil.encodeUTF8StringBase64(reqData);
		OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(signatureInterceptor).build();
		Request request = new Request.Builder().url(url).build();
		try (Response response = okHttpClient.newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			log.warn("请求出现异常: ", e);
		}
		return "";
	}

}
