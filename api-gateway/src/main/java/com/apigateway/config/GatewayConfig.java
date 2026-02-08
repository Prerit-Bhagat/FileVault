package com.apigateway.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties("gateway")
public class GatewayConfig {

    private Map<String, String> services;

    public Map<String, String> getServices() {
        return services;
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }
}
