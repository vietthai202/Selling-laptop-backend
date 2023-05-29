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
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<BlogDto> getBlogBySlug(@PathVariable String slug) {
        BlogDto blog = blogService.getBlogBySlug(slug);
        if (blog != null) {
            return ResponseEntity.status(HttpStatus.OK).body(blog);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<BlogDto>> getAllBlog() {
        List<BlogDto> blogDtoList = blogService.getAllBlog();
        if (blogDtoList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(blogDtoList);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody BlogDto blog) {
        Blog bl = blogService.createBlog(blog);
        if (bl != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Tạo thành công");
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogDto blog) {
        boolean isUpdate = blogService.updateBlog(id, blog);
        if (isUpdate) {
            ApiSuccessResponse response = new ApiSuccessResponse("Blog update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Blog update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        boolean isDelete = blogService.deleteBlog(id);
        if (isDelete) {
            ApiSuccessResponse response = new ApiSuccessResponse("Blog delete Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Blog delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
