package com.thiscompany.membership_details.config.security.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.thiscompany.membership_details.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientSecretOnStartupResolver {

	private final RestClient restClient;
	private final KeycloakProperties keycloakProperties;


	@Primary
	@EventListener(ApplicationReadyEvent.class)
	public String receiveClientId() {
		return keycloakProperties.getClientId();
	}

	@EventListener(ApplicationReadyEvent.class)
	public String receiveClientSecret() throws JsonProcessingException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.put("Content-Type", List.of(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

		MultiValueMap<String, String> body1 = new LinkedMultiValueMap<>();
		body1.put("client_id", List.of("admin-cli"));
		body1.put("grant_type", List.of("password"));
		body1.put("username", List.of("admin"));
		body1.put("password", List.of( "password"));

		String accessToken = getAccessToken(keycloakProperties.getAdminTokenPath(), headers, body1);

		headers.put("Authorization", List.of("Bearer " + accessToken));
		String secret = getSpecifiedClientDetails(
			accessToken, keycloakProperties.getClientsSettingsPath(), headers
		);

		keycloakProperties.setClientSecret(secret);

		return secret;
	}

	private String getAccessToken(String uri, MultiValueMap<String, String> headers,
								  MultiValueMap<String, String> body) throws JsonProcessingException {
		String accessToken = "";
		JsonNode response = restClient.post()
						   .uri(uri)
						   .accept(MediaType.APPLICATION_JSON)
						   .body(body)
						   .headers((map)->map.addAll(headers))
						   .retrieve().toEntity(JsonNode.class).getBody();
			accessToken = response.get("access_token").asText();

			keycloakProperties.setAdminAccessToken(accessToken);

		return accessToken;
	}

	private String getSpecifiedClientDetails(String accessToken, String uri, MultiValueMap<String, String> headers) throws JsonProcessingException {
		String clientSecret = "";
		JsonNode response = restClient.get()
							  .uri(uri)
								.headers(httpHeaders -> httpHeaders.addAll(headers))
							  .accept(MediaType.APPLICATION_JSON)
							  .retrieve().toEntity(JsonNode.class).getBody();

		for(JsonNode node : response) {
			String element = node.get("clientId").asText();
			if(element != null && element.equals(keycloakProperties.getClientId())){
					clientSecret = node.get("secret").asText();
			}
		}
		return clientSecret;
	}

}
