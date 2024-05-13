package com.nnaltakyan.core.auth;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
// @Import(ServiceClientsConfiguration.class)
// @EnableConfigurationProperties({ UserDynamicPasswordProperties.class })
@EnableScheduling
// @EnableSchedulerLock(defaultLockAtMostFor = "10m")
@Slf4j
public class AuthApplication
{
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args)
	{
		SpringApplication.run(AuthApplication.class, args);
	}

	@PostConstruct
	public void afterStart()
	{
		log.debug("Auth Application started");
	}
}
