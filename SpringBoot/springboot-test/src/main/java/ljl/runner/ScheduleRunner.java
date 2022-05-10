package ljl.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

//@Component
public class ScheduleRunner implements CommandLineRunner {
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;


    @Override
    public void run(String... args) throws Exception {
        schedulerFactoryBean.start();
    }
}
