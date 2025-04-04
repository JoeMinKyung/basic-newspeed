package com.example.springbasicnewspeed.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequest {

    @NotBlank
    private String content;
}
