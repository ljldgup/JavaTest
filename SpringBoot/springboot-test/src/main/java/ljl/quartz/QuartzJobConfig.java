package ljl.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Random;

//@Configuration
public class QuartzJobConfig {

    private String cronExpression = "*/5 * * * * ?";

    @Bean
    public Random genRandom(){
        return new Random();
    }


    @Bean(name = "jobDetail")
    public MethodInvokingJobDetailFactoryBean tablePartitionJobDetail(TestQuartzJob1 job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(job);
        // 需要执行的方法
        jobDetail.setTargetMethod("execute");
        return jobDetail;
    }


    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean tablePartitionTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setCronExpression(cronExpression);
        return trigger;
    }

    /**
     * 配置调度器Scheduler
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger ...triggers) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        scheduler.setStartupDelay(1);
        // 注册触发器
        scheduler.setTriggers(triggers);
        return scheduler;
    }
}