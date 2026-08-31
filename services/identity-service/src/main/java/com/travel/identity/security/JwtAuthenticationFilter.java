package com.travel.identity.security;

import com.travel.identity.entity.User;
import com.travel.identity.exception.InvalidCredentialsException;
import com.travel.identity.exception.ResourceNotFoundException;
import com.travel.identity.repository.UserRepository;
import com.travel.identity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository){
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/auth/login")
                || request.getServletPath().equals("/api/auth/register")
                || request.getServletPath().equals("/api/auth/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader=request.getHeader("Authorization");
        System.out.println(authHeader);

        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            System.out.println("No Authorization header or not Bearer");
            return;
        }
        String token =authHeader.substring(7);
        System.out.println("token Length "+token.length());
        System.out.println("Token chars "+token.chars().filter(ch->ch=='.').count());
        System.out.println(token);
        //jwt validation
        try{
            String userId=jwtService.extractUserId(token);
            System.out.println("userId"+userId);
            User user=userRepository.findByIdWithRoles(Long.valueOf(userId)).orElseThrow(()->new ResourceNotFoundException("User not found"));
            if (!user.isEnabled()){
                throw new InvalidCredentialsException("User account is disabled");
            }
            List<SimpleGrantedAuthority> authorities=user.getRoles()
                    .stream()
                    .map(role->new SimpleGrantedAuthority(role.getName()))
                    .toList();
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
