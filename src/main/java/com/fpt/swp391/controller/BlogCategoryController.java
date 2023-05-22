package com.fpt.swp391.controller;

import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.service.BlogCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogCategoryController {
    // them sưa xóa
    private final BlogCategoryService blogCategoryService;

    public BlogCategoryController(BlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @PostMapping ( "/createBlogCategory")
    public ResponseEntity<BlogCategory> createBlogCategory(@RequestBody BlogCategory blogCategory) {
        blogCategoryService.createBC(blogCategory);
        return null;
    }
    @PostMapping("/updateBC/{id}")
    public ResponseEntity<BlogCategory> createBlogCategory(@PathVariable Long id, @RequestBody BlogCategory blogCategory) {
        blogCategoryService.updateBC(id,blogCategory);
        return null;
    }
    @DeleteMapping("/deleteBC/{id}")
    public ResponseEntity<BlogCategory> deleteBC(@PathVariable Long id) {
       BlogCategory bc = blogCategoryService.findById(id); ;
        blogCategoryService.deleteBC(bc.getId());
        return null;
    }

}
