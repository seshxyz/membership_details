package com.thiscompany.membership_details.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${application.caches.user-details-cache}")
    private String userCache;

    @Value("${application.caches.membership-result-cache}")
    private String membershipCache;

    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine.newBuilder()
             .expireAfterWrite(Duration.ofSeconds(20))
             .initialCapacity(100)
             .maximumSize(100);
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine());
        cacheManager.setCacheNames(List.of(userCache, membershipCache));
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }
}

