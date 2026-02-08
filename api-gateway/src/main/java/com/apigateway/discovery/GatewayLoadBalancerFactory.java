package com.apigateway.discovery;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.micronaut.context.annotation.Factory;
import io.micronaut.discovery.DiscoveryClient;
import io.micronaut.http.client.LoadBalancer;
import io.micronaut.http.client.loadbalance.DiscoveryClientLoadBalancerFactory;
import jakarta.inject.Singleton;

@Factory
public class GatewayLoadBalancerFactory {
    @Singleton
    Map<String, LoadBalancer> serviceLoadBalancers(
            DiscoveryClient discoveryClient,
            GatewayProperties properties) {

        DiscoveryClientLoadBalancerFactory factory =
                new DiscoveryClientLoadBalancerFactory(discoveryClient);

        Map<String, LoadBalancer> map = new HashMap<>();
        for (String service : properties.getServices()) {
            map.put(service, factory.create(service));
        }

        return Collections.unmodifiableMap(map);
    }
}
