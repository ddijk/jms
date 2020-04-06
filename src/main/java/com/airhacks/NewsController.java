package com.airhacks;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("news")
public class NewsController {

    @Resource(mappedName = "jms/newsTopic")
    Destination topic;

    @Inject
    JMSContext jmsContext;

//    @Resource
//    InitialContext context;

    @Resource
    javax.jms.ConnectionFactory connectionFactory;

    @GET
    public String get(@QueryParam("subject") String subject) throws JMSException {

        final JMSProducer producer = jmsContext.createProducer();

        TextMessage msg = jmsContext.createTextMessage("hallo Sports 1:" );
        msg.setStringProperty("NewsType", "Sports");

        producer.send(topic, msg);

        msg = jmsContext.createTextMessage("hallo Voetbal 2:" );
        msg.setStringProperty("NewsType", "Voetbal");
        producer.send(topic, msg);

        msg = jmsContext.createTextMessage("hallo Opinion 3:" );
        msg.setStringProperty("NewsType", "Opinion");
        producer.send(topic, msg);


        System.out.println("succesvol verzonden");

        return "leuk " + subject;

    }
}
