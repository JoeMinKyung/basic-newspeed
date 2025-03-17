package com.example.springbasicnewspeed.domain.like.service;

import com.example.springbasicnewspeed.common.exception.CannotLikeOwnPostException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.like.dto.response.LikeResponse;
import com.example.springbasicnewspeed.domain.like.entity.PostLike;
import com.example.springbasicnewspeed.domain.like.repository.PostLikeRepository;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public LikeResponse togglePostLike(AuthUser authUser, Post post) {

        User user = User.fromAuthUser(authUser);

        if (post.isPostOwnerByAuthUser(authUser)) {
            throw new CannotLikeOwnPostException("본인의 게시물에 좋아요를 누를 수 없습니다.");
        }

        boolean exists = postLikeRepository.existsByUserAndPost(user, post);
        boolean liked;
        LocalDateTime likedAt = null;

        if (exists) {
            postLikeRepository.deleteByUserAndPost(user, post);
            liked = false;
        } else {
            PostLike postLike = new PostLike(user, post);
            PostLike savedPostLike = postLikeRepository.save(postLike);
            likedAt = savedPostLike.getLikedAt();
            liked = true;
        }

        long likeCount = postLikeRepository.countByPost(post);
        post.updatePostLikedCount((int) likeCount);

        return LikeResponse.of(post.getId(), user.getUserName(), liked, likeCount, likedAt);
    }
}
