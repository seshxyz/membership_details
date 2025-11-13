package com.thiscompany.membership_details.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.thiscompany.membership_details.filter.ServiceTokenPreFilter;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationContext applicationContext;


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

}


