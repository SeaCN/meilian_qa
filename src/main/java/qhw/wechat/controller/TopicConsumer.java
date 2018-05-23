package qhw.wechat.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopicConsumer {

	@RequestMapping(value = "/topicConsumer")
	public void topicConsumer() throws Exception{
		// 第一步：创建一个ConnectionFactory对象。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		// 第二步：从ConnectionFactory对象中获得一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接。调用Connection对象的start方法。
		connection.setClientID("123");
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
		Topic topic = session.createTopic("topic");
		// 第六步：使用Session对象创建一个Consumer对象。
//		MessageConsumer consumer = session.createConsumer(topic);
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "111");
		// 第七步：接收消息。
		topicSubscriber.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = null;
					// 取消息的内容
					text = textMessage.getText();
					// 第八步：打印消息。
					System.out.println("manual:" + text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("按任意键退出...");
		// 等待键盘输入
		System.in.read();
		// 第九步：关闭资源
		topicSubscriber.close();
		session.close();
		connection.close();
	}
}
