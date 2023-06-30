package com.fpt.swp391.controller;


import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Coupon;
import com.fpt.swp391.service.CouponService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCO(@RequestBody Coupon coupon) {
        Coupon co = couponService.createCoupon(coupon);
        if (co != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Tạo thành công");
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateCo(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon co = couponService.updateCoupon(id, coupon);
        if (co != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Coupon update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Coupon update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCo(@PathVariable Long id) {
        boolean isDelete = couponService.deleteCoupon(id);
        if (isDelete) {
            ApiSuccessResponse response = new ApiSuccessResponse("Coupon delete Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Coupon delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllCo() {
        List<Coupon> listCO = couponService.listAllCoupon();
        if (listCO.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(listCO);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Coupon delete Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable Long id) {
        Coupon co = couponService.findById(id);
        if (co != null) {
            return ResponseEntity.status(HttpStatus.OK).body(co);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getCouponByName(@PathVariable String name) {
        Coupon co = couponService.findByName(name);
        if (co != null) {
            return ResponseEntity.status(HttpStatus.OK).body(co);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCouponByName(@RequestParam String name) {
        List<Coupon> lstCo;
        if (name != null && !name.trim().equals("")) {
            lstCo = couponService.searchCouponByName(name);
        } else {
            lstCo = couponService.listAllCoupon(); // Assuming there is a method to retrieve all coupons
        }
        List<Coupon> result = new ArrayList<>();
        if (lstCo != null) {
            result.addAll(lstCo);
        }
        if (result.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            ApiExceptionResponse response = new ApiExceptionResponse("No content", HttpStatus.NO_CONTENT, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }
}
