package com.example.springbasicnewspeed.domain.user.service;

import com.example.springbasicnewspeed.common.exception.EmailAlreadyExistsException;
import com.example.springbasicnewspeed.common.exception.PasswordMismatchException;
import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.config.PasswordEncoder;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.user.dto.request.PasswordUpdateRequest;
import com.example.springbasicnewspeed.domain.user.dto.request.UserRequest;
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

import java.util.ArrayList;
import java.util.List;
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

    @Mock
    private PasswordEncoder passwordEncoder;

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

    @Test
    void 유저_비밀번호를_수정할_수_있다() {
        // given
        long userId = 1L;
        String email = "test@gmail.com";
        String password = "Password111111!!";
        String newPassword = "newPassword1!!";
        AuthUser authUser = new AuthUser(userId, email, password);
        User user = new User(email, password, "test");
        ReflectionTestUtils.setField(user, "id", userId);

        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(password, newPassword, newPassword);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);
        given(passwordEncoder.encode(newPassword)).willReturn("encodedNewPassword");

        // when
        userService.updatePassword(authUser, passwordUpdateRequest);

        // then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void 비밀번호_수정_시_현재_비밀번호가_일치하지_않으면_예외를_던진다() {
        // given
        long userId = 1L;
        String email = "test@gmail.com";
        String password = "currentPassword1!!";
        String newPassword = "newPassword1!!";
        AuthUser authUser = new AuthUser(userId, email, password);
        User user = new User(email, password, "test");
        ReflectionTestUtils.setField(user, "id", userId);

        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest("wrongPassword", newPassword, newPassword);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when & then
        assertThrows(PasswordMismatchException.class,
                () -> userService.updatePassword(authUser, passwordUpdateRequest),
                "현재 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 유저_프로필을_수정할_수_있다() {
        // given
        long userId = 1L;
        String email = "test@gmail.com";
        String userName = "test";
        String newUserName = "updatedUserName";
        String password = "newPassword1!!";
        AuthUser authUser = new AuthUser(userId, email, userName);
        UserRequest userRequest = new UserRequest("newEmail@gmail.com", password, newUserName);
        User user = new User(email, password, userName);
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByUserName(anyString())).willReturn(false);

        // when
        UserProfileResponse userProfileResponse = userService.update(authUser, userRequest);

        // then
        assertThat(userProfileResponse.getUserName()).isEqualTo(newUserName);
        assertThat(userProfileResponse.getEmail()).isEqualTo("newEmail@gmail.com");
    }

    @Test
    void 유저_이메일_중복시_수정할_수_없다() {
        // given
        long userId = 1L;
        String email = "test@gmail.com";
        String userName = "test";
        String newUserName = "updatedUserName";
        String password = "newPassword1!!";
        AuthUser authUser = new AuthUser(userId, email, userName);
        UserRequest userRequest = new UserRequest("test@gmail.com", password, newUserName); // 중복된 이메일
        User user = new User(email, password, userName);
        ReflectionTestUtils.setField(user, "id", userId);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when & then
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.update(authUser, userRequest),
                "이미 존재하는 이메일이기 때문에 수정할 수 없습니다.");
    }

    @Test
    void 유저_전체_조회_기능_테스트() {
        // given
        long userId1 = 1L;
        long userId2 = 2L;
        String email1 = "test1@gmail.com";
        String email2 = "test2@gmail.com";
        String userName1 = "testUser1";
        String userName2 = "testUser2";

        User user1 = new User(email1, "password1", userName1);
        User user2 = new User(email2, "password2", userName2);

        ReflectionTestUtils.setField(user1, "id", userId1);
        ReflectionTestUtils.setField(user2, "id", userId2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        given(userRepository.findAll()).willReturn(userList);
        given(followRepository.countByFollowed(user1)).willReturn(5);
        given(followRepository.countByFollower(user1)).willReturn(3);
        given(followRepository.countByFollowed(user2)).willReturn(8);
        given(followRepository.countByFollower(user2)).willReturn(4);

        // when
        List<UserResponse> userResponses = userService.findAll();

        // then
        assertThat(userResponses.size()).isEqualTo(2);
        UserResponse userResponse1 = userResponses.get(0);
        assertThat(userResponse1.getUserName()).isEqualTo(userName1);
        assertThat(userResponse1.getEmail()).isEqualTo(email1);
        assertThat(userResponse1.getFollowerCount()).isEqualTo(5);
        assertThat(userResponse1.getFollowingCount()).isEqualTo(3);

        UserResponse userResponse2 = userResponses.get(1);
        assertThat(userResponse2.getUserName()).isEqualTo(userName2);
        assertThat(userResponse2.getEmail()).isEqualTo(email2);
        assertThat(userResponse2.getFollowerCount()).isEqualTo(8);
        assertThat(userResponse2.getFollowingCount()).isEqualTo(4);

        verify(followRepository, times(2)).countByFollowed(any(User.class));
        verify(followRepository, times(2)).countByFollower(any(User.class));
    }
}