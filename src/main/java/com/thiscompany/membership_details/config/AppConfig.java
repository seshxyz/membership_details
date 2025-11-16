package com.thiscompany.membership_details.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.thiscompany.membership_details.filter.ServiceTokenPreFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestClient;

import java.util.Collections;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationContext applicationContext;
    private final KeycloakProperties keycloakProperties;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new ParameterNamesModule());
    }

    @Bean
    public FilterRegistrationBean<ServiceTokenPreFilter> serviceTokenFilterRegisteredBean() {
        ServiceTokenPreFilter filter = applicationContext.getBean(ServiceTokenPreFilter.class);
        FilterRegistrationBean<ServiceTokenPreFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/v1/check-user-membership");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.getGlobalOptions().put("CamelJacksonEnableTypeConverter", "true");
                camelContext.getGlobalOptions().put("CamelJacksonTypeConverterToPojo", "true");
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {}
        };
    }

    @Lazy
    @Bean
    public RestClient keycloakRestClient() {
        return RestClient.builder()
                   .baseUrl(keycloakProperties.getHostPath())
                   .build();
    }

    @Bean
    OpenAPI swaggerConfig() {
        Contact contact = new Contact();
        contact.setName("Alexander");
        contact.setUrl("https://github.com/seshxyz");

        OpenAPI swagger = new OpenAPI();
        swagger.setInfo(getInfo(contact));
        swagger.servers(Collections.emptyList());
        swagger.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme()));
        return swagger;
    }

    private static Info getInfo(Contact contact) {
        Info info = new Info();
        info.setTitle("Membership details API");
        info.setDescription("Интеграционное API для получения сведений о пользователи, является ли он участником группы ВКонтакте. " +
                                "API взаимодействует с API Вконтакте с помощью Apache Camel роутов. " +
                                "Доступ к энпдоинту для запроса - защищён Spring security + Keycloak в качестве провайдера авторизации Ouath (client credential flow).");
        info.setContact(contact);
        info.setVersion("1.0.0");
        return info;
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                   .type(SecurityScheme.Type.HTTP)
                   .in(SecurityScheme.In.HEADER)
                   .scheme("bearer")
                   .bearerFormat("JWT");
    }

}


