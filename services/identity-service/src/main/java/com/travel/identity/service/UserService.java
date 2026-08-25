package com.travel.identity.service;

import com.travel.identity.dto.request.RegisterUserRequest;
import com.travel.identity.dto.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse registerUser(RegisterUserRequest request);

}
