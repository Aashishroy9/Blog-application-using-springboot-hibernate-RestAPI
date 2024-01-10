package com.Blog.service;

import com.Blog.payload.PostDto;

import java.util.List;


public interface PostService {

    public PostDto createPost(PostDto postDto);


    void deletepost(long id);



    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


    PostDto updatePost(long id, PostDto updatedPostDto);


}
