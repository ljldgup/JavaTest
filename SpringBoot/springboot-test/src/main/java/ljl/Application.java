package ljl;

import ljl.runner.ScheduleRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
@EnableScheduling
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "ljl.quartz.*")})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
