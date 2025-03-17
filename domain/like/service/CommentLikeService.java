package com.example.springbasicnewspeed.domain.like.service;

import com.example.springbasicnewspeed.common.exception.CannotLikeOwnPostException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.like.dto.response.LikeResponse;
import com.example.springbasicnewspeed.domain.like.entity.CommentLike;
import com.example.springbasicnewspeed.domain.like.repository.CommentLikeRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public LikeResponse toggleCommentLike(AuthUser authUser, Comment comment) {

        User user = User.fromAuthUser(authUser);

        if (comment.isCommentOwnerByAuthUser(authUser)) {
            throw new CannotLikeOwnPostException("본인의 댓글에 좋아요를 누를 수 없습니다.");
        }

        boolean exists = commentLikeRepository.existsByUserAndComment(user, comment);
        boolean liked;
        LocalDateTime likedAt = null;

        if (exists) {
            commentLikeRepository.deleteByUserAndComment(user, comment);
            liked = false;
        } else {
            CommentLike commentLike = new CommentLike(user, comment);
            CommentLike savedCommentLike = commentLikeRepository.save(commentLike);
            likedAt = savedCommentLike.getLikedAt();
            liked = true;
        }

        long likeCount = commentLikeRepository.countByComment(comment);
        comment.updateCommentLikedCount((int) likeCount);

        return LikeResponse.of(comment.getId(), user.getUserName(), liked, likeCount, likedAt);
    }
}

