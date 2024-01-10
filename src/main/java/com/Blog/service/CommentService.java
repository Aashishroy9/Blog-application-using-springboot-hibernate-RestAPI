package com.Blog.service;

import com.Blog.payload.CommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId,CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsForPost(long postId);

    CommentDto updateComment(long commentId, CommentDto updatedCommentDto);
}
