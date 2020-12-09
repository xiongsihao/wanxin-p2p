package cn.xsh.sailing.sms;

/**
 * <P>
 * 短信服务
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-07-12
 */
public interface SmsService {

	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	default void send(String mobile, String content) {
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param code
	 * @param effectiveTime
	 * @return
	 */
	void send(String mobile, String code, int effectiveTime);

	/**
	 * 在控制台输出验证码，模拟发送短信
	 * @param mobile
	 * @param code
	 * @param effectiveTime
	 * @return
	 */
	void sendOnConsole(String mobile, String code, int effectiveTime);

}
