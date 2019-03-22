package nl.hu.iac.workshop_microservices;

/*

Producer.java

Simple JMS producer for Apache ActiveMQ

(c)2013 Kevin Boone
 */

// Note that the only Apache-specific class referred to in the source is
//  the one that provides the initial broker connection. The rest is
//  standard JMS
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

public class Producer {
	private Connection connection;
	private Session session;
	private MessageProducer messageProducer;

	public void create() throws Exception {
		BrokerService broker = new BrokerService();

		broker.addConnector("tcp://localhost:61616");

		broker.start();

		// Create a connection factory referring to the broker host and port
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		// Note that a new thread is created by createConnection, and it
		// does not stop even if connection.stop() is called. We must
		// shut down the JVM using System.exit() to end the program
		connection = factory.createConnection();

		// Start the connection
		connection.start();

		// Create a non-transactional session with automatic acknowledgement
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create a reference to the queue test_queue in this session. Note
		// that ActiveMQ has auto-creation enabled by default, so this JMS
		// destination will be created on the broker automatically
		// Queue queue = session.createQueue("test_queue");
		Topic topic = session.createTopic("test_topic");

		// Create a producer for test queue
		// MessageProducer producer = session.createProducer(queue);
		messageProducer = session.createProducer(topic);
	}

	public void closeConnection() throws JMSException {
		connection.close();
	}

	public void sendMessage(String message) throws JMSException {
		// create a JMS TextMessage
		TextMessage textMessage = session.createTextMessage(message);

		// send the message to the topic destination
		messageProducer.send(textMessage);
		
		System.out.println("Sent message: \"" + message + "\"");
	}
}
