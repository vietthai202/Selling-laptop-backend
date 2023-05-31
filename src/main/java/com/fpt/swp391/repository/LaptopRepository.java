package com.fpt.swp391.repository;

import com.fpt.swp391.model.Laptop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long>, JpaSpecificationExecutor<Laptop> {
    Laptop findLaptopBySlug(String slug);

    Page<Laptop> findAll(Specification<Laptop> specification, Pageable pageable);
}
