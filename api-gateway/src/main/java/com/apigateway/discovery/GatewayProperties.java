package com.apigateway.discovery;

import java.util.List;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("gateway")
public class GatewayProperties {
    private List<String> services;
    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
