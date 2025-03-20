package com.example.springbasicnewspeed.domain.post.service;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.follow.entity.Follow;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PageResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@TestPropertySource("classpath:application.properties")
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private PostService postService;

    private AuthUser authUser;
    private Post post;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@gmail.com", "테스트");
        authUser = new AuthUser(1L, "test@gmail.com", "테스트");

        // Post 객체 생성 시 user 설정
        post = new Post("테스트 제목", "테스트 댓글", user);

        // PostRepository에 대한 Mocking 추가
        lenient().when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
    }

    @Test
    void 포스트를_저장할_수_있다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        String userName = "test";
        User user = new User(email, "password", userName);
        userRepository.save(user);

        PostSaveRequest postSaveRequest = new PostSaveRequest("제목", "내용");

        AuthUser authUser = new AuthUser(userId, email, userName);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // Mock PostRepository save() method
        Post savedPost = new Post("제목", "내용", user);
        given(postRepository.save(any(Post.class))).willReturn(savedPost);

        // when
        PostSaveResponse postSaveResponse = postService.savePost(authUser, postSaveRequest);

        // then
        assertNotNull(postSaveResponse);
        assertEquals("제목", postSaveResponse.getTitle());
        assertEquals("내용", postSaveResponse.getContent());
    }

    @Test
    void 포스트를_최신순으로_조회할_수_있다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        User user = new User(email, "password", "test");
        userRepository.save(user);

        Post post1 = new Post("첫 번째 글", "내용1", user);
        Post post2 = new Post("두 번째 글", "내용2", user);

        Page<Post> postPage = new PageImpl<>(List.of(post2, post1));  // 최신순 정렬

        given(postRepository.findAllByOrderByCreatedAtDesc(any(Pageable.class)))
                .willReturn(postPage);

        // when
        PageResponse<PostResponse> postResponses = postService.getPosts(1, 10);

        // then
        assertNotNull(postResponses.getData());
        assertEquals(2, postResponses.getData().size());
        assertEquals("두 번째 글", postResponses.getData().get(0).getTitle());
        assertEquals("첫 번째 글", postResponses.getData().get(1).getTitle());
    }

    @Test
    void 팔로우한_사용자의_포스트를_조회할_수_있다() {
        // given
        String email = "test@gmail.com";
        long userId = 1L;
        User user = new User(email, "password", "test");
        userRepository.save(user);

        long followedUserId = 2L;
        User followedUser = new User("followed@gmail.com", "password", "followed");
        userRepository.save(followedUser);

        Follow follow = new Follow(user, followedUser);
        followRepository.save(follow);

        given(followRepository.findFollowedUserIdsByFollowerId(userId)).willReturn(List.of(followedUserId));

        Post post1 = new Post("팔로우한 사용자의 포스트", "내용", followedUser);
        postRepository.save(post1);

        Page<Post> postPage = new PageImpl<>(List.of(post1));  // 단일 포스트 반환
        given(postRepository.findByUserIdInOrderByCreatedAtDesc(List.of(followedUserId), PageRequest.of(0, 10)))
                .willReturn(postPage);

        // when
        PageResponse<PostResponse> postResponses = postService.getPostsByFollowedUsers(new AuthUser(userId, email, "test"), 1, 10);

        // then
        assertNotNull(postResponses.getData());
        assertEquals(1, postResponses.getData().size());
        assertEquals("팔로우한 사용자의 포스트", postResponses.getData().get(0).getTitle());
    }

    @Test
    void 포스트를_수정할_수_있다() {
        // given
        userRepository.save(user);

        Post post = new Post("제목", "내용", user);

        postRepository.save(post);  // 포스트를 데이터베이스에 저장

        // when
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post)); // Mocking 추가
        PostResponse updatedPostResponse = postService.updatePost(authUser, post.getId(), "수정된 제목", "수정된 내용");
        System.out.println("Post found: " + post.getId());

        // then
        assertEquals("수정된 제목", updatedPostResponse.getTitle());
        assertEquals("수정된 내용", updatedPostResponse.getContent());
    }
}
