package com.thiscompany.membership_details.controller;

import com.thiscompany.membership_details.controller.dto.AuthenticationResponse;
import com.thiscompany.membership_details.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@GetMapping("/auth")
	public ResponseEntity<AuthenticationResponse> getAccessToken() {
		return ResponseEntity.ok(authenticationService.getAccessToken());
	}

}
