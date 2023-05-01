package com.laundrymanagementsystem.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.laundrymanagementsystem.app.properties.FileStorageProperties;

@SpringBootApplication
@ComponentScan(basePackages = { "com.laundrymanagementsystem.app" })
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableScheduling
public class LaundryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaundryManagementSystemApplication.class, args);
	}

}
