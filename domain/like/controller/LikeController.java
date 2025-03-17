package com.example.springbasicnewspeed.domain.like.controller;

import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.comment.repository.CommentRepository;
import com.example.springbasicnewspeed.domain.common.annotation.Auth;
import com.example.springbasicnewspeed.domain.like.dto.response.LikeResponse;
import com.example.springbasicnewspeed.domain.like.service.CommentLikeService;
import com.example.springbasicnewspeed.domain.like.service.PostLikeService;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/likes/posts/{postId}")
    public LikeResponse togglePostLike(@Auth AuthUser authUser, @PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
        return postLikeService.togglePostLike(authUser, post);
    }

    @PostMapping("/likes/comments/{commentId}")
    public LikeResponse toggleCommentLike(@Auth AuthUser authUser, @PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new PostNotFoundException("댓글을 찾을 수 없습니다."));
        return commentLikeService.toggleCommentLike(authUser, comment);
    }
}
