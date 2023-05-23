package com.fpt.swp391.controller;

import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.service.BlogCategoryService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class BlogCategoryController {
    // them sưa xóa
    private final BlogCategoryService blogCategoryService;

    public BlogCategoryController(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @PostMapping("/createBlogCategory")
    public ResponseEntity<?> createBlogCategory(@RequestBody BlogCategory blogCategory) {
        BlogCategory BC = blogCategoryService.createBC(blogCategory);
        if (BC != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Create Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/updateBC/{id}")
    public ResponseEntity<?> createBlogCategory(@PathVariable Long id, @RequestBody BlogCategory blogCategory) {
        BlogCategory BC = blogCategoryService.updateBC(id, blogCategory);
        if (BC != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/deleteBC/{id}")
    public ResponseEntity<?> deleteBC(@PathVariable Long id) {
        BlogCategory bc = blogCategoryService.findById(id);
        boolean delete = blogCategoryService.deleteBC(bc.getId());
        if (delete) {
            ApiSuccessResponse response = new ApiSuccessResponse("Delete Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
