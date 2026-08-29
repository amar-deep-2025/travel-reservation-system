package com.travel.identity.service.impl;

import com.travel.identity.dto.response.UserResponse;
import com.travel.identity.dto.request.LoginRequest;
import com.travel.identity.dto.request.RegisterUserRequest;
import com.travel.identity.dto.response.LoginResponse;
import com.travel.identity.dto.response.RegisterUserResponse;
import com.travel.identity.entity.Role;
import com.travel.identity.entity.User;
import com.travel.identity.exception.InvalidCredentialsException;
import com.travel.identity.exception.ResourceAlreadyExistsException;
import com.travel.identity.exception.ResourceNotFoundException;
import com.travel.identity.repository.RoleRepository;
import com.travel.identity.repository.UserRepository;
import com.travel.identity.service.JwtService;
import com.travel.identity.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())){
            throw new ResourceAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        Role role=roleRepository.findByName("ROLE_USER")
                .orElseThrow(()->new RuntimeException("Default role ROLE_USER not found"));

        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.getRoles().add(role);

        User savedUser=userRepository.save(user);

        RegisterUserResponse response=new RegisterUserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user=userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(),request.getUsernameOrEmail())
                .orElseThrow(()->new InvalidCredentialsException("Invalid username/email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public UserResponse getCurrentUser(Long userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role->role.getName()).toList()
        );
    }
}
