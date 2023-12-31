package com.fpt.swp391.controller;

import com.fpt.swp391.dto.LaptopImgDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.LaptopImg;
import com.fpt.swp391.service.LaptopImgService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/laptop-img")
public class LaptopImgController {
    private final LaptopImgService laptopImgService;

    public LaptopImgController(LaptopImgService laptopImgService) {
        this.laptopImgService = laptopImgService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLaptopImg(@RequestBody LaptopImgDto laptopImg) {
        LaptopImgDto dto = laptopImgService.create(laptopImg);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create-multiple")
    public ResponseEntity<?> createMultipleImageForLaptop(@RequestBody LaptopImgDto laptopImg) {
        try {
            laptopImgService.createMultiple(laptopImg);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLaptopImg(@PathVariable Long id) {
        LaptopImg img = laptopImgService.findById(id);
        if (img != null) {
            boolean isDelete = laptopImgService.deleteImgById(img.getId());
            if(isDelete) {
                final ApiSuccessResponse response = new ApiSuccessResponse("Delete Successful", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getAllByLaptopId(@PathVariable Long id) {
        final List<LaptopImgDto> listImg = laptopImgService.listAllImg(id);
        if(listImg.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(listImg);
        } else {
            final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
