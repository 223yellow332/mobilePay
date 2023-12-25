package com.calmdown.mobilePay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy // AOP 추가
//@EnableJpaAuditing
@SpringBootApplication
public class MobilePayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilePayApplication.class, args);
	}

}
