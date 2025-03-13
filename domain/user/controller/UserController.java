package com.example.springbasicnewspeed.domain.user.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.user.dto.UserRequest;
import com.example.springbasicnewspeed.domain.user.dto.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인한 유저만 전체 유저를 조회할 수 있는 API
    @GetMapping("/users")
    public List<UserResponse> getUsers(@Auth AuthUser authUser) {
        return userService.findAll();
    }

    // 자기 정보를 자신만 업데이트 가능
    @PutMapping("/users")
    public void update(@Auth AuthUser authUser, @RequestBody UserRequest request) {
        userService.update(authUser, request);
    }
}
