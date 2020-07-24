package org.jack.stringcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class StringCalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(StringCalculatorApplication.class, args);
	}
}
