package com.example.springbasicnewspeed.domain.comment.controller;

import com.example.springbasicnewspeed.common.response.MessageResponse;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentUpdateRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentResponse;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.service.CommentService;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    // 댓글 수정 (작성자 본인만 가능)
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @Auth AuthUser authUser,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse updatedComment =  commentService.updateComment(authUser, postId, commentId, request);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제 (작성자 본인만 가능)
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @Auth AuthUser authUser,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(authUser, commentId);
        return ResponseEntity.ok(MessageResponse.of("댓글이 삭제되었습니다!"));
    }
}
