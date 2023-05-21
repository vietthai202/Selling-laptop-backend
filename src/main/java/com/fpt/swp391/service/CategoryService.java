package com.fpt.swp391.service;

import com.fpt.swp391.model.Category;

public interface CategoryService {
     Category createCategory(Category category);

     boolean deleteCategoryById(Long id);

     Category updateCategoryById(Long id, Category category);
}
