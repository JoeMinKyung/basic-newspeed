package com.example.springbasicnewspeed.domain.like.repository;

import com.example.springbasicnewspeed.domain.like.entity.PostLike;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
    long countByPost(Post post);
}
