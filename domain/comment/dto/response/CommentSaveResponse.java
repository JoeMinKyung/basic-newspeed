package com.example.springbasicnewspeed.domain.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponse {

    private final Long id;
    private final Long postId;
    private final String userName;
    private final String content;
    private final int commentLikedCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentSaveResponse(Long id, Long postId, String userName, String content, int commentLikedCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.userName = userName;
        this.content = content;
        this.commentLikedCount = commentLikedCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
