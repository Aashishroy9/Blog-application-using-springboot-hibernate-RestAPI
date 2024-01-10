package com.Blog.controller;

import com.Blog.exception.ResourceNotFoundException;
import com.Blog.payload.PostDto;
import com.Blog.service.PostService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }else {

        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletepost(@PathVariable long id){
        postService.deletepost(id);
        return new ResponseEntity<>("post is deleted!!",HttpStatus.OK);

    }
      //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortBy=desc
    @GetMapping
    public List<PostDto> getAllPosts(


            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = "3",required = false)int pageSize,
             @RequestParam(name="sortBy",defaultValue="id",required=false)String sortBy,
            @RequestParam(name="sortDir",defaultValue="asc",required=false)String sortDir
    ){
        List<PostDto> postDtos= postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable long id,
            @Valid @RequestBody PostDto updatedPostDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            PostDto updatedDto = postService.updatePost(id, updatedPostDto);
            return new ResponseEntity<>(updatedDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}

