package com.example.springbasicnewspeed.domain.auth.controller;

import com.example.springbasicnewspeed.common.response.MessageResponse;
import com.example.springbasicnewspeed.domain.auth.dto.SigninRequest;
import com.example.springbasicnewspeed.domain.auth.dto.SigninResponse;
import com.example.springbasicnewspeed.domain.auth.dto.SignupRequest;
import com.example.springbasicnewspeed.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(MessageResponse.of("회원가입 성공!"));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }
}
