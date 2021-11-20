package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
public class JmsConfig {

	/**
	 * JmsTransactionManager事务管理， 有了这个Transaction 才能胜雄安
	 */
	@Bean("JmsTransactionManager")
	public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
		return new JmsTransactionManager(connectionFactory);
	}
}