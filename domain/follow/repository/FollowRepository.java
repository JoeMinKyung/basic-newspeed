package com.example.springbasicnewspeed.domain.follow.repository;

import com.example.springbasicnewspeed.domain.follow.entity.Follow;
import com.example.springbasicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFollowerAndFollowed(User follower, User followed);

    @Query("SELECT f.followed.id FROM Follow f WHERE f.follower.id = :followerId")
    List<Long> findFollowedUserIdsByFollowerId(@Param("followerId") Long followerId);

    int countByFollowed(User followed);

    int countByFollower(User follower);
}
