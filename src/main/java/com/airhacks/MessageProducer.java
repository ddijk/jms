package com.airhacks;

import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class MessageProducer {

    public static void main(String[] args) throws NamingException {

        Properties properties = new Properties();
        properties.put("osb.jndi.queue.connectionfactory","jms/OppConnectionFactoryOSB");
        InitialContext initialContext = new InitialContext(properties);

        String topicName = "";
        Topic topic = (Topic) initialContext.lookup(topicName);
    }
}
