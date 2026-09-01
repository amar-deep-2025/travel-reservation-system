package com.travel.identity.service;

import com.travel.identity.entity.User;

public interface RefreshTokenService {

    void saveRefreshToken(User user, String refreshToken);
    boolean isValid(String refreshToken);
    void revokeToken(String refreshToken);
}
