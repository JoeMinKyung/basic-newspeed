package com.example.springbasicnewspeed.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String password;
    private final String userName;
    private final int followerCount;
    private final int followingCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponse(Long id, String email, String userName, String password, int followerCount, int followingCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
