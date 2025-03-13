package com.example.springbasicnewspeed.domain.auth.service;

import com.example.springbasicnewspeed.config.JwtUtil;
import com.example.springbasicnewspeed.config.PasswordEncoder;
import com.example.springbasicnewspeed.domain.auth.dto.SigninRequest;
import com.example.springbasicnewspeed.domain.auth.dto.SigninResponse;
import com.example.springbasicnewspeed.domain.auth.dto.SignupRequest;
import com.example.springbasicnewspeed.domain.user.dto.UserResponse;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {

        if (!Objects.equals(request.getPassword(), request.getPasswordCheck())) {
            throw new IllegalStateException("비밀번호와 비밀번호 확인이 같지 않습니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest request) {
        UserResponse userResult = userService.findByEmail(request.getEmail());

        String bearJwt = jwtUtil.createToken(userResult.getId(), userResult.getEmail());
        return new SigninResponse(bearJwt);
    }
}
