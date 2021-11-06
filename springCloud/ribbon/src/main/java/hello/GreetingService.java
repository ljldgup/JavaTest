package hello;

import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class GreetingService {
    
  @Autowired
  private final RestTemplate restTemplate;

  public GreetingService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  //@HystrixCommand(fallbackMethod = "reliable")
  public String greeting() {
    URI uri = URI.create("http://EUREKA-CLIENT/");

    return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
    return "fallbackMethod reliable()";
  }

}