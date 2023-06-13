package com.fpt.swp391.repository;

import com.fpt.swp391.model.FAQs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQsRepository extends JpaRepository<FAQs,Long> {

    List<FAQs> findFAQsByLaptopId(Long laptopId);

}
