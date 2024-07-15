package com.nnaltakyan.core.auth;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
