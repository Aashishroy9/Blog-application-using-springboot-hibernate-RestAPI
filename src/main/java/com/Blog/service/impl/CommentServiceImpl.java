package com.Blog.service.impl;

import com.Blog.entity.Comment;
import com.Blog.entity.Post;
import com.Blog.exception.ResourceNotFoundException;
import com.Blog.payload.CommentDto;
import com.Blog.repository.CommentRepository;
import com.Blog.repository.PostRepository;
import com.Blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id" + postId)
        );
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto=new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());

        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            commentRepository.deleteById(commentId);
            System.out.println("Comment with ID " + commentId + " is deleted successfully.");
        } else {
            System.out.println("Comment with ID " + commentId + " not found.");
            throw new ResourceNotFoundException("Comment not found with id " + commentId);
        }
    }
    @Override
    public List<CommentDto> getCommentsForPost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id " + postId)
        );

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getName(),
                        comment.getEmail(),
                        comment.getBody()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto updatedCommentDto) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id " + commentId)
        );

        existingComment.setName(updatedCommentDto.getName());
        existingComment.setEmail(updatedCommentDto.getEmail());
        existingComment.setBody(updatedCommentDto.getBody());

        Comment updatedComment = commentRepository.save(existingComment);

        return new CommentDto(
                updatedComment.getId(),
                updatedComment.getName(),
                updatedComment.getEmail(),
                updatedComment.getBody()
        );
    }

}
