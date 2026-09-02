package com.travel.apigateway.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class SecurityErrorHandler implements ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        return writeError(
                exchange,
                HttpStatus.UNAUTHORIZED,
                "UNAUTHORIZED",
                "Authentication is Required"
        );
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return writeError(
                exchange,
                HttpStatus.FORBIDDEN,
                "FORBIDDEN",
                "You do not have permission to access this resource"

                );
    }

    private Mono<Void> writeError(ServerWebExchange exchange,
                                  HttpStatus status, String error,
                                  String message){
       exchange.getResponse().setStatusCode(status);
       exchange.getResponse().getHeaders()
               .setContentType(MediaType.APPLICATION_JSON);

       String body= """
               {
                "status": %d,
                "error": "%s",
                "message": "%s",
               }
               """.formatted(status.value(), error,message);

       byte[] bytes=body.getBytes(StandardCharsets.UTF_8);

       return exchange.getResponse()
               .writeWith(Mono.just(
                       exchange.getResponse()
                               .bufferFactory().wrap(bytes)
               ));
    }
}
