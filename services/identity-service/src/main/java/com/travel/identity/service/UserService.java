package com.travel.identity.service;

import com.travel.identity.dto.response.UserResponse;
import com.travel.identity.dto.request.LoginRequest;
import com.travel.identity.dto.request.RegisterUserRequest;
import com.travel.identity.dto.response.LoginResponse;
import com.travel.identity.dto.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse registerUser(RegisterUserRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse getCurrentUser(Long userId);
    String refreshAccessToken(String refreshToken);
}
