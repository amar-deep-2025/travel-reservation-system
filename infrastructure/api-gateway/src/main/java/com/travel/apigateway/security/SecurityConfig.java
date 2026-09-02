package com.travel.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange

                        .pathMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/refresh"
                        ).permitAll()

                        .pathMatchers("/actuator/health/**")
                        .permitAll()

                        .pathMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .anyExchange()
                        .authenticated()
                )

//                .oauth2ResourceServer(
//                        oauth2 -> oauth2
//                                .jwt(Customizer.withDefaults())
//
//                )
                .oauth2ResourceServer(
                        oauth2-> oauth2
                                .authenticationEntryPoint(new SecurityErrorHandler())
                                .accessDeniedHandler(new SecurityErrorHandler())
                                .jwt(jwt->jwt
                                        .jwtAuthenticationConverter(new AccessTokenAuthenticationConverter()))
                )
                .build();
    }
}