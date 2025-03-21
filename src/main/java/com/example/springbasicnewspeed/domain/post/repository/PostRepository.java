package com.example.springbasicnewspeed.domain.post.repository;

import com.example.springbasicnewspeed.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 기본 정렬 (생성일 기준 최신순)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 특정 유저들의 게시물 조회 (생성일 기준 최신순)
    Page<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds, Pageable pageable);

    // 수정일 기준 최신순 정렬
    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    // 좋아요 많은 순 정렬
    Page<Post> findAllByOrderByPostLikedCountDesc(Pageable pageable);

    // 특정 기간 내 게시물 조회 (createdAt 기준) - LocalDateTime 사용
    Page<Post> findAllByCreatedAtBetweenOrderByCreatedAtDesc(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
