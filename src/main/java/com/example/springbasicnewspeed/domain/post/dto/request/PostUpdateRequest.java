package com.example.springbasicnewspeed.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;
    private String content;

    public PostUpdateRequest(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }
}
