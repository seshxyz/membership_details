package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Тело ответа на аутентификацию")
public record AuthenticationResponse(

	@Schema(
		description = "Токен доступа к API",
		example = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6I...",
		type = "String"
	)
	@JsonProperty(value = "access_token")
	String accessToken
) {
}
