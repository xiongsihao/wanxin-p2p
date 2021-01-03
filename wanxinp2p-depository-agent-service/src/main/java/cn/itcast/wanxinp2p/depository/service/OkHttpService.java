package cn.itcast.wanxinp2p.depository.service;

import cn.itcast.wanxinp2p.depository.interceptor.SignatureInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * okHttp3请求工具类
 */
@Slf4j
@Service
public class OkHttpService {

    @Autowired
    private SignatureInterceptor signatureInterceptor;

    /**
     * okHttp 同步 GET 请求
     *
     * @param url
     * @return
     */
    public String doSyncGet(String url) {
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
