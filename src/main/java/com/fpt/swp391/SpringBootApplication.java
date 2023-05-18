package com.fpt.swp391;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class SpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
