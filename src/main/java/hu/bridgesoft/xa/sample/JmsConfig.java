package hu.bridgesoft.xa.sample;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Session;

/**
 * Jms atomikos config.
 * @author kasler
 */
@Configuration
@DependsOn("transactionManager")
@EnableConfigurationProperties(ArtemisProperties.class)
public class JmsConfig {

    private static final String ARTEMIS_URL = "tcp://%s:%s";


    @Autowired
    private ArtemisProperties artemisProperties;

    @Bean(name = "atomikosJmsConnectionFactor", initMethod = "init")
    public AtomikosConnectionFactoryBean atomikosConnectionFactoryBean(){
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("ActiveMQXA");
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory());
        atomikosConnectionFactoryBean.setPoolSize(1);
        return atomikosConnectionFactoryBean;
    }

    @Bean
    public ActiveMQXAConnectionFactory activeMQXAConnectionFactory() {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(String.format(ARTEMIS_URL, artemisProperties.getHost(), artemisProperties.getPort()));
        activeMQXAConnectionFactory.setUserName(artemisProperties.getUser());
        activeMQXAConnectionFactory.setPassword(artemisProperties.getPassword());
        return activeMQXAConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(atomikosConnectionFactoryBean());
        jmsTemplate.setReceiveTimeout(2000);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        return jmsTemplate;
    }
}
