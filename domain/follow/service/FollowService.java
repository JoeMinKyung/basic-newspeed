package com.example.springbasicnewspeed.domain.follow.service;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.dto.response.FollowResponse;
import com.example.springbasicnewspeed.domain.follow.entity.Follow;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponse toggleFollow(AuthUser authUser, Long userId) {

        User follower = User.fromAuthUser(authUser);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 이미 팔로우 관계가 있는지 확인
        Follow existingFollow = followRepository.findByFollowerAndFollowed(follower, user);

        boolean followed;
        LocalDateTime followAt = null;

        if (existingFollow != null) {
            // 팔로우가 존재하면 언팔로우
            followRepository.delete(existingFollow);
            followed = false;
        } else {
            // 팔로우가 없으면 팔로우 생성
            Follow newFollow = new Follow(follower, user);
            Follow savedFollow = followRepository.save(newFollow);
            followAt = savedFollow.getFollowAt();
            followed = true;
        }

        return new FollowResponse(follower.getId(), follower.getUserName(), followed, followAt);
    }
}
