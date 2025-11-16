package com.thiscompany.membership_details.service.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.thiscompany.membership_details.config.KeycloakProperties;
import com.thiscompany.membership_details.controller.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final RestClient restClient;
	private final KeycloakProperties keycloakProperties;

	@GetMapping("/auth")
	public AuthenticationResponse getAccessToken() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", keycloakProperties.getGrantType());
		body.add("client_id", keycloakProperties.getClientId());
		body.add("client_secret", keycloakProperties.getClientSecret());

		JsonNode node = restClient.post()
							.uri(keycloakProperties.getRealmTokenUri())
							.headers(httpHeaders ->
										 httpHeaders.add("Authorization", "Bearer "+keycloakProperties.getAdminAccessToken())
							)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.body(body)
							.retrieve().toEntity(JsonNode.class).getBody();
		String accessToken = node.get("access_token").asText();

		return new AuthenticationResponse(accessToken);
	}
}
