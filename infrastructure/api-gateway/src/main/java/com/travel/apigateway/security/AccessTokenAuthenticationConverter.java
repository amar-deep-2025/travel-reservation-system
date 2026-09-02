package com.travel.apigateway.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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

        List<String> roles=jwt.getClaimAsStringList("role");

       List<SimpleGrantedAuthority> authorities=new ArrayList<>();

       if (roles!=null){
           for (String role:roles){
               authorities.add(new SimpleGrantedAuthority(role));
           }
       }

        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }
}