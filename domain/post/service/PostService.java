package com.example.springbasicnewspeed.domain.post.service;

import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostSaveResponse savePost(AuthUser authUser, PostSaveRequest request) {

        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UserNotFoundException("현재 로그인한 유저를 찾을 수 없습니다."));

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                user
        );

        Post savedPost = postRepository.save(post);

        return new PostSaveResponse(
                savedPost.getId(),
                savedPost.getUser().getUserName(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getPostLikedCount(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );
    }
}
