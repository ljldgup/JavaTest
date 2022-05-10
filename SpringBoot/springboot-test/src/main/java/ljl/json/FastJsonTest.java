package ljl.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import ljl.activemq.TestData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static util.RandomUtil.getRandomString;

//@Component
public class FastJsonTest {

    @Scheduled(cron = "*/2 * * * * ?")
    public void test() {
        System.out.println("FastJsonTestf");
        JSON.toJSONString(new TestData(new Date(), getRandomString(4096*4096)),
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty);
    }
}
