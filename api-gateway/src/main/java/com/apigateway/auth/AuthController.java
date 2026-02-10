package com.apigateway.auth;

import java.util.Map;
import java.util.Optional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;

@Controller("/auth")
public class AuthController {

    @Inject
    JwtTokenGenerator tokenGenerator;

    @Post("/login")
    public HttpResponse<?> login(@Body Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        // SIMPLE DEMO AUTH (hardcoded)
        if (!"admin".equals(username) || !"password".equals(password)) {
            return HttpResponse.unauthorized();
        }

        Authentication authentication = Authentication.build(username);

        Optional<String> token = tokenGenerator.generateToken(authentication, null);

        return token
                .map(t -> HttpResponse.ok(Map.of("access_token", t)))
                .orElseGet(HttpResponse::serverError);
    }
}
