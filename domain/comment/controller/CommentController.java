package com.example.springbasicnewspeed.domain.comment.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.service.CommentService;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @Auth AuthUser authUser,
            @PathVariable Long postId,
            @Valid @RequestBody CommentSaveRequest request) {

        return ResponseEntity.ok(commentService.saveComment(authUser, postId, request));
    }
}
