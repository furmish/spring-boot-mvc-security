package ru.furmish.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

}
