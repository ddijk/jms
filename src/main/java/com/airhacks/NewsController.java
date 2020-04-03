package com.airhacks;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
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
    public String get(@QueryParam("subject") String subject) {

        final JMSProducer producer = jmsContext.createProducer();

        producer.send(topic, "hallo:" + (topic != null));

        System.out.println("succesvol verzonden");

        return "leuk " + subject;

    }
}
