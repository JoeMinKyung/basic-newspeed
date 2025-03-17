package com.example.springbasicnewspeed.domain.comment.service;

import com.example.springbasicnewspeed.common.exception.CannotLikeOwnPostException;
import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentLikeResponse;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.comment.repository.CommentRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentLikeResponse toggleCommentLike(AuthUser authUser, Long commentId) {

        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다."));

        if (comment.isCommentOwner(user)) {
            throw new CannotLikeOwnPostException("본인의 게시물에 좋아요를 누를 수 없습니다.");
        }

        boolean currentlyCommentLiked = comment.getCommentLikedUsers().contains(user);

        if (currentlyCommentLiked) {
            // 좋아요 취소
            comment.removeLike(user);
        } else {
            // 좋아요 추가
            comment.addLike(user);
        }

        // 좋아요 수 업데이트
        comment.updateCommentLikedCount(comment.getCommentLikedUsers().size());

        // 변경사항 저장
        commentRepository.save(comment);

        return new CommentLikeResponse(
                comment.getId(),
                user.getUserName(),
                !currentlyCommentLiked,
                comment.getCommentLikedCount()
        );
    }
}
