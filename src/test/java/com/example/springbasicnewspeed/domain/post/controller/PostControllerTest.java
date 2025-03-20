package com.example.springbasicnewspeed.domain.post.controller;

import com.example.springbasicnewspeed.config.AuthUserArgumentResolver;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.entity.Follow;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.request.PostUpdateRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PageResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.service.PostService;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private FollowRepository followRepository;

    @MockitoBean
    private AuthUserArgumentResolver authUserArgumentResolver;

    @BeforeEach
    void setUp() {
        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new AuthUser(1L, "user@example.com", "userName"));
    }

    @Test
    void 포스트_생성() throws Exception {
        // given
        Long postId = 25L;
        Long userId = 11L;
        String userName = "test1dfsd22f";
        String title = "테스트";
        String content = "좋아요테스트2";
        int postLikedCount = 0;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        PostSaveRequest postSaveRequest = new PostSaveRequest(title, content);
        PostSaveResponse postSaveResponse = new PostSaveResponse(postId, userName, title, content, postLikedCount, createdAt, updatedAt);

        given(postService.savePost(any(AuthUser.class), any(PostSaveRequest.class)))
                .willReturn(postSaveResponse);

        // when & then
        mockMvc.perform(post("/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postSaveRequest))
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.postLikedCount").value(postLikedCount))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void 포스트_목록_조회() throws Exception {
        // given
        String userName1 = "테스트";
        String title1 = "새로운 포스트 제목";
        String content1 = "테스트 내용입니다.";
        int postLikedCount1 = 7;
        LocalDateTime createdAt1 = LocalDateTime.now();
        LocalDateTime updatedAt1 = LocalDateTime.now();

        String userName2 = "테스트2";
        String title2 = "새로운 포스트 제목2";
        String content2 = "테스트 내용입니다.2";
        int postLikedCount2 = 23;
        LocalDateTime createdAt2 = LocalDateTime.now();
        LocalDateTime updatedAt2 = LocalDateTime.now();

        PostResponse post1 = new PostResponse(1L, userName1, title1, content1, postLikedCount1, createdAt1, updatedAt1);
        PostResponse post2 = new PostResponse(2L, userName2, title2, content2, postLikedCount2, createdAt2, updatedAt2);

        PageResponse<PostResponse> pageResponse = new PageResponse<>(List.of(post1, post2), 1, 10, 1, 2);

        given(postService.getPosts(1, 10)).willReturn(pageResponse);

        // when & then
        mockMvc.perform(get("/posts")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].title").value(title1))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].title").value(title2));
    }

    @Test
    void 팔로우한_사람들의_포스트_조회() throws Exception {
        // given
        Long user1Id = 1L;  // 로그인한 사용자 ID (AuthUser)
        Long user2Id = 2L;  // 팔로우한 유저 ID (user1이 팔로우한 대상)

        // user1이 user2를 팔로우하고 있다고 가정
        given(followRepository.findFollowedUserIdsByFollowerId(user1Id))
                .willReturn(List.of(user2Id));

        String userName2 = "테스트2";
        String title2 = "새로운 포스트 제목2";
        String content2 = "테스트 내용입니다.2";
        int postLikedCount2 = 23;
        LocalDateTime createdAt2 = LocalDateTime.now();
        LocalDateTime updatedAt2 = LocalDateTime.now();

        PostResponse post2 = new PostResponse(2L, userName2, title2, content2, postLikedCount2, createdAt2, updatedAt2);

        PageResponse<PostResponse> pageResponse = new PageResponse<>(List.of(post2), 1, 10, 1, 1);

        given(postService.getPostsByFollowedUsers(any(AuthUser.class), anyInt(), anyInt()))
                .willReturn(pageResponse);

        // when & then
        mockMvc.perform(get("/followed")
                        .param("page", "1")
                        .param("size", "10")
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(2L))
                .andExpect(jsonPath("$.data[0].title").value(title2));
    }

    @Test
    void 포스트_수정() throws Exception {
        // given
        String userName = "테스트";
        int postLikedCount = 7;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        String newTitle = "업데이트 포스트 제목";
        String newContent = "업데이트 된 내용입니다. ";
        Long postId = 1L;

        PostUpdateRequest updateRequest = new PostUpdateRequest(newTitle, newContent);
        PostResponse postResponse = new PostResponse(postId, userName, newTitle, newContent, postLikedCount, createdAt, updatedAt);

        given(postService.updatePost(any(AuthUser.class), eq(postId), eq(newTitle), eq(newContent)))
                .willReturn(postResponse);

        // when & then
        mockMvc.perform(patch("/posts/{postId}", postId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(newTitle))
                .andExpect(jsonPath("$.content").value(newContent));
    }

    @Test
    void 포스트_삭제() throws Exception {
        // given
        Long postId = 1L;
        AuthUser authUser = new AuthUser(1L, "user@example.com", "password");

        // when & then
        mockMvc.perform(delete("/post/{postId}", postId)
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("포스트가 삭제되었습니다!"));

        verify(postService).deletePost(eq(postId), any(AuthUser.class));
    }
}
