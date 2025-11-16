package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticationResponse(
	@JsonProperty(value = "access_token")
	String accessToken
) {
}
