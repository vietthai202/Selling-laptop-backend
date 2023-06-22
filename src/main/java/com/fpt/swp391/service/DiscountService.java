package com.fpt.swp391.service;

import com.fpt.swp391.dto.DiscountDto;
import com.fpt.swp391.model.Discount;

import java.util.List;

public interface DiscountService {
    Discount createDiscount(DiscountDto discountDto);

    Discount getDiscountById(Long id);

    Discount updateDiscount(Discount discount);

    List<Discount> getAllDiscounts();

    List<DiscountDto> getAllDiscountDtos();

    Discount addLaptopsToDiscount(String discountId, List<String> laptopIds);

    DiscountDto convertToDto(Discount discount);

    void updateDiscountStatus(Long discountId, Boolean newStatus);

    void updateDiscount(Long discountId, DiscountDto discountDto);

    List<DiscountDto> getDiscountsByLaptopId(Long laptopId);

    void deleteDiscount(Long discountId);

    void removeLaptopFromDiscount(Long discountId, Long laptopId);


}
