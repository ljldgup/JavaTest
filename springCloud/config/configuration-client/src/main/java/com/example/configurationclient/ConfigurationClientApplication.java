package com.example.configurationclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class ConfigurationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationClientApplication.class, args);
	}
}

@RefreshScope
@RestController
class MessageRestController {

	@Autowired
	private Environment env;

    //默认值
	@Value("${message:Hello default}")
	private String message;

	//@RequestMapping("/message")
	@GetMapping("/property")
	@ResponseBody
	public String getProperty(@RequestParam(name="name", required=true) String property) throws InterruptedException{

		return String.format("%s:%s message:%s", property, env.getProperty(property), message);
	}
}
