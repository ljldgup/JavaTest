package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@SpringBootApplication
@EnableDiscoveryClient
//EnableEurekaClient
//@EnableEurekaClient
@EnableAutoConfiguration
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
@RibbonClient(name = "server", configuration = SayHelloConfiguration.class)
public class RibbonClientApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(RibbonClientApplication.class, args);
    }

    @Bean
    @LoadBalanced //客户端负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    
}
