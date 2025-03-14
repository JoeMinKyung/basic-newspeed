package com.example.springbasicnewspeed.domain.post.service;

import com.example.springbasicnewspeed.common.exception.CannotLikeOwnPostException;
import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.post.dto.response.PostLikeResponse;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponse toggleLike(AuthUser authUser, Long postId) {

        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다."));

        if (post.isPostOwner(user)) {
            throw new CannotLikeOwnPostException("본인의 게시물에 좋아요를 누를 수 없습니다.");
        }

        boolean currentlyPostLiked = post.getLikedUsers().contains(user);

        if (currentlyPostLiked) {
            // 좋아요 취소
            post.removeLike(user);
        } else {
            // 좋아요 추가
            post.addLike(user);
        }

        // 좋아요 수 업데이트
        post.updatePostLikedCount(post.getLikedUsers().size());

        // 변경사항 저장
        postRepository.save(post);

        return new PostLikeResponse(
                post.getId(),
                user.getUserName(),
                !currentlyPostLiked,  // 토글 후 값 반환
                post.getPostLikedCount()
        );
    }
}
