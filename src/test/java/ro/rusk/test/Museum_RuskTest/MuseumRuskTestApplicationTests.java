package ro.rusk.test.Museum_RuskTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class MuseumRuskTestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testMuseumApi() {
		var response = WebClient.builder()
				.baseUrl("https://www.rijksmuseum.nl/api/en/collection?key=0fiuZFh4")
				.build()
				.get()
				.uri(uri -> {
							uri.queryParam("p", 1000);
							uri.queryParam("ps", 111);
							return uri.build();
						}
				)
				.retrieve().bodyToMono(String.class).block();
		WebTestClient testClient = WebTestClient
				.bindToServer()
				.baseUrl("https://www.rijksmuseum.nl/api/en/collection?key=0fiuZFh4")
				.build();
		testClient.get().attribute("p", 1000).attribute("ps", 11)
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
		System.out.println(response);

	}

}
