package ljl.quartz;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

//不需要SchedulerFactoryBean启动
//@Component
public class TestQuartzJob1 {
    public void execute(Object... args) throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(now + " TestQuartzJob1 " + i);
        }
    }
}
