package com.bellaryinfotech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 🔐 Allow only these domains
        config.setAllowedOriginPatterns(Arrays.asList(
            "http://dev.bellaryinfotech.com",
            "http://uat.bellaryinfotech.com",
            "http://prod.bellaryinfotech.com"
        ));

        // Allow all origins - note: use allowedOriginPatterns when allowCredentials = true
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // Allow credentials (cookies, auth headers, etc.)
        config.setAllowCredentials(true);

        // Allow all headers
        config.setAllowedHeaders(Arrays.asList("*"));

        // Allow all methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Optional: Set how long preflight requests can be cached
        config.setMaxAge(3600L);

        // Apply config to all routes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
