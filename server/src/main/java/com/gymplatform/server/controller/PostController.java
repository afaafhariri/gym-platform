package com.gymplatform.server.controller;

import com.gymplatform.server.model.Post;
import com.gymplatform.server.repository.PostRepository;
import com.gymplatform.server.service.GcsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final GcsService gcsService;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam("description") String description,
            @RequestParam("mediaType") String mediaType, // "photo" or "video"
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("userId") String userId
    ) {
        System.out.println("✅ Received Post Request:");
        System.out.println("Description: " + description);
        System.out.println("MediaType: " + mediaType);
        System.out.println("Files count: " + files.size());
        System.out.println("UserId: " + userId);
        try {
            if (mediaType.equals("photo") && files.size() != 3) {
                return ResponseEntity.badRequest().body(null);
            }
            if (mediaType.equals("video") && files.size() != 1) {
                return ResponseEntity.badRequest().body(null);
            }

            List<String> mediaUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
                file.transferTo(tempFile.toFile());

                String url = gcsService.uploadFile(tempFile, "posts");

                mediaUrls.add(url);

                Files.deleteIfExists(tempFile);
            }

            Post post = Post.builder()
                    .userId(userId)
                    .description(description)
                    .mediaType(mediaType)
                    .mediaUrls(mediaUrls)
                    .build();

            postRepository.save(post);
            System.out.println("Post created");
            return ResponseEntity.ok(post);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String postId,
            @RequestParam("description") String description,
            @RequestParam(value = "mediaType", required = false) String mediaType,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            Post post = postRepository.findById(postId).orElse(null);

            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            // Update description
            post.setDescription(description);

            if (files != null && !files.isEmpty()) {
                // Validate new media type
                if (mediaType.equals("photo") && files.size() != 3) {
                    return ResponseEntity.badRequest().body(null);
                }
                if (mediaType.equals("video") && files.size() != 1) {
                    return ResponseEntity.badRequest().body(null);
                }

                List<String> mediaUrls = new ArrayList<>();
                for (MultipartFile file : files) {
                    Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
                    file.transferTo(tempFile.toFile());

                    String url = gcsService.uploadFile(tempFile, "posts");

                    mediaUrls.add(url);

                    Files.deleteIfExists(tempFile);
                }

                post.setMediaType(mediaType);
                post.setMediaUrls(mediaUrls);
            }

            postRepository.save(post);

            return ResponseEntity.ok(post);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        try {
            Post post = postRepository.findById(postId).orElse(null);

            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            // Delete files from GCS
            for (String url : post.getMediaUrls()) {
                gcsService.deleteFile(url);
            }

            // Delete post from MongoDB
            postRepository.deleteById(postId);

            return ResponseEntity.noContent().build(); // 204 No Content

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}