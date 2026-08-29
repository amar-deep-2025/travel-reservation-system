package com.travel.identity.controller.auth;

import com.travel.identity.dto.request.LoginRequest;
import com.travel.identity.dto.request.RegisterUserRequest;
import com.travel.identity.dto.response.LoginResponse;
import com.travel.identity.dto.response.RegisterUserResponse;
import com.travel.identity.service.UserService;
import jakarta.validation.Valid;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {

        RegisterUserResponse response = userService.registerUser(request);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        LoginResponse response =userService.login(request);

        return ResponseEntity.ok(response);
    }
}
