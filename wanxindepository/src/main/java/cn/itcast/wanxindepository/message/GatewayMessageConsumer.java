package cn.itcast.wanxindepository.message;

import cn.itcast.wanxindepository.service.ConfigService;
import cn.itcast.wanxindepository.service.OkHttpService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * <P>
 * 开户网关接口通知回调消息消费者
 * </p>

 */
@Slf4j
@Component
public class GatewayMessageConsumer {

	private static final String CONSUMER_GROUP = "CID_DEPOSITORY_GATEWAY_NOTIFY";

	@Autowired
	private ConfigService configService;

	@Autowired
	private OkHttpService okHttpService;

	public GatewayMessageConsumer(@Value("${rocketmq.name-server}") String mqNameServer) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
		consumer.setNamesrvAddr(mqNameServer);
		// 消费策略：设置消费地点,从最后一个进行消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 订阅主题的哪些标签
		consumer.subscribe("TP_GATEWAY_NOTIFY", "*");
		// 注册监听器
		consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			try {
				Message msg = msgs.get(0);
				String body = new String(msg.getBody(), StandardCharsets.UTF_8);
				//获取异步通知接口名称
				String tags = msg.getTags();

				JSONObject jsonBody = JSON.parseObject(body);
				//获取P2P平台编号
				String appCode = jsonBody.getJSONObject("headers").getString("KEYS");
				//获取消息返回主题内容
				String response = jsonBody.getString("payload");
				String result = okHttpService
						.sendHttpGet(configService.getP2PNotifyUrl(appCode), tags, appCode, response);
				log.info("Receive Message: Result({}),KEYS({}),Tags({}),Body({})", result, appCode, tags, response);
				if ("OK".equals(result)) {
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		});
		consumer.start();
		log.info("GatewayMessageConsumer Listener");
	}

}