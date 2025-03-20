package com.example.springbasicnewspeed.domain.user.controller;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.user.dto.request.PasswordUpdateRequest;
import com.example.springbasicnewspeed.domain.user.dto.request.UserRequest;
import com.example.springbasicnewspeed.domain.user.dto.response.UserProfileResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void User_목록_조회_빈리스트() throws Exception {
        // given
        given(userService.findAll()).willReturn(List.of());

        // when & then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void User_목록_조회() throws Exception {
        // given
        long userId1 = 1L;
        long userId2 = 2L;
        String email1 = "user1@a.com";
        String email2 = "user2@a.com";
        String userName1 = "User1";
        String userName2 = "User2";
        String password1 = "password1";
        String password2 = "password2";
        int followerCount1 = 10;
        int followerCount2 = 20;
        int followingCount1 = 5;
        int followingCount2 = 15;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        List<UserResponse> userList = List.of(
                new UserResponse(userId1, email1, userName1, password1, followerCount1, followingCount1, createdAt, updatedAt),
                new UserResponse(userId2, email2, userName2, password2, followerCount2, followingCount2, createdAt, updatedAt)
        );

        given(userService.findAll()).willReturn(userList);

        // when & then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(userId1))
                .andExpect(jsonPath("$[0].email").value(email1))
                .andExpect(jsonPath("$[1].id").value(userId2))
                .andExpect(jsonPath("$[1].email").value(email2));
    }

    @Test
    void User_정보_수정() throws Exception {
        // given
        long userId = 1L;
        String email = "user@a.com";
        String password = "password";
        String userName = "UpdatedUser";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        int followerCount = 10;
        int followingCount = 5;

        UserRequest request = new UserRequest(email, password, userName);
        UserProfileResponse response = new UserProfileResponse(email, userName, followerCount, followingCount, createdAt, updatedAt);

        given(userService.update(any(AuthUser.class), any(UserRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void User_비밀번호_수정() throws Exception {
        // given
        String currentPassword = "currentPassword1!!";
        String newPassword = "newPassword1!!";

        PasswordUpdateRequest request = new PasswordUpdateRequest(currentPassword, newPassword, newPassword);

        doNothing().when(userService).updatePassword(any(AuthUser.class), any(PasswordUpdateRequest.class));

        // when & then
        mockMvc.perform(patch("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("비밀번호 변경 성공!"));
    }


    @Test
    void User_단건_조회() throws Exception {
        // given
        long authUserId = 1L;
        long userId = 2L;
        String email = "test@mail.com";
        String password = "currentPassword1!!";
        String userName = "UpdatedUser";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        int followerCount = 10;
        int followingCount = 5;
        AuthUser mockAuthUser = new AuthUser(authUserId, email, password);

        given(userService.getUserProfile(any(AuthUser.class), eq(userId)))
                .willReturn(new UserProfileResponse(email, userName, followerCount, followingCount, createdAt, updatedAt));

        // when & then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.followerCount").value(followerCount))
                .andExpect(jsonPath("$.followingCount").value(followingCount));
    }
}
