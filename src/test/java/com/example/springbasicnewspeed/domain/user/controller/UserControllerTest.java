package com.example.springbasicnewspeed.domain.user.controller;

import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

//    @Test
//    void User_단건_조회() throws Exception {
//        // given
//        long userId = 1L;
//        String email = "a@a.com";
//
//        given(userService.getUser(userId)).willReturn(new UserResponse(userId, email));
//
//        // when & then
//        mockMvc.perform(get("/users/{userId}", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(userId))
//                .andExpect(jsonPath("$.email").value(email));
//    }
}
