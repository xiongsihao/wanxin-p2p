package cn.itcast.wanxindepository.interceptor;

import cn.itcast.wanxindepository.common.domain.RemoteReturnCode;
import cn.itcast.wanxindepository.common.util.EncryptUtil;
import cn.itcast.wanxindepository.common.util.RSAUtil;
import cn.itcast.wanxindepository.common.util.ResponseUtil;
import cn.itcast.wanxindepository.model.BaseResponse;
import cn.itcast.wanxindepository.model.DepositoryResponse;
import cn.itcast.wanxindepository.service.ConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <P>
 * 统一拦截P2P平台请求数据验签
 * </p>
 *
 * @author zhupeiyuan@itcast.cn
 * @since 2019-05-23
 */
@Slf4j
@Component
public class VerificationInterceptor implements HandlerInterceptor {

	@Autowired
	private ConfigService configService;

	@Override
	public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) {
		String serviceName = httpRequest.getParameter("serviceName");
		String platformNo = httpRequest.getParameter("platformNo");
		String signature = httpRequest.getParameter("signature");
		String reqData = httpRequest.getParameter("reqData");

		BaseResponse baseResponse = new BaseResponse();

		//校验参数是否为空
		if (StringUtils.isBlank(serviceName) || StringUtils.isBlank(platformNo) || StringUtils.isBlank(reqData)
				|| StringUtils.isBlank(signature)) {
			baseResponse.setResp(RemoteReturnCode.PARAM_FAIL);
			ResponseUtil.responseOut(httpResponse, JSON.toJSONString(new DepositoryResponse<>(baseResponse)));
			return false;
		}

		//校验reqData格式
		try {
			JSON.parse(EncryptUtil.decodeUTF8StringBase64(reqData));
		} catch (JSONException e) {
			log.warn(e.getMessage(), e);
			baseResponse.setResp(RemoteReturnCode.PARAM_FAIL);
			ResponseUtil.responseOut(httpResponse, JSON.toJSONString(new DepositoryResponse<>(baseResponse)));
			return false;
		}

		//校验平台签名
		reqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		if (!verifySignature(platformNo, signature, reqData)) {
			baseResponse.setResp(RemoteReturnCode.SIGN_FAIL);
			ResponseUtil.responseOut(httpResponse, JSON.toJSONString(new DepositoryResponse<>(baseResponse)));
			return false;
		}

		return true;
	}

	/**
	 * 验签
	 * @param platformNo
	 * @param signature
	 * @param reqData
	 * @return
	 */
	private boolean verifySignature(String platformNo, String signature, String reqData) {
		//获取P2P平台公钥
		String p2pPublicKey = configService.getP2PPublicKey(platformNo);
		if (StringUtils.isBlank(p2pPublicKey)) {
			return false;
		}
		//验证签名
		return RSAUtil.verify(reqData, signature, p2pPublicKey, "utf-8");
	}
}
