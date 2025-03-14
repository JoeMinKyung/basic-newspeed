package com.example.springbasicnewspeed.domain.post.repository;

import com.example.springbasicnewspeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
