package ljl.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {
    @Value("test-topic")
    private String topicName;

    @Value("${spring.activemq.user}")
    private String usrName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
    }

}
