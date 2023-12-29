package io.github.jubadeveloper.requestlimiter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RequestLimiterApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	void contextLoads() {
	}

	@Test
	void shouldBlockIp () throws Exception {
		// Make four (4) requests after ip blocking
		CommandLineRunner dateGetRequest = (v) -> restTemplate.getForEntity(String.format("http://localhost:%d/date", port), String.class);
		for (int x = 0; x < 5; x++) {
			dateGetRequest.run();
		}
		ResponseEntity<String> result = restTemplate.getForEntity(String.format("http://localhost:%d/date", port), String.class);
		Assertions.assertThat(result.getStatusCode().value()).isEqualTo(403);
	}

}
