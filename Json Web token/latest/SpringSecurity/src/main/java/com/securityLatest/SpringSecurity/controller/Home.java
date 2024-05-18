package com.securityLatest.SpringSecurity.controller;

import com.securityLatest.SpringSecurity.dto.AuthenticationRequest;
import com.securityLatest.SpringSecurity.dto.AuthenticationResponse;
import com.securityLatest.SpringSecurity.dto.RegisterRequest;
import com.securityLatest.SpringSecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class Home {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(@RequestBody RegisterRequest request)
{
return ResponseEntity.ok(authenticationService.register(request));
}
    @PostMapping("/authenticate")


    public ResponseEntity<AuthenticationResponse>authenticate(@RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
