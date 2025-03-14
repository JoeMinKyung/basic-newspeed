package com.example.springbasicnewspeed.domain.post.service;

import com.example.springbasicnewspeed.common.exception.PostNotFoundException;
import com.example.springbasicnewspeed.common.exception.UnauthorizedPostException;
import com.example.springbasicnewspeed.common.exception.UserNotFoundException;
import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import com.example.springbasicnewspeed.domain.post.dto.request.PostSaveRequest;
import com.example.springbasicnewspeed.domain.post.dto.response.PostResponse;
import com.example.springbasicnewspeed.domain.post.dto.response.PostSaveResponse;
import com.example.springbasicnewspeed.domain.post.entity.Post;
import com.example.springbasicnewspeed.domain.post.repository.PostRepository;
import com.example.springbasicnewspeed.domain.user.dto.response.UserNameResponse;
import com.example.springbasicnewspeed.domain.user.dto.response.UserResponse;
import com.example.springbasicnewspeed.domain.user.entity.User;
import com.example.springbasicnewspeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return posts.stream().map(post -> new PostResponse(
                post.getId(),
                new UserNameResponse(post.getUser().getUserName()),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    public PostResponse updatePost(AuthUser authUser, Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다.")
                );

        if (!post.isPostOwnerByAuthUser(authUser)) {
            throw new UnauthorizedPostException("작성자만 수정할 수 있습니다.");
        }

        if (title == null && content == null) {
            throw new IllegalArgumentException("타이틀 또는 콘텐츠를 입력해야 합니다.");
        }

        if (title != null && !title.isEmpty()) {
            post.updateTitle(title);
        }

        if (content != null && !content.isEmpty()) {
            post.updateContent(content);
        }

        postRepository.save(post);

        return new PostResponse(
                post.getId(),
                new UserNameResponse(post.getUser().getUserName()),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
