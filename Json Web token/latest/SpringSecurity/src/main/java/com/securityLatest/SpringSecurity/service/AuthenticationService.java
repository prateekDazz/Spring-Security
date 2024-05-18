package com.securityLatest.SpringSecurity.service;

import com.securityLatest.SpringSecurity.dto.AuthenticationRequest;
import com.securityLatest.SpringSecurity.dto.AuthenticationResponse;
import com.securityLatest.SpringSecurity.dto.RegisterRequest;
import com.securityLatest.SpringSecurity.entity.Role;
import com.securityLatest.SpringSecurity.entity.User;
import com.securityLatest.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().firstName(request.getFirstname())
                .lastName(request.getLastname()).email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalArgumentException("invalid user"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
