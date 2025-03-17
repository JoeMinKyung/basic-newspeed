package com.example.springbasicnewspeed.domain.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentLikeResponse {

    private final Long commentId;
    private final String userName;
    private final boolean currentlyCommentLiked;
    private final int commentLikedCount;

    public CommentLikeResponse(Long commentId, String userName, boolean currentlyCommentLiked, int commentLikedCount) {
        this.commentId = commentId;
        this.userName = userName;
        this.currentlyCommentLiked = currentlyCommentLiked;
        this.commentLikedCount = commentLikedCount;
    }
}
