package com.wojtek.evento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoApplication.class, args);
	}

}
