package uk.co.gaungoo.azoomee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AzoomeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzoomeeApplication.class, args);
	}

}
