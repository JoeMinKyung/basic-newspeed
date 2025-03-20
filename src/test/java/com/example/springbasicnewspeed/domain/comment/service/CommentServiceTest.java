package com.example.springbasicnewspeed.domain.comment.service;

import com.example.springbasicnewspeed.common.exception.UnauthorizedPostException;
import com.example.springbasicnewspeed.config.PasswordEncoder;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentSaveRequest;
import com.example.springbasicnewspeed.domain.comment.dto.request.CommentUpdateRequest;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentResponse;
import com.example.springbasicnewspeed.domain.comment.dto.response.CommentSaveResponse;
import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.comment.repository.CommentRepository;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import com.example.springbasicnewspeed.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    private AuthUser authUser;
    private Post post;
    private User user;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@gmail.com", "테스트");
        authUser = new AuthUser(1L, "test@gmail.com", "테스트");

        // Comment 객체 생성 시 user 설정
        post = new Post("테스트 제목", "테스트 댓글", user);
        comment = new Comment("테스트 댓글", user, post);

        lenient().when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        lenient().when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
    }

    @Test
    void 댓글_생성_성공() {
        // given
        CommentSaveRequest request = new CommentSaveRequest("테스트 댓글");
        CommentSaveResponse expectedResponse = new CommentSaveResponse(1L, post.getId(), user.getUserName(), "테스트 댓글", 0, null, null);

        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment("테스트 댓글", user, post));

        // when
        CommentSaveResponse response = commentService.saveComment(authUser, post.getId(), request);

        // then
        assertEquals(expectedResponse.getContent(), response.getContent());
        assertEquals(expectedResponse.getPostId(), response.getPostId());
        assertEquals(expectedResponse.getUserName(), response.getUserName());
    }

    @Test
    void 댓글_조회_성공() {
        // given
        Comment comment = new Comment("테스트 댓글", user, post);
        when(commentRepository.findByPostIdWithUser(post.getId())).thenReturn(List.of(comment));

        // when
        List<CommentResponse> comments = commentService.getComments(post.getId());

        // then
        assertFalse(comments.isEmpty());
        assertEquals("테스트 댓글", comments.get(0).getContent());
    }

    @Test
    void 댓글_수정_성공() {
        // given
        String content = "테스트 댓글";
        String newContent = "업데이트 댓글";
        Comment comment = new Comment(content, user, post);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentUpdateRequest updateRequest = new CommentUpdateRequest(newContent);

        // when
        CommentResponse updatedComment = commentService.updateComment(authUser, post.getId(), 1L, updateRequest);

        // then
        assertEquals(newContent, updatedComment.getContent());
    }

    @Test
    void 댓글_수정_실패_댓글_작성자가_아님() {
        // given
        String content = "테스트 댓글";
        String newContent = "업데이트 댓글";
        User otherUser = new User(2L, "other@gmail.com", "테스트2");
        Comment comment = new Comment(content, otherUser, post);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentUpdateRequest updateRequest = new CommentUpdateRequest(newContent);

        // when & then
        assertThrows(UnauthorizedPostException.class, () -> {
            commentService.updateComment(authUser, post.getId(), 1L, updateRequest);
        });
    }

    @Test
    void 댓글_삭제_성공() {
        // given
        Long commentId = 1L;
        Comment comment = new Comment("Test comment", user, post);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        commentService.deleteComment(authUser, commentId);

        // then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void 댓글_삭제_실패_댓글_작성자가_아님() {
        // given
        User otherUser = new User(2L, "other@gmail.com", "테스트2");
        Comment comment = new Comment("Test comment", otherUser, post);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // when & then
        assertThrows(UnauthorizedPostException.class, () -> {
            commentService.deleteComment(authUser, 1L);
        });
    }
}
