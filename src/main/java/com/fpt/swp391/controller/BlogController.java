package com.fpt.swp391.controller;

import com.fpt.swp391.dto.BlogDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.service.BlogService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;

    }
    @PostMapping( "/createBlog")
    public ResponseEntity<Blog> createBlogCategory(@RequestBody BlogDto blog) {
        blogService.createBlog(blog);
        return null;
    }
    @PostMapping("/updateBl/{id}")
    public ResponseEntity<Blog> createBlogCategory(@PathVariable Long id, @RequestBody BlogDto blog) {
        blogService.updateBlog(id,blog);
        return null;
    }
    @DeleteMapping("/deleteBl/{id}")
    public ResponseEntity<?> deleteBl(@PathVariable Long id) {
        boolean isDelete =  blogService.deleteBlog(id);
        if (isDelete){
            ApiSuccessResponse response = new ApiSuccessResponse("Delete Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
