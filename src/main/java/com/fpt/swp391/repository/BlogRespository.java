package com.fpt.swp391.repository;

import com.fpt.swp391.model.Blog;
import com.fpt.swp391.model.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRespository extends JpaRepository<Blog, Long> {
}
