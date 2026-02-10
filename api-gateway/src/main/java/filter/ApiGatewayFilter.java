// // package filter;

// // import io.micronaut.http.*;
// // import io.micronaut.http.annotation.Filter;
// // import io.micronaut.http.client.ProxyHttpClient;
// // import io.micronaut.http.filter.HttpServerFilter;
// // import io.micronaut.http.filter.ServerFilterChain;
// // import io.micronaut.discovery.ServiceInstance;
// // import io.micronaut.http.client.LoadBalancer;

// // import org.reactivestreams.Publisher;
// // import reactor.core.publisher.Mono;

// // import java.net.URI;
// // import java.util.Map;


// // @Filter("/**")
// // public class ApiGatewayFilter implements HttpServerFilter {

// //     private final Map<String, LoadBalancer> loadBalancers;
// //     private final ProxyHttpClient proxyClient;

// //     public ApiGatewayFilter(
// //             Map<String, LoadBalancer> loadBalancers,
// //             ProxyHttpClient proxyClient) {
// //         this.loadBalancers = loadBalancers;
// //         this.proxyClient = proxyClient;
// //     }

// //     @Override
// //     public Publisher<MutableHttpResponse<?>> doFilter(
// //             HttpRequest<?> request,
// //             ServerFilterChain chain) {

// //         String[] parts = request.getPath().split("/", 3);
// //         if (parts.length < 2) {
// //             return chain.proceed(request);
// //         }

// //         String serviceId = parts[1];
// //         LoadBalancer lb = loadBalancers.get(serviceId);

// //         if (lb == null) {
// //             return Mono.just(HttpResponse.notFound());
// //         }

// //         return Mono.from(lb.select())
// //                 .map(ServiceInstance::getURI)
// //                 .flatMap(uri -> {
// //                     String newPath = parts.length == 3 ? "/" + parts[2] : "/";
// //                     URI target = uri.resolve(newPath);

// //                     MutableHttpRequest<?> proxyRequest =
// //         HttpRequest.create(request.getMethod(), target.toString())
// //                 .headers(h -> request.getHeaders()
// //         .forEach((name, values) -> values.forEach(value -> h.add(name, value))));


// //                     return Mono.from(proxyClient.proxy(proxyRequest));
// //                 });
// //     }
// // }
// package filter;

// import com.apigateway.config.GatewayConfig;
// import io.micronaut.http.*;
// import io.micronaut.http.annotation.Filter;
// import io.micronaut.http.client.ProxyHttpClient;
// import io.micronaut.http.filter.HttpServerFilter;
// import io.micronaut.http.filter.ServerFilterChain;

// import org.reactivestreams.Publisher;
// import reactor.core.publisher.Mono;

// import java.net.URI;

// @Filter("/**")
// public class ApiGatewayFilter implements HttpServerFilter {

//     private final GatewayConfig config;
//     private final ProxyHttpClient proxyClient;

//     public ApiGatewayFilter(GatewayConfig config,
//                             ProxyHttpClient proxyClient) {
//         this.config = config;
//         this.proxyClient = proxyClient;
//     }

//     @Override
//     public Publisher<MutableHttpResponse<?>> doFilter(
//             HttpRequest<?> request,
//             ServerFilterChain chain) {

//         String[] parts = request.getPath().split("/", 3);

//         // No service name in URL
//         if (parts.length < 2) {
//             return chain.proceed(request);
//         }

//         String serviceId = parts[1];
//         String baseUrl = config.getServices().get(serviceId);

//         // Unknown service
//         if (baseUrl == null) {
//             return Mono.just(HttpResponse.notFound("Unknown service: " + serviceId));
//         }

//         String newPath = parts.length == 3 ? "/" + parts[2] : "/";
//         URI targetUri = URI.create(baseUrl + newPath);

//         MutableHttpRequest<?> proxyRequest =
//                 HttpRequest.create(request.getMethod(), targetUri.toString())
//                 .headers(h -> request.getHeaders()
//         .forEach((name, values) -> values.forEach(value -> h.add(name, value))));

//         return Mono.from(proxyClient.proxy(proxyRequest));
//     }
// }
package filter;

import com.apigateway.config.GatewayConfig;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.ProxyHttpClient;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.net.URI;

@Filter("/**")
public class ApiGatewayFilter implements HttpServerFilter {

    private final GatewayConfig config;
    private final ProxyHttpClient proxyClient;

    public ApiGatewayFilter(GatewayConfig config,
                            ProxyHttpClient proxyClient) {
        this.config = config;
        this.proxyClient = proxyClient;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(
            HttpRequest<?> request,
            ServerFilterChain chain) {
        
        // 🔑 IMPORTANT: allow auth endpoints to be handled locally
        if (request.getPath().startsWith("/auth")) {
            return chain.proceed(request);
        }

        String[] parts = request.getPath().split("/", 3);

        // No service name → continue
        if (parts.length < 2) {
            return chain.proceed(request);
        }

        String serviceId = parts[1];
        String baseUrl = config.getServices().get(serviceId);

        if (baseUrl == null) {
            return Mono.just(HttpResponse.notFound("Unknown service: " + serviceId));
        }

        String newPath = parts.length == 3 ? "/" + parts[2] : "/";
        URI targetUri = URI.create(baseUrl + newPath);

        //  KEY FIX: mutate URI, DO NOT rebuild body
        MutableHttpRequest<?> proxiedRequest =
                request.mutate().uri(targetUri);

        return Mono.from(proxyClient.proxy(proxiedRequest));
    }
}
