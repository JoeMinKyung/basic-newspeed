package com.example.springbasicnewspeed.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostLikeResponse {

    private final Long postId;
    private final String username;
    private final boolean currentlyPostLiked;
    private final int postLikedCount;

    public PostLikeResponse(Long postId, String username, boolean currentlyPostLiked, int postLikedCount) {
        this.postId = postId;
        this.username = username;
        this.currentlyPostLiked = currentlyPostLiked;
        this.postLikedCount = postLikedCount;
    }
}
