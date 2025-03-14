package com.example.springbasicnewspeed.domain.post.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PostSaveResponse {

    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostSaveResponse(Long id, String userName, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
