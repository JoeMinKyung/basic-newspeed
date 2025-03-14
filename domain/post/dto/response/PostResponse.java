package com.example.springbasicnewspeed.domain.post.dto.response;

import com.example.springbasicnewspeed.domain.user.dto.response.UserNameResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final UserNameResponse userNameResponse;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Long id, UserNameResponse userNameResponse, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userNameResponse = userNameResponse;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
