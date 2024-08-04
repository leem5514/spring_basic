package com.beyond.basic.controller;

import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import com.beyond.basic.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Service
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping("/post/list")
    public List<PostResDto> postList() {
        List<PostResDto> postList = postService.postList();
        return postList;
    }

    // LAZY(지연로딩), EAGER(즉시로딩) 테스트
    @GetMapping("post/member/all")
    @ResponseBody
    public void memberPostAll() {
        System.out.println("postRepository: " + postRepository.findAll());
    }
}
