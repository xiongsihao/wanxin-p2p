package cn.xsh.sailing.sms.qcloud;

import cn.xsh.sailing.common.domain.BusinessException;
import cn.xsh.sailing.common.domain.CommonErrorCode;
import cn.xsh.sailing.sms.SmsService;
import com.github.qcloudsms.SmsSingleSenderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <P>
 * 腾讯云短信服务
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-07-12
 */
@Slf4j
@Service
public class QCloudSmsService implements SmsService {

	@Value("${sms.qcloud.appId}")
	private int appId;

	@Value("${sms.qcloud.appKey}")
	private String appKey;

	@Value("${sms.qcloud.templateId}")
	private int templateId;

	@Value("${sms.qcloud.sign}")
	private String sign;

	@Override
	public void send(String mobile, String code, int effectiveTime) {
		log.info("给手机号" + mobile + "发送验证码：" + code);
		SmsSingleSenderResult senderResult;
		try {
			QCloudSmsSingleSender qCloudSmsSingleSender = new QCloudSmsSingleSender(appId, appKey);
			senderResult = qCloudSmsSingleSender
					.sendWithParam("86", mobile, templateId, new String[]{code, String.valueOf(effectiveTime / 60)},
							sign, "", "");
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(CommonErrorCode.UNKNOWN, e.getMessage());
		}
		if (senderResult.result != CommonErrorCode.SUCCESS.getCode()) {
			log.warn("QCloud return: {}", senderResult.toString());
			throw new BusinessException(CommonErrorCode.CUSTOM, senderResult.errMsg);
		}
	}

	@Override
	public void sendOnConsole(String mobile, String code, int effectiveTime) {
		log.info("给手机号" + mobile + "发送验证码：" + code);
	}
}
