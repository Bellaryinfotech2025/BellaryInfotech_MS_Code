package com.bellaryinfotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bellaryinfotech.model.TenantPreferences;
import com.bellaryinfotech.repo.TenantPreferencesRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TenantResolutionService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TenantResolutionService.class);
    private static final List<String> VALID_ENVIRONMENTS = Arrays.asList("dev", "qa", "mega_prod", "mega_dev");
    private static final Integer DEFAULT_TENANT_ID = 1;
    
    @Autowired
    private TenantPreferencesRepository tenantPreferencesRepository;
    
    /**
     * Resolves the tenant ID based on the current request URL
     * 
     * @param request The HTTP request
     * @return The resolved tenant ID
     */
    public Integer resolveTenantId(HttpServletRequest request) {
        try {
            if (request == null) {
                LOG.warn("Request is null, using default tenant ID");
                return DEFAULT_TENANT_ID;
            }
            
            // Extract the host from the request
            String host = extractHost(request);
            if (host == null || host.isEmpty()) {
                LOG.warn("Could not extract host from request, using default tenant ID");
                return DEFAULT_TENANT_ID;
            }
            
            LOG.info("Resolving tenant ID for host: {}", host);
            
            // Try to find a tenant preference with matching URL
            List<TenantPreferences> matchingPreferences = tenantPreferencesRepository.findByAppUrlContainingAndEnvironment(host);
            
            if (matchingPreferences.isEmpty()) {
                LOG.warn("No matching tenant preferences found for host: {}, using default tenant ID", host);
                return DEFAULT_TENANT_ID;
            }
            
            // If we have multiple matches, try to determine the environment
            if (matchingPreferences.size() > 1) {
                String environment = determineEnvironment(host);
                if (environment != null) {
                    Optional<TenantPreferences> specificMatch = tenantPreferencesRepository
                            .findByAppUrlContainingAndSpecificEnvironment(host, environment);
                    
                    if (specificMatch.isPresent()) {
                        LOG.info("Found specific tenant match for host: {} and environment: {}", host, environment);
                        return specificMatch.get().getTenantId().intValue();
                    }
                }
            }
            
            // Return the first match if we have one
            TenantPreferences preference = matchingPreferences.get(0);
            LOG.info("Using tenant ID: {} for host: {}", preference.getTenantId(), host);
            return preference.getTenantId().intValue();
            
        } catch (Exception e) {
            LOG.error("Error resolving tenant ID, using default tenant ID", e);
            return DEFAULT_TENANT_ID;
        }
    }
    
    /**
     * Extracts the host from the request
     */
    private String extractHost(HttpServletRequest request) {
        String host = request.getHeader("Host");
        if (host == null || host.isEmpty()) {
            // Try to get from the request URL
            StringBuffer url = request.getRequestURL();
            if (url != null && url.length() > 0) {
                String urlStr = url.toString();
                try {
                    java.net.URL parsedUrl = new java.net.URL(urlStr);
                    host = parsedUrl.getHost();
                } catch (Exception e) {
                    LOG.warn("Could not parse URL: {}", urlStr, e);
                }
            }
        }
        return host;
    }
    
    /**
     * Tries to determine the environment from the host
     */
    private String determineEnvironment(String host) {
        String hostLower = host.toLowerCase();
        
        if (hostLower.contains("dev")) {
            return "dev";
        } else if (hostLower.contains("qa")) {
            return "qa";
        } else if (hostLower.contains("prod")) {
            return "mega_prod";
        } else if (hostLower.contains("mega")) {
            return "mega_dev";
        }
        
        return null;
    }
}