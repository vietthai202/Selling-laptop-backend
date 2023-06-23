package com.fpt.swp391.controller;

import com.fpt.swp391.dto.DiscountDto;
import com.fpt.swp391.dto.DiscountRequestDto;
import com.fpt.swp391.model.Discount;
import com.fpt.swp391.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DiscountDto>> getAllDiscountDtos() {
        List<DiscountDto> listDiscountDto = discountService.getAllDiscountDtos();
        List<DiscountDto> list = new ArrayList<>();
        for (DiscountDto discountDto : listDiscountDto) {
            DiscountDto d = new DiscountDto();
            d.setId(discountDto.getId());
            d.setDescription(discountDto.getDescription());
            d.setQuantity(discountDto.getQuantity());
            d.setCreateDate(discountDto.getCreateDate());
            d.setDuration(discountDto.getDuration());
            d.setAmount(discountDto.getAmount());
            list.add(d);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
        Discount createdDiscount = discountService.createDiscount(discountDto);
        DiscountDto createdDiscountDto = convertToDto(createdDiscount);
        return ResponseEntity.ok(createdDiscountDto);
    }

    @PostMapping("/addLaptopToDiscount")
    public ResponseEntity<String> addLaptopToDiscount(@RequestBody DiscountRequestDto requestDto) {
        String discountIdList = requestDto.getDiscountId();
        List<String> discountIds = Arrays.asList(discountIdList.split(","));

        String laptopIdList = requestDto.getLaptopIds();
        List<String> laptopIds = Arrays.asList(laptopIdList.split(","));

        for (String discountId : discountIds) {
            discountService.addLaptopsToDiscount(discountId, laptopIds);
        }

        return ResponseEntity.ok("Laptops added to discounts successfully!");
    }

    @PutMapping("/{discountId}/status")
    public ResponseEntity<String> updateDiscountStatus(@PathVariable Long discountId, @RequestParam Boolean newStatus) {
        discountService.updateDiscountStatus(discountId, newStatus);
        return ResponseEntity.ok("Discount status updated successfully!");
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<String> updateDiscount(@PathVariable Long discountId, @RequestBody DiscountDto discountDto) {
        discountService.updateDiscount(discountId, discountDto);
        return ResponseEntity.ok("Discount updated successfully!");
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.ok("Discount deleted successfully!");
    }

    @DeleteMapping("/{discountId}/laptops/{laptopId}")
    public ResponseEntity<String> removeLaptopFromDiscount(@PathVariable Long discountId, @PathVariable Long laptopId) {
        discountService.removeLaptopFromDiscount(discountId, laptopId);
        return ResponseEntity.ok("Laptop removed from discount successfully");
    }

    @GetMapping("/search/{discountId}")
    public ResponseEntity<DiscountDto> getDiscountById(@PathVariable Long discountId) {
        Discount discount = discountService.getDiscountById(discountId);
        DiscountDto discountDto = convertToDto(discount);
        if (discountDto != null) {
            return ResponseEntity.ok(discountDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private DiscountDto convertToDto(Discount discount) {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(discount.getId());
        discountDto.setDescription(discount.getDescription());
        discountDto.setQuantity(discount.getQuantity());
        discountDto.setCreateDate(discount.getCreateDate());
        discountDto.setStatus(discount.getStatus());
        return discountDto;
    }
}
