package com.example.springbasicnewspeed.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostSaveResponse {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("userName")
    private final String userName;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("content")
    private final String content;

    @JsonProperty("postLikedCount")
    private final int postLikedCount;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updatedAt")
    private final LocalDateTime updatedAt;
}
