package com.example.springbasicnewspeed.domain.user.service;

import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.config.PasswordEncoder;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.user.dto.response.UserProfileResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void User를_ID로_조회할_수_있다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        String userName = "test";
        User user = new User(email, "password", userName);
        ReflectionTestUtils.setField(user, "id", userId);

        AuthUser authUser = new AuthUser(userId, email, userName);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when
        UserProfileResponse userProfileResponse = userService.getUserProfile(authUser, userId);

        // then
        assertThat(userProfileResponse).isNotNull();
        assertThat(userProfileResponse.getUserName()).isEqualTo(userName);
        assertThat(userProfileResponse.getEmail()).isEqualTo(email);
    }

    @Test
    void 존재하지_않는_User를_조회_시_UserNotFoundException을_던진다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        String userName = "test";
        AuthUser authUser = new AuthUser(userId, email, userName);
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserProfile(authUser, userId),
                "존재하지 않는 유저입니다."
        );
    }

    @Test
    void User를_삭제할_수_있다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        String userName = "test";
        AuthUser authUser = new AuthUser(userId, email, userName);
        given(userRepository.existsById(anyLong())).willReturn(true);
        doNothing().when(userRepository).deleteById(anyLong());

        // when
        userService.deleteUser(authUser);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void 존재하지_않는_User를_삭제_시_UserNotFoundException를_던진다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        String userName = "test";
        AuthUser authUser = new AuthUser(userId, email, userName);
        given(userRepository.existsById(userId)).willReturn(false);

        // when & then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(authUser));
        verify(userRepository, never()).deleteById(userId);
    }
}
