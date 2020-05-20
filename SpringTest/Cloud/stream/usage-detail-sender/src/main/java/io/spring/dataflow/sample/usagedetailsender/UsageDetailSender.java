package io.spring.dataflow.sample.usagedetailsender;

import java.util.Random;

import io.spring.dataflow.sample.domain.UsageDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableBinding(Source.class)
public class UsageDetailSender {

	@Autowired
	private Source source;

	private String[] users = {"Glenn", "Sabby", "Mark", "Janne", "Ilaya"};
    
    //fixedDelay,控制方法执行的间隔时间,阻塞执行完，并间隔给定的时间后，执行下一次
    //fixedRate是按照一定的速率执行,塞这段时间内累计应该执行的次数
    //initialDelay 启动延迟 
	@Scheduled(fixedDelay = 1000)
	public void sendEvents() {
		UsageDetail usageDetail = new UsageDetail();
		usageDetail.setUserId(this.users[new Random().nextInt(5)]);
		usageDetail.setDuration(new Random().nextInt(300));
		usageDetail.setData(new Random().nextInt(700));
		this.source.output().send(MessageBuilder.withPayload(usageDetail).build());
	}
}
