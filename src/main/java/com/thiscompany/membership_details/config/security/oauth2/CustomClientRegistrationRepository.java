package com.thiscompany.membership_details.config.security.oauth2;

import com.thiscompany.membership_details.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomClientRegistrationRepository implements ClientRegistrationRepository {

	private final KeycloakProperties keycloakProperties;

	@Override
	public ClientRegistration findByRegistrationId(String registrationId) {
		return ClientRegistration.withRegistrationId(keycloakProperties.getClientSecret())
				   .clientId(keycloakProperties.getClientId())
				   .clientSecret(keycloakProperties.getClientSecret())
				   .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
				   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				   .scope(keycloakProperties.getClientScope())
				   .authorizationUri(keycloakProperties.getRealmAuthUri())
				   .tokenUri(keycloakProperties.getRealmTokenUri())
				   .build();
	}
}
