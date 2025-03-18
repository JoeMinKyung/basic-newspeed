package com.example.springbasicnewspeed.domain.user.service;

import com.example.springbasicnewspeed.common.exception.*;
import com.example.springbasicnewspeed.config.PasswordEncoder;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.user.dto.request.PasswordUpdateRequest;
import com.example.springbasicnewspeed.domain.user.dto.request.UserRequest;
import com.example.springbasicnewspeed.domain.user.dto.response.UserProfileResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;

    // 프로필 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = new ArrayList<>();
        for (User user : users) {
            int followerCount = followRepository.countByFollowed(user);
            int followingCount = followRepository.countByFollower(user);

            response.add(new UserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getUserName(),
                    user.getPassword(),
                    followerCount,
                    followingCount,
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            ));
        }
        return response;
    }

    // 유저 프로필 수정
    @Transactional
    public UserProfileResponse update(AuthUser authUser, UserRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );  // 레파짓토리에서 찾는거 말고 다른방법도??

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("이미 존재하는 이메일이기 때문에 수정할 수 없습니다.");
            }
            user.updateEmail(request.getEmail());
        }

        if (request.getUserName() != null && !request.getUserName().isEmpty()) {
            if (userRepository.existsByUserName(request.getUserName())) {
                throw new UserNameAlreadyExistsException("이미 존재하는 닉네임이기 때문에 수정할 수 없습니다.");
            }
            user.updateUserName(request.getUserName());
        }

        int followerCount = followRepository.countByFollowed(user);
        int followingCount = followRepository.countByFollower(user);

        return new UserProfileResponse(
                user.getEmail(),
                user.getUserName(),
                followerCount,
                followingCount,
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    // 유저 비밀번호 수정
    @Transactional
    public void updatePassword(AuthUser authUser, PasswordUpdateRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 존재하지 않습니다.")
        );

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!Objects.equals(request.getNewPassword(), request.getNewPasswordCheck())) {
            throw new PasswordMismatchException("새 비밀번호와 비밀번호 확인이 같지 않습니다.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new SamePasswordException("현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // 다른 유저 프로필 단건 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(AuthUser authUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 존재하지 않습니다.")
        );

        int followerCount = followRepository.countByFollowed(user);
        int followingCount = followRepository.countByFollower(user);

        return new UserProfileResponse(
                user.getEmail(),
                user.getUserName(),
                followerCount,
                followingCount,
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
