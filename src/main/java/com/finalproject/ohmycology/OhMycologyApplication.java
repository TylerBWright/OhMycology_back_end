package com.finalproject.ohmycology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.finalproject")
public class OhMycologyApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OhMycologyApplication.class);
	}


	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(OhMycologyApplication.class, args);
	}
}