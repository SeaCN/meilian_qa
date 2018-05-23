package qhw.wechat.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopicProducer {

	@RequestMapping(value = "/topicProducer")
	public void topicProducer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic");
		MessageProducer producer = session.createProducer(topic);
		TextMessage text = session.createTextMessage("this is a test!");
		producer.send(text);
		producer.close();
		session.close();
		connection.close();
	}
	
	@RequestMapping(value = "/test")
	public void test(){
		System.out.println(123);
	}
}
