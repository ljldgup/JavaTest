package ljl.activemq;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import javax.jms.Topic;

@Component
public class Publisher {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    public void sendDataMessage(TestData testData) {
        String jsonString = JSON.toJSONString(testData);
        jmsTemplate.convertAndSend(topic, jsonString);
    }
}
