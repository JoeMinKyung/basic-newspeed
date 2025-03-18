package com.example.springbasicnewspeed.domain.follow.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.follow.dto.response.FollowResponse;
import com.example.springbasicnewspeed.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우/언팔로우 토글
    @PostMapping("/users/{userId}/follow")
    public FollowResponse toggleFollow(@Auth AuthUser authUser, @PathVariable Long userId) {
        return followService.toggleFollow(authUser, userId);
    }
}
