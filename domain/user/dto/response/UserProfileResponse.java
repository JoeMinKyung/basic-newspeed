package com.example.springbasicnewspeed.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserProfileResponse {

    private final String email;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserProfileResponse(String email, String userName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
