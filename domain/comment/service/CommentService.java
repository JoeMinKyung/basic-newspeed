package com.example.springbasicnewspeed.domain.comment.service;

import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.comment.repository.CommentRepository;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, Long postId, CommentSaveRequest request) {
        User user = User.fromAuthUser(authUser);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("포스트를 찾을 수 없습니다.")
        );

        Comment savedComment = new Comment(
                request.getContent(),
                user,
                post
        );

        commentRepository.save(savedComment);

        return new CommentSaveResponse(
                savedComment.getId(),
                post.getId(),
                user.getUserName(),
                savedComment.getContent(),
                savedComment.getCommentLikedCount(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt());
    }
}
