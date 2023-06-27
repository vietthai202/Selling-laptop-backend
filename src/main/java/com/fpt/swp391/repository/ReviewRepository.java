package com.fpt.swp391.repository;

import com.fpt.swp391.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findReviewByLaptopId(Long laptopId);

    List<Review> findReviewByUserId(Long userId);
}
