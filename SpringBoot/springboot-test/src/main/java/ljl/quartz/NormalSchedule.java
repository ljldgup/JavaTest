package ljl.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//@Component
public class NormalSchedule {
    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        LocalDateTime now = LocalDateTime.now();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(now + " NormalSchedule " + atomicInteger.addAndGet(1));
    }
}
