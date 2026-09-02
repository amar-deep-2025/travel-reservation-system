package com.travel.apigateway.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class AccessTokenAuthenticationConverter
        implements Converter<Jwt, Mono<JwtAuthenticationToken>> {

    @Override
    public Mono<JwtAuthenticationToken> convert(Jwt jwt) {

        String tokenType = jwt.getClaimAsString("tokenType");

        if (!"access".equals(tokenType)) {
            return Mono.error(
                    new BadCredentialsException("Invalid token type")
            );
        }

        return Mono.just(new JwtAuthenticationToken(jwt));
    }
}