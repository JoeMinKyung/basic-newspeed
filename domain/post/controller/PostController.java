package com.example.springbasicnewspeed.domain.post.controller;

import com.example.springbasicnewspeed.common.response.MessageResponse;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.like.service.CommentLikeService;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.request.PostUpdateRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PageResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 포스트 생성
    @PostMapping("/posts")
    public ResponseEntity<PostSaveResponse> savePost(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostSaveRequest request
    ) {
        return ResponseEntity.ok(postService.savePost(authUser, request));
    }

    // 포스트 조회
    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PostResponse>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<PostResponse> response = postService.getPosts(page, size);
        return ResponseEntity.ok(response);
    }

    // 포스트 수정 (작성자 본인만 가능)
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @Auth AuthUser authUser,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostResponse updatedPost = postService.updatePost(authUser, postId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(updatedPost);
    }

    // 포스트 삭제 (작성자 본인만 가능)
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<MessageResponse> deletePost(
            @Auth AuthUser authUser,
            @PathVariable Long postId
    ) {
        postService.deletePost(postId, authUser);
        return ResponseEntity.ok(MessageResponse.of("포스트가 삭제되었습니다!"));
    }
}
