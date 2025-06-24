package com.firstapp.firstapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class TestAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TestAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Create WebClient instance to make HTTP requests
		WebClient webClient = WebClient.builder()
				.baseUrl("https://leetcode.com")
				.defaultHeader("Content-Type", "application/json")
				.build();

		// GraphQL query to get the active daily question
		String query = """
			{
			  "query": "query questionOfToday { activeDailyCodingChallengeQuestion { date link question { title titleSlug difficulty topicTags { name } } } }"
			}
		""";

		// Send the request to LeetCode GraphQL API
		String response = webClient.post()
				.uri("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(query)
				.retrieve()
				.bodyToMono(String.class)
				.block();  // Block and get the result synchronously

		// ✅ Print the entire raw JSON response
		System.out.println("🌐 Full JSON Response from LeetCode API:\n");
		System.out.println(response);
		System.out.println("\n------------------------\n");

		// ✅ Parse JSON using Jackson
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response);
		JsonNode data = root.path("data").path("activeDailyCodingChallengeQuestion");

		// ✅ Extract relevant fields
		String title = data.path("question").path("title").asText();
		String difficulty = data.path("question").path("difficulty").asText();
		String link = "https://leetcode.com" + data.path("link").asText();

		// ✅ Print the extracted daily challenge info
		System.out.println("📌 LeetCode Daily Challenge:");
		System.out.println("Title      : " + title);
		System.out.println("Difficulty : " + difficulty);
		System.out.println("Link       : " + link);
	}
}
