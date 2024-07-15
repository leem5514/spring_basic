package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResDto> postList() {
        List<PostResDto> PostResDtos = new ArrayList<>();
        List<Post> postList = postRepository.findAll();
        for(Post post : postList) {
            PostResDtos.add(post.listFromEntity());
            System.out.println("저자의 이름은 " + post.getMember().getEmail());
        }
        return PostResDtos;
    }
}
