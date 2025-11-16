package com.thiscompany.membership_details.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

	private String id = "keycloak";
	private String host = "localhost";
	private String port = "8100";
	private String adminUsername = "admin";
	private String adminPassword;
	private String adminGrantType = "password";
	private String adminAccessToken;
	private String adminTokenPath = "/realms/master/protocol/openid-connect/token";
	private String clientsSettingsPath = "/admin/realms/app_realm/clients";
	private String grantType = "client_credentials";
	private String clientId = "keycloak-auth-client";
	private String clientSecret;
	private String clientScope = "app_client";
	private String realmAuthUri = "/realms/app_realm/protocol/openid-connect/auth";
	private String realmTokenUri = "/realms/app_realm/protocol/openid-connect/token";

	public String getHostPath(){
		return "http://"+host+":"+port;
	}

	@Override
	public String toString() {
		return "KeycloakProperties{" +
				   "host='" + host + '\'' +
				   ", port='" + port + '\'' +
				   ", adminUsername='" + adminUsername + '\'' +
				   ", adminPassword='" + adminPassword + '\'' +
				   ", adminGrantType='" + adminGrantType + '\'' +
				   ", adminAccessToken='" + adminAccessToken + '\'' +
				   ", adminTokenPath='" + adminTokenPath + '\'' +
				   ", clientsSettingsPath='" + clientsSettingsPath + '\'' +
				   ", clientGrantType='" + grantType + '\'' +
				   ", clientId='" + clientId + '\'' +
				   ", clientSecret='" + clientSecret + '\'' +
				   ", clientScope='" + clientScope + '\'' +
				   ", realmAuthUri='" + realmAuthUri + '\'' +
				   ", realmTokenUri='" + realmTokenUri + '\'' +
				   '}';
	}
}
