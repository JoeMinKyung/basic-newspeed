package com.example.springbasicnewspeed.domain.post.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PostLikeResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.service.PostLikeService;
import com.example.springbasicnewspeed.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

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
    public ResponseEntity<List<PostResponse>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponse> postResponses = postService.getPosts(page, size);
        return ResponseEntity.ok(postResponses);
    }

    // 포스트 수정 (작성자 본인만 가능)

    // 포스트 삭제 (작성자 본인만 가능)

    // 좋아요 토글 (좋아요 추가/제거)
    @PatchMapping("/posts/likes/{postId}")
    public ResponseEntity<PostLikeResponse> likePost(
            @Auth AuthUser authUser,
            @PathVariable Long postId
    ) {
        PostLikeResponse response = postLikeService.toggleLike(authUser, postId);
        return ResponseEntity.ok(response);
    }
}
