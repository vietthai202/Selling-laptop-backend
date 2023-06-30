package com.fpt.swp391.service;

import com.fpt.swp391.model.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    Coupon findById(Long id);
    Coupon findByName(String name);
    List<Coupon> searchCouponByName(String name);
    List<Coupon> listAllCoupon();
    boolean deleteCoupon(Long id);
    Coupon updateCoupon(Long id, Coupon coupon);
}

