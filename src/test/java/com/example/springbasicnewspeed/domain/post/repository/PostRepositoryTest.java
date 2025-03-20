package com.example.springbasicnewspeed.domain.post.repository;

import com.example.springbasicnewspeed.domain.follow.entity.Follow;
import com.example.springbasicnewspeed.domain.follow.repository.FollowRepository;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Test
    void 게시글을_최신순으로_조회할_수_있다() {
        // given
        User user = new User("test@gmail.com", "password", "test");
        userRepository.save(user);

        Post post1 = new Post("첫 번째 글", "내용1", user);
        Post post2 = new Post("두 번째 글", "내용2", user);

        post1.updateCreatedAt(LocalDateTime.now().minusDays(1));  // 1일 전
        post2.updateCreatedAt(LocalDateTime.now());  // 현재 시간

        postRepository.save(post1);
        postRepository.save(post2);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        // then
        assertEquals(2, posts.getTotalElements());
        assertEquals("두 번째 글", posts.getContent().get(0).getTitle());
        assertEquals("첫 번째 글", posts.getContent().get(1).getTitle());
    }

    @Test
    void 팔로우한_유저들의_게시글만_조회할_수_있다() {
        // given
        User user1 = new User("user1@gmail.com", "password", "user1");
        User user2 = new User("user2@gmail.com", "password", "user2");
        User user3 = new User("user3@gmail.com", "password", "user3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // user1이 user2를 팔로우
        Follow follow = new Follow(user1, user2);
        followRepository.save(follow);

        // user2와 user3이 게시글 작성
        Post post1 = new Post( "user2의 글", "내용1", user2);
        Post post2 = new Post("user3의 글", "내용2", user3);
        postRepository.saveAll(List.of(post1, post2));

        Pageable pageable = PageRequest.of(0, 10);
        List<Long> followedUserIds = List.of(user2.getId()); // user1이 팔로우한 user2만 조회해야 함

        // when
        Page<Post> posts = postRepository.findByUserIdInOrderByCreatedAtDesc(followedUserIds, pageable);

        // then
        assertEquals(1, posts.getTotalElements()); // user2의 글만 나와야 함
        assertEquals("user2의 글", posts.getContent().get(0).getTitle());
    }
}

