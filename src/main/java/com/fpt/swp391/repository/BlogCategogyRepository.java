package com.fpt.swp391.repository;

import com.fpt.swp391.model.BlogCategory;
import com.fpt.swp391.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface BlogCategogyRepository extends JpaRepository<BlogCategory, Long> {
    BlogCategory findBlogCategoryByName(String name);
}
