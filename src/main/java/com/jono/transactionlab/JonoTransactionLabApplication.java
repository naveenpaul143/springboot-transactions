package com.jono.transactionlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class JonoTransactionLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(JonoTransactionLabApplication.class, args);
	}

}
