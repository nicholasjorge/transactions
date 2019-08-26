package com.george.transaction.persistenceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.george.transaction.persistenceservice.messaging.TransactionChannels;

@EnableBinding(TransactionChannels.class)
@EnableEurekaClient
@EnableJpaRepositories
@SpringBootApplication
public class PersistenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistenceServiceApplication.class, args);
	}

}
