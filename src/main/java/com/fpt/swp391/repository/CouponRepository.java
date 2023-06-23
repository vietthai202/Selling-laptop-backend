package com.fpt.swp391.repository;
import com.fpt.swp391.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByNameContainingIgnoreCase(String name);
}
