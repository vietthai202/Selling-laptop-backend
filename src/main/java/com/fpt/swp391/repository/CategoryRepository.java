package com.fpt.swp391.repository;

import com.fpt.swp391.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryBySlug(String slug);
}
