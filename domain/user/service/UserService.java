package com.example.springbasicnewspeed.domain.user.service;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = new ArrayList<>();
        for (User user : users) {
            response.add(new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPassword()));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public void update(AuthUser authUser, UserRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );  // 레파짓토리에서 찾는거 말고 다른방법도??

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일이기 때문에 수정할 수 없습니다.");
        }

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new IllegalStateException("이미 존재하는 닉네임이기 때문에 수정할 수 없습니다.");
        }

        user.update(request.getEmail(), request.getPassword());
    }

    // 다른 유저 프로필 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(AuthUser authUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 존재하지 않습니다.")
        );

        return new UserProfileResponse(user.getEmail(), user.getUserName());
    }
}
