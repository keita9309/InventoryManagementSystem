package com.example.ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ImsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ImsApplication.class, args);
	}

	// warファイルを作成するためのメソッド
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ImsApplication.class);
	}

}
