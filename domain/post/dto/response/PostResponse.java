package com.example.springbasicnewspeed.domain.post.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final int postLikedCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Long id, String userName, String title, String content, int postLikedCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.postLikedCount = postLikedCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
