package config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
//需要EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
public class TransactionConfig {
	String driveClassName;
	String url1;
	String url2;
	String username;
	String password;

	@Configuration
	@MapperScan(basePackages = "mapper1", sqlSessionTemplateRef = "sqlSessionTemplate1")
	public class Config1 {
		@Bean("DataSource1")
		@Primary
		public DataSource getDataSource1() {
			DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driveClassName)
					.url(url1)
					.username(username)
					.password(password);
			return factory.build();
		}

		@Bean(name = "sqlSessionFactory1")
		@Primary
		public SqlSessionFactory getSqlSessionFactory1(@Qualifier("DataSource1") DataSource datasource)
				throws Exception {
			//mybatis plus需要MybatisSqlSessionFactoryBean 代替 原生SqlSessionFactory
			MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources("classpath*:mapper1/mapping/**Mapper.xml"));
			bean.setDataSource(datasource);
			return bean.getObject();
		}

		@Bean("sqlSessionTemplate1")
		@Primary
		public SqlSessionTemplate getSqlSessionTemplate1(@Qualifier("sqlSessionFactory1") SqlSessionFactory sessionfactory) {
			return new SqlSessionTemplate(sessionfactory);
		}

		@Bean(name = "transactionManager1")
		@Primary
		public PlatformTransactionManager getTransactionManager1(@Qualifier("DataSource1") DataSource dataSource) {
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
			return dataSourceTransactionManager;
		}
	}

    @Configuration
	@MapperScan(basePackages = "mapper2", sqlSessionTemplateRef = "sqlSessionTemplate2")
	public class Config2 {
		@Bean("DataSource2")
		public DataSource getDataSource2() {
			DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driveClassName)
					.url(url2)
					.username(username)
					.password(password);
			return factory.build();
		}

		@Bean(name = "transactionManager2")
		public PlatformTransactionManager getTransactionManager2(@Qualifier("DataSource2") DataSource dataSource) {
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
			return dataSourceTransactionManager;
		}

		@Bean(name = "sqlSessionFactory2")
		public SqlSessionFactory getSqlSessionFactory2(@Qualifier("DataSource2") DataSource datasource)
				throws Exception {
			MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources("classpath*:mapper2/mapping/**Mapper.xml"));
			bean.setDataSource(datasource);
			return bean.getObject();
		}

		@Bean("sqlSessionTemplate2")
		public SqlSessionTemplate getSqlSessionTemplate2(@Qualifier("sqlSessionFactory2") SqlSessionFactory sessionfactory) {
			return new SqlSessionTemplate(sessionfactory);
		}
	}


}
