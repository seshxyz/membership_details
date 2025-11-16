package com.thiscompany.membership_details.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiscompany.membership_details.exception.global_handler.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final MessageSource messageSource;
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

		ProblemDetail problemDetail = GlobalExceptionHandler.createProblemDetail(
			messageSource,
			HttpStatus.UNAUTHORIZED,
			"error.401",
			"error.401",
			null
		);

		objectMapper.writeValue(response.getWriter(), problemDetail);
	}
}
