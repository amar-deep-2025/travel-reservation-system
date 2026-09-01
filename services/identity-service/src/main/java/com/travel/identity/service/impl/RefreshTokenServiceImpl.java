package com.travel.identity.service.impl;

import com.travel.identity.entity.RefreshToken;
import com.travel.identity.entity.User;
import com.travel.identity.repository.RefreshTokenRepository;
import com.travel.identity.service.JwtService;
import com.travel.identity.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void saveRefreshToken(User user, String refreshToken) {

        RefreshToken entity=new RefreshToken();
        entity.setUser(user);
        entity.setTokenHash(jwtService.hashToken(refreshToken));
        entity.setExpiresAt(jwtService.extractExpiration(refreshToken));

        refreshTokenRepository.save(entity);

    }

    @Override
    public boolean isValid(String refreshToken) {

        String tokenHash= jwtService.hashToken(refreshToken);
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .map(entity-> !entity.isRevoked() && entity.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);

    }

    @Override
    public void revokeToken(String refreshToken) {

        String tokenHash= jwtService.hashToken(refreshToken);

        refreshTokenRepository.findByTokenHash(tokenHash)
                .ifPresent(entity->{
                    entity.setRevoked(true);
                    refreshTokenRepository.save(entity);
                });
    }
}
