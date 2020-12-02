package cn.itcast.wanxinp2p.gateway.common.util;

import cn.itcast.wanxinp2p.common.domain.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtil {

    public static void writerError(RestResponse restResponse, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(restResponse.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),restResponse);
    }
}
