package ljl.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ljl.activemq.Publisher;
import ljl.activemq.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static util.RandomUtil.getRandomString;

@Component
public class MqRunner implements CommandLineRunner {
    @Autowired
    Publisher publisher;


    @Override
    public void run(String... args) throws Exception {
        while (true) {
            publisher.sendDataMessage(new TestData(new Date(), getRandomString(1024 * 1024)));
        }
    }
    
}
