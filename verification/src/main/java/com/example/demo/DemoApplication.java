package com.example.demo;

import com.example.demo.model.registered;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Scheduled(fixedRateString = "PT1M")
	void cronjob() throws IOException {
		RestTemplate restTemplate = this.getRestTemplate();
		ResponseEntity<registered[]> response = restTemplate.getForEntity("http://localhost:8081/api/regi", registered[].class);


		registered[] tocheck = response.getBody();

		for (registered cuser : tocheck) {


			if (cuser.isEnabled() == false) {
				String string = String.format("http://localhost:8081/api/regi/%s",cuser.getUserid());

				restTemplate.delete(string);

			}
			else {
				System.out.println(cuser.getUseremail());
			}
		}
	}



	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
	class SchedulingConfiguration {

	}
}