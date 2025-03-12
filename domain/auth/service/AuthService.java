package com.example.springbasicnewspeed.domain.auth.service;

import com.example.springbasicnewspeed.config.JwtUtil;
import com.example.springbasicnewspeed.domain.auth.dto.SigninRequest;
import com.example.springbasicnewspeed.domain.auth.dto.SigninResponse;
import com.example.springbasicnewspeed.domain.auth.dto.SignupRequest;
import com.example.springbasicnewspeed.domain.auth.dto.SignupResponse;
import com.example.springbasicnewspeed.domain.user.dto.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        UserResponse saveResult = userService.save(request.getEmail());

        String bearJwt = jwtUtil.createToken(saveResult.getId(), saveResult.getEmail());
        return new SignupResponse(bearJwt);
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest request) {
        UserResponse userResult = userService.findByEmail(request.getEmail());

        String bearJwt = jwtUtil.createToken(userResult.getId(), userResult.getEmail());
        return new SigninResponse(bearJwt);
    }
}
