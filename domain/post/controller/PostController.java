package com.example.springbasicnewspeed.domain.post.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostSaveResponse> savePost(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostSaveRequest request
    ) {
        return ResponseEntity.ok(postService.savePost(authUser, request));
    }
}
