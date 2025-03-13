package com.example.springbasicnewspeed.domain.user.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.user.dto.request.UserRequest;
import com.example.springbasicnewspeed.domain.user.dto.response.UserProfileResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인한 유저만 전체 유저를 조회할 수 있는 API -> admin만 가능하게 수정하기
    @GetMapping("/users")
    public List<UserResponse> getUsers(@Auth AuthUser authUser) {
        return userService.findAll();
    }

    // 자기 정보를 자신만 업데이트 가능
    @PatchMapping("/users")
    public UserProfileResponse update(@Auth AuthUser authUser, @RequestBody UserRequest request) {
        return userService.update(authUser, request);
    }

    // 유저 프로필 단건 조회
    @GetMapping("/users/{userId}")
    public UserProfileResponse getUser(@Auth AuthUser authUser, @PathVariable Long userId) {
        return userService.getUserProfile(authUser, userId);
    }
}
