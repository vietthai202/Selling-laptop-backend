package com.fpt.swp391.repository;
import com.fpt.swp391.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
}
