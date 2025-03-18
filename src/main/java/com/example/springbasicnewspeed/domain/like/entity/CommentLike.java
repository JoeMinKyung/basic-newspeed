package com.example.springbasicnewspeed.domain.like.entity;

import com.example.springbasicnewspeed.domain.comment.entity.Comment;
import com.example.springbasicnewspeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment_likes")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // user_id로 매핑
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")  // comment_id로 매핑
    private Comment comment;

    private LocalDateTime likedAt;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.likedAt = LocalDateTime.now();
    }
}

