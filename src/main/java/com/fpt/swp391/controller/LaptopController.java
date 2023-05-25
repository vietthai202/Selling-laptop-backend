package com.fpt.swp391.controller;

import com.fpt.swp391.dto.LaptopDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.service.LaptopService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LaptopController {
    private final LaptopService laptopService;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping("/laptops")
    public ResponseEntity<List<LaptopDto>> listAllLaptop() {
        final List<LaptopDto> listLt = laptopService.listAllLaptop();
        return ResponseEntity.status(HttpStatus.OK).body(listLt);
    }

    @GetMapping("/laptops/{slug}")
    public ResponseEntity<?> getLaptopBySlug(@PathVariable String slug) {
        LaptopDto dto = laptopService.getLaptopBySlug(slug);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Can't get by slug!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("/laptop")
    public ResponseEntity<?> createLaptop(@RequestBody LaptopDto laptopDto) {
        Laptop lt = laptopService.createLaptop(laptopDto);
        if (lt != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Create Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Create Fail", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("laptops/{id}")
    public ResponseEntity<?> updateLaptop(@PathVariable Long id, @RequestBody LaptopDto laptopDto) {
        Laptop lt = laptopService.updateLaptop(id, laptopDto);
        if (lt != null) {
            ApiSuccessResponse response = new ApiSuccessResponse("Update Successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
