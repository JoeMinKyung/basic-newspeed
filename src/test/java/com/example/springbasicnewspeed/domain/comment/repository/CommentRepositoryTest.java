package com.example.springbasicnewspeed.domain.comment.repository;

import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 게시글에_달린_댓글을_조회할_수_있다() {
        // given
        User user = new User("test@gmail.com", "password", "test");
        userRepository.save(user);

        Post post = new Post("게시글 제목", "게시글 내용", user);
        postRepository.save(post);

        Comment comment1 = new Comment("첫 번째 댓글", user, post);
        Comment comment2 = new Comment("두 번째 댓글", user, post);
        commentRepository.saveAll(List.of(comment1, comment2));

        // when
        List<Comment> comments = commentRepository.findByPostIdWithUser(post.getId());

        // then
        assertEquals(2, comments.size());  // 댓글 두 개가 조회되어야 함
        assertEquals("첫 번째 댓글", comments.get(0).getContent());
        assertEquals("두 번째 댓글", comments.get(1).getContent());
    }

    @Test
    void 특정_사용자가_작성한_댓글을_조회할_수_있다() {
        // given
        User user = new User("test@gmail.com", "password", "test");
        userRepository.save(user);

        Post post = new Post("게시글 제목", "게시글 내용", user);
        postRepository.save(post);

        Comment comment1 = new Comment("첫 번째 댓글", user, post);
        Comment comment2 = new Comment("두 번째 댓글", user, post);
        commentRepository.saveAll(List.of(comment1, comment2));

        // when
        List<Comment> comments = commentRepository.findByPostIdWithUser(post.getId());

        // then
        assertEquals(2, comments.size());  // 두 개의 댓글이 존재해야 함
        assertEquals("첫 번째 댓글", comments.get(0).getContent());
        assertEquals("두 번째 댓글", comments.get(1).getContent());
    }
}
