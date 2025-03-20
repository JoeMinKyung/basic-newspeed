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

    @JsonProperty("id")  // 이 어노테이션을 추가해 봅니다
    private final Long id;

    @JsonProperty("userName")  // 이 어노테이션을 추가해 봅니다
    private final String userName;

    @JsonProperty("title")  // 이 어노테이션을 추가해 봅니다
    private final String title;

    @JsonProperty("content")  // 이 어노테이션을 추가해 봅니다
    private final String content;

    @JsonProperty("postLikedCount")  // 이 어노테이션을 추가해 봅니다
    private final int postLikedCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updatedAt")
    private final LocalDateTime updatedAt;
}
