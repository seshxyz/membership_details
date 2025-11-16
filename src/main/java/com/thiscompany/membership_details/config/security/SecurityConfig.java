package com.thiscompany.membership_details.config.security;

import com.thiscompany.membership_details.filter.ServiceTokenPreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final ServiceTokenPreFilter serviceTokenPreFilter;
	private final AppAuthenticationEntryPoint appAuthenticationEntryPoint;
	private final AppAccessDeniedHandler accessDeniedHandler;

	public SecurityConfig(ServiceTokenPreFilter serviceTokenPreFilter, AppAuthenticationEntryPoint appAuthenticationEntryPoint, ClientRegistrationRepository clientRegistrationRepository, AppAccessDeniedHandler accessDeniedHandler) {
		this.serviceTokenPreFilter = serviceTokenPreFilter;
		this.appAuthenticationEntryPoint = appAuthenticationEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorizeRequests ->
			{
				authorizeRequests.requestMatchers("/auth").permitAll()
					.requestMatchers("/api/v1/**").authenticated().anyRequest().authenticated();
			}).csrf(AbstractHttpConfigurer::disable)
			.oauth2Client(Customizer.withDefaults())
			.oauth2ResourceServer(
				oauth2ResourceServer -> oauth2ResourceServer
															  .jwt(Customizer.withDefaults())
															  .authenticationEntryPoint(appAuthenticationEntryPoint)
											.accessDeniedHandler(accessDeniedHandler)
			)
			.addFilterBefore(serviceTokenPreFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		return http.build();
	}
}
