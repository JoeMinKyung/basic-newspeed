package com.example.springbasicnewspeed.domain.like.repository;

import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.like.entity.CommentLike;
import com.example.springbasicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
    void deleteByUserAndComment(User user, Comment comment);
    long countByComment(Comment comment);
}
