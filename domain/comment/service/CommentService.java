package com.example.springbasicnewspeed.domain.comment.service;

import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.common.exception.UnauthorizedPostException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentUpdateRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentResponse;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.comment.repository.CommentRepository;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
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

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostIdWithUser(postId);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("포스트를 찾을 수 없습니다.")
        );

        List<CommentResponse> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    post.getId(),
                    user.getUserName(),
                    comment.getContent(),
                    comment.getCommentLikedCount(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // 댓글 수정
    @Transactional
    public CommentResponse updateComment(AuthUser authUser, Long postId, Long commentId, CommentUpdateRequest request) {
        User user = User.fromAuthUser(authUser);
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다.")
                );

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다.")
                );

        if (!comment.isCommentOwnerByAuthUser(authUser)) {
            throw new UnauthorizedPostException("작성자만 수정할 수 있습니다.");
        }

        if (request.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해야 합니다.");
        }

        comment.update(request.getContent());

        commentRepository.save(comment);

        return new CommentResponse(
                comment.getId(),
                post.getId(),
                user.getUserName(),
                comment.getContent(),
                comment.getCommentLikedCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
