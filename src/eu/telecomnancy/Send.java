package eu.telecomnancy;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: charoy
 * Date: 30/09/13
 * Time: 10:20
 */
public class Send {
    private final static String QUEUE_NAME = "hello";
    private final static String hostname ="ec2-54-73-194-42.eu-west-1.compute.amazonaws.com";
    private final static int port=5672;

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        factory.setPort(port);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello gugusse! tutut crap";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
