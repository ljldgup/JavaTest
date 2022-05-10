package ljl.runner;

import ljl.activemq.TestData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

import static util.RandomUtil.getRandomString;

@Component
public class GcTestRunner  implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
//        while (true) {
//            new TestData(new Date(),getRandomString(4096 * 4096));
//        }
    }
}
