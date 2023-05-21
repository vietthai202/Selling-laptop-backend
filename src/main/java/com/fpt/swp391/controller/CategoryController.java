package com.fpt.swp391.controller;

import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Category;
import com.fpt.swp391.service.CategoryService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/creCategory")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category c = categoryService.createCategory(category);
        if(c != null){
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        Category c = new Category();
        if(c != null){
            categoryService.deleteCategoryById(id);
            final ApiSuccessResponse response = new ApiSuccessResponse("Delete Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody Category category){
        Category c = categoryService.updateCategoryById(id,category);
        if(c != null){
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
