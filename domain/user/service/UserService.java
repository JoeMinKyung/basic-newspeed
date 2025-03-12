package com.example.springbasicnewspeed.domain.user.service;

import com.example.springbasicnewspeed.domain.user.dto.UserResponse;
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

    @Transactional
    public UserResponse save(String email) {

        if (userRepository.existsByEamil(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        User user = new User(email);
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유정비니다.")
        );
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = new ArrayList<>();
        for (User user : users) {
            response.add(new UserResponse(user.getId(), user.getEmail()));
        }
        return response;
    }
}
