package com.example.springbasicnewspeed.domain.like.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LikeResponse {
    private final Long targetId;
    private final String username;
    private final boolean liked;
    private final long likeCount;
    private final LocalDateTime likedAt;

    public static LikeResponse of(Long targetId, String username, boolean liked, long likeCount, LocalDateTime likedAt) {
        return LikeResponse.builder()
                .targetId(targetId)
                .username(username)
                .liked(liked)
                .likeCount(likeCount)
                .likedAt(likedAt)
                .build();
    }
}
