package com.example.springbasicnewspeed.domain.follow.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowResponse {

    private final Long id;
    private final String username;
    private final boolean followed;
    private final LocalDateTime followAt;

    public FollowResponse(Long id, String username, boolean followed, LocalDateTime followAt) {
        this.id = id;
        this.username = username;
        this.followed = followed;
        this.followAt = followAt;
    }
}
