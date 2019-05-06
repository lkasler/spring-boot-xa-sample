package hu.bridgesoft.xa.sample.jms;

import hu.bridgesoft.xa.sample.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Jms listener.
 * @author kasler.
 */
@Component
@Slf4j
public class JmsXaEventListener {

    @JmsListener(destination = JmsConfig.QUEUE_1)
    public void receiveMessage(final Message message) throws JMSException {
        if (message instanceof TextMessage){
            log.info("Message received: {}" ,  ((TextMessage) message).getText());
        } else {
            log.warn("Jms message received not a textMessage: {}", message.getBody(Object.class).toString());
        }

    }
}
