package com.fpt.swp391.repository;

import com.fpt.swp391.model.Discount;
import com.fpt.swp391.model.Laptop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long>, JpaSpecificationExecutor<Laptop> {
    Laptop findLaptopBySlug(String slug);

    Page<Laptop> findAll(Specification<Laptop> specification, Pageable pageable);

    List<Laptop> findByTitleContainingIgnoreCaseOrSummaryContainingIgnoreCaseOrMetaTitleContainingIgnoreCase(String title, String summary, String metaTitle);

    @Query("SELECT d FROM Discount d JOIN d.laptops l WHERE l.id = :laptopId")
    List<Discount> findDiscountsByLaptopId(@Param("laptopId") Long laptopId);

    @Query("SELECT l FROM Laptop l JOIN l.discounts d WHERE d.id = :discountId")
    List<Laptop> findLaptopsByDiscountId(@Param("discountId") Long discountId);

}
