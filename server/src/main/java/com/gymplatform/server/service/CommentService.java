package com.gymplatform.server.service;

import com.gymplatform.server.model.Comment;
import com.gymplatform.server.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment updateComment(String id, String newCommentText) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setCommentText(newCommentText);
                    comment.setUpdatedAt(LocalDateTime.now());
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
    }

    public String deleteComment(String id) {
        commentRepository.findById(id)
                .ifPresent(comment -> {
                    comment.setActive(false);
                    comment.setUpdatedAt(LocalDateTime.now());
                    commentRepository.save(comment);
                });
        return "Comment deleted with id "+id;
    }

    public Comment getCommentById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
    }

    public List<Comment> getAllComments() {
        return commentRepository.findByIsActive(true);
    }

    public List<Comment> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getCommentsByUserId(String userId) {
        return commentRepository.findByUserId(userId);
    }
}