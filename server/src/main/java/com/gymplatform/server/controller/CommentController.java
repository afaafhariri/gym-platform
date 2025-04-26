package com.gymplatform.server.controller;

import com.gymplatform.server.model.Comment;
import com.gymplatform.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/create")
	public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
		return ResponseEntity.ok(commentService.createComment(comment));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Comment> updateComment(
			@PathVariable String id,
			@RequestBody Comment comment) {
		return ResponseEntity.ok(commentService.updateComment(id, comment.getCommentText()));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable String id) {
		return ResponseEntity.ok(commentService.deleteComment(id));
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
		return ResponseEntity.ok(commentService.getCommentById(id));
	}

	@GetMapping("/get-all")
	public ResponseEntity<List<Comment>> getAllComments() {
		return ResponseEntity.ok(commentService.getAllComments());
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId) {
		return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable String userId) {
		return ResponseEntity.ok(commentService.getCommentsByUserId(userId));
	}
}