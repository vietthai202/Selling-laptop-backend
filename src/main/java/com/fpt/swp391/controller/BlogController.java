package com.fpt.swp391.controller;

import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.service.BlogService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog/{slug}")
    public ResponseEntity<BlogDto> getBlogBySlug(@PathVariable String slug) {
        BlogDto blog = blogService.getBlogBySlug(slug);
        if (blog != null) {
            return ResponseEntity.status(HttpStatus.OK).body(blog);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDto>> getAllBlog() {
        List<BlogDto> blogDtoList = blogService.getAllBlog();
        if (blogDtoList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(blogDtoList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createBlog")
    public ResponseEntity<?> createBlogCategory(@RequestBody BlogDto blog) {
        Blog bl = blogService.createBlog(blog);
        if (bl != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Create Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PostMapping("/updateBl/{id}")
    public ResponseEntity<?> createBlogCategory(@PathVariable Long id, @RequestBody BlogDto blog) {
        Blog bl = blogService.updateBlog(id, blog);
        if (bl != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/deleteBl/{id}")
    public ResponseEntity<?> deleteBl(@PathVariable Long id) {
        boolean isDelete = blogService.deleteBlog(id);
        if (isDelete) {
            ApiSuccessResponse response = new ApiSuccessResponse("Delete Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
