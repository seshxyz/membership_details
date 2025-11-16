package com.thiscompany.membership_details.controller;

import com.thiscompany.membership_details.controller.dto.AuthenticationResponse;
import com.thiscompany.membership_details.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Операция аутентификации", description = "Эндпоинт позволяет аутентифицироваться (без ввода паролей) " +
														 "и получить токен доступа, выдаваемый провайдером (keycloak) по Ouath2 Grant Credentials flow")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@Operation(
		summary = "Произвести аутентификацию",
		description = "Возвращаёт токен доступа"
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200", description = "Успешное получение",
			content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
		),
		@ApiResponse(
			responseCode = "401", description = "Не авторизован",
			content = @Content(schema = @Schema(implementation = ProblemDetail.class))
		),
		@ApiResponse(
				responseCode = "403", description = "Доступ запрещён",
				content = @Content(schema = @Schema(implementation = ProblemDetail.class))
		)})
	@GetMapping("/auth")
	public ResponseEntity<AuthenticationResponse> getAccessToken() {
		return ResponseEntity.ok(authenticationService.getAccessToken());
	}

}
