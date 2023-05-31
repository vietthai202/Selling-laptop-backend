package com.fpt.swp391.repository;

import com.fpt.swp391.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRespository extends JpaRepository<Blog, Long> {
    Blog findBlogBySlug(String slug);

    List<Blog> findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(String name, String content);
}
