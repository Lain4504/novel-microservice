package com.backend.postservice.controller;

import com.backend.postservice.dto.request.PostRequest;
import com.backend.postservice.dto.response.PageResponse;
import com.backend.postservice.dto.response.PostResponse;
import com.backend.postservice.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/create")
    ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request){
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping("/my-posts")
    ResponseEntity<PageResponse<PostResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(postService.getMyPosts(page, size));
    }
}