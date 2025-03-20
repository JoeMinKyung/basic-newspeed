package com.example.springbasicnewspeed.domain.user.controller;

import com.example.springbasicnewspeed.common.response.MessageResponse;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.user.dto.request.PasswordUpdateRequest;
import com.example.springbasicnewspeed.domain.user.dto.request.UserRequest;
import com.example.springbasicnewspeed.domain.user.dto.response.UserProfileResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserProfileResponse> update(@Auth AuthUser authUser, @RequestBody UserRequest request) {
        UserProfileResponse response = userService.update(authUser, request);
        return ResponseEntity.ok(response);
    }

    // 유저 비밀번호 수정
    @PatchMapping("/users/password")
    public ResponseEntity<MessageResponse> updatePassword(@Auth AuthUser authUser, @Valid @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(authUser, request);
        return ResponseEntity.ok(MessageResponse.of("비밀번호 변경 성공!"));
    }

    // 다른 유저 프로필 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@Auth AuthUser authUser, @PathVariable Long userId) {
        UserProfileResponse response = userService.getUserProfile(authUser, userId);
        return ResponseEntity.ok(response);
    }

    // 본인 프로필 단건 조회
    @GetMapping("/users/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@Auth AuthUser authUser) {
        UserProfileResponse response = userService.getUserProfile(authUser, authUser.getId());
        return ResponseEntity.ok(response);
    }

    // 유저 본인 탈퇴
    @DeleteMapping("/users")
    public ResponseEntity<MessageResponse> deleteUser(@Auth AuthUser authUser) {
        userService.deleteUser(authUser);
        return ResponseEntity.ok(MessageResponse.of("회원 탈퇴 성공!"));
    }
}
