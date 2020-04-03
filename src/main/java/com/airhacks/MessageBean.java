/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 * <p>
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.airhacks;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.Startup;
import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MessageBean class is a message-driven bean. It implements the
 * javax.jms.MessageListener interface. It is defined as public (but not final
 * or abstract).
 */
/* At present, must define destination in MDB in order to use destinationLookup;
 * must use mappedName if destination is defined elsewhere
 * (GlassFish issue 20715).
 */
@JMSDestinationDefinition(
        name = "java:module/jms/newsTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "myPhysicalName")
//@MessageDriven(activationConfig = {
//    @ActivationConfigProperty(propertyName = "destinationLookup",
//            propertyValue = "java:module/jms/newsTopic"),
//    @ActivationConfigProperty(propertyName = "destinationType",
//            propertyValue = "javax.jms.Topic"),
//    @ActivationConfigProperty(propertyName = "messageSelector",
//            propertyValue = "NewsType = 'Sports' OR NewsType = 'Opinion'"),
//    @ActivationConfigProperty(propertyName = "subscriptionDurability",
//            propertyValue = "Durable"),
//    @ActivationConfigProperty(propertyName = "clientId",
//            propertyValue = "MyID"),
//    @ActivationConfigProperty(propertyName = "subscriptionName",
//            propertyValue = "MySub")
//})
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:module/jms/newsTopic"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic")})
@Startup
public class MessageBean implements MessageListener {

    static final Logger logger = Logger.getLogger("MessageBean");
    @Resource
    public MessageDrivenContext mdc;

    public MessageBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("MessageBean is comming up...." + (mdc != null));
    }

    /**
     * onMessage method, declared as public (but not final or static), with a
     * return type of void, and with one argument of type javax.jms.Message.
     *
     * Casts the incoming Message to a TextMessage and displays the text.
     *
     * @param inMessage the incoming message
     */
    @Override
    public void onMessage(Message inMessage) {

        try {
            if (inMessage instanceof TextMessage) {
                logger.log(Level.INFO,
                        "MESSAGE BEAN: Message received: {0}",
                        inMessage.getBody(String.class));
            } else {
                logger.log(Level.WARNING,
                        "Message of wrong type: {0}",
                        inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "MessageBean.onMessage: JMSException: {0}", e.toString());
            mdc.setRollbackOnly();
        }
    }
}
