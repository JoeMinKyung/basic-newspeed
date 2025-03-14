package com.example.springbasicnewspeed.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
