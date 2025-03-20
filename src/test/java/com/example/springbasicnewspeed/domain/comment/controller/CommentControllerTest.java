package com.example.springbasicnewspeed.domain.comment.controller;

import com.example.springbasicnewspeed.config.AuthUserArgumentResolver;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentUpdateRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentResponse;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private AuthUserArgumentResolver authUserArgumentResolver;

    @BeforeEach
    void setUp() {
        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new AuthUser(1L, "user@example.com", "userName"));
    }

    @Test
    void 댓글_생성() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;
        String content = "댓글 내용";
        String userName = "testUser";
        int commentLikedCount = 0;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        CommentSaveRequest commentSaveRequest = new CommentSaveRequest(content);
        CommentSaveResponse commentSaveResponse = new CommentSaveResponse(commentId, postId, userName, content, commentLikedCount, createdAt, updatedAt);

        given(commentService.saveComment(any(AuthUser.class), eq(postId), any(CommentSaveRequest.class)))
                .willReturn(commentSaveResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentSaveRequest))
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void 댓글_조회() throws Exception {
        // given
        Long postId = 1L;
        String content = "댓글 내용";
        String userName = "testUser";
        int commentLikedCount = 7;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        CommentResponse commentResponse = new CommentResponse(1L, postId, userName, content, commentLikedCount, createdAt, updatedAt);

        given(commentService.getComments(eq(postId)))
                .willReturn(List.of(commentResponse));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userName").value(userName))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @Test
    void 댓글_수정() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;
        String newContent = "수정된 댓글 내용";
        String userName = "testUser";
        int commentLikedCount = 11;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        CommentUpdateRequest updateRequest = new CommentUpdateRequest(newContent);
        CommentResponse updatedCommentResponse = new CommentResponse(1L, postId, userName, newContent, commentLikedCount, createdAt, updatedAt);

        given(commentService.updateComment(any(AuthUser.class), eq(postId), eq(commentId), any(CommentUpdateRequest.class)))
                .willReturn(updatedCommentResponse);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}/comments/{commentId}", postId, commentId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.content").value(newContent));
    }

    @Test
    void 댓글_삭제() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 1L;

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}/comments/{commentId}", postId, commentId)
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("댓글이 삭제되었습니다!"));

        verify(commentService).deleteComment(any(AuthUser.class), eq(commentId));
    }
}
