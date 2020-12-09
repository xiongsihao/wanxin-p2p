package cn.xsh.sailing.sms.qcloud;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSenderUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.qcloudsms.httpclient.HTTPMethod;
import com.github.qcloudsms.httpclient.HTTPRequest;
import com.github.qcloudsms.httpclient.HTTPResponse;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 扩展腾讯云发送短信方法
 * 因为腾讯云引用json的包与spring-boot有冲突，故自己来实现
 */
public class QCloudSmsSingleSender extends SmsSingleSender {

	private String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";

	QCloudSmsSingleSender(int appid, String appkey) {
		super(appid, appkey);
	}

	@Override
	public SmsSingleSenderResult sendWithParam(String nationCode, String phoneNumber, int templateId, String[] params,
			String sign, String extend, String ext) throws HTTPException, JSONException, IOException {
		return this
				.sendWithParam(nationCode, phoneNumber, templateId, new ArrayList(Arrays.asList(params)), sign, extend,
						ext);
	}

	@Override
	public SmsSingleSenderResult sendWithParam(String nationCode, String phoneNumber, int templateId,
			ArrayList<String> params, String sign, String extend, String ext)
			throws HTTPException, JSONException, IOException {
		long random = SmsSenderUtil.getRandom();
		long now = SmsSenderUtil.getCurrentTime();
		//扩展方法 使用fastjson
		JSONObject body = new JSONObject();
		Map telMap = new HashMap<>();
		telMap.put("mobile", phoneNumber);
		telMap.put("nationcode", nationCode);
		body.put("tel", telMap);
		body.put("params", params);
		body.put("tpl_id", templateId);
		body.put("sig", SmsSenderUtil.calculateSignature(this.appkey, random, now, phoneNumber));
		body.put("sign", sign);
		body.put("time", now);
		body.put("extend", SmsSenderUtil.isNotEmpty(extend) ? extend : "");
		body.put("ext", SmsSenderUtil.isNotEmpty(ext) ? ext : "");

		HTTPRequest req = (new HTTPRequest(HTTPMethod.POST, this.url)).addHeader("Conetent-Type", "application/json")
				.addQueryParameter("sdkappid", this.appid).addQueryParameter("random", random)
				.setConnectionTimeout('\uea60').setRequestTimeout('\uea60').setBody(body.toString());

		try {
			HTTPResponse e = this.httpclient.fetch(req);
			this.handleError(e);
			return (new SmsSingleSenderResult()).parseFromHTTPResponse(e);
		} catch (URISyntaxException var15) {
			throw new RuntimeException("API url has been modified, current url: " + this.url);
		}
	}
}