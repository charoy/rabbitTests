package eu.telecomnancy;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: charoy
 * Date: 30/09/13
 * Time: 11:29

 */
public class ProduceTasks {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static String hostname ="ec2-50-19-7-134.compute-1.amazonaws.com";
    //private final static int port=5672;

    public static void main(String[] argv) throws Exception {
        int messageCount=0;
        AtomicReference<String> message = new AtomicReference<String>();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        while (messageCount<50)     {
            message.set(produceTask(messageCount++));
            channel.basicPublish( "", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.get().getBytes());
            System.out.println(" [x] Sent '" + message.get() + "'");
            Thread.sleep(1000);
        }

        channel.close();
        connection.close();
    }


    private static String produceTask(int count) {
        int length = new Random().nextInt(9);
        String message=""+count+" ";
        for (int i=0;i<length;i++) {
          message=message+".";
        }
        return message;
    }
}
