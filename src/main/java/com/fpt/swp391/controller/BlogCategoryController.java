package com.fpt.swp391.controller;

import com.fpt.swp391.dto.BlogCategoryDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.service.BlogCategoryService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blog-category")
public class BlogCategoryController {
    // them sưa xóa
    private final BlogCategoryService blogCategoryService;

    public BlogCategoryController(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBlogCategory(@RequestBody BlogCategory blogCategory) {
        BlogCategoryDto dto = blogCategoryService.createBC(blogCategory);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Create Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateBlogCategory(@PathVariable Long id, @RequestBody BlogCategory blogCategory) {
        BlogCategory BC = blogCategoryService.updateBC(id, blogCategory);
        if (BC != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/delete/{id}")
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

    @GetMapping("/list")
    public ResponseEntity<?> getAllBlogCategory() {
        List<BlogCategoryDto> blogCategoryDtoList = blogCategoryService.listAllBlogCategory();
        if (blogCategoryDtoList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(blogCategoryDtoList);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBlogCategoryById(@PathVariable Long id) {
        BlogCategoryDto blogCategoryDto = blogCategoryService.getBlogCategoryDtoById(id);
        if (blogCategoryDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(blogCategoryDto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
