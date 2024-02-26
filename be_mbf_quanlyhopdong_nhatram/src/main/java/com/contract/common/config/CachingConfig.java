package com.contract.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }
    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

//    @Bean
//    public CacheManager caffeine() {
//        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
//        return caffeineCacheManager;
//    }

//    private Caffeine<Object, Object> caffeineCacheBuilder() {
//        return Caffeine.newBuilder().
//                initialCapacity(initialCapacity).
//                maximumSize(maxCapacity).
//                expireAfterAccess(Duration.parse(ttl)).
//                recordStats();
//    }
}
