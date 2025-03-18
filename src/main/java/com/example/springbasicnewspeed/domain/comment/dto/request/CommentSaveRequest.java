package com.example.springbasicnewspeed.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentSaveRequest {

    @NotBlank
    private String content;
}
