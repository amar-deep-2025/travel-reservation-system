package com.travel.identity.service.impl;

import com.travel.identity.dto.request.RegisterUserRequest;
import com.travel.identity.dto.response.RegisterUserResponse;
import com.travel.identity.entity.Role;
import com.travel.identity.entity.User;
import com.travel.identity.repository.RoleRepository;
import com.travel.identity.repository.UserRepository;
import com.travel.identity.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
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
}
