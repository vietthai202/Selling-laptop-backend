package com.fpt.swp391.controller;

import com.fpt.swp391.dto.FAQsDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.FAQs;
import com.fpt.swp391.service.FAQsService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/faqs")
public class FAQsController {
    private final FAQsService faPsService;

    public FAQsController(FAQsService faPsService) {
        this.faPsService = faPsService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFaps(@RequestBody FAQsDto faps) {
        FAQsDto dto = faPsService.create(faps);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create-multiple/{laptopId}")
    public ResponseEntity<?> createFap(@RequestBody List<FAQsDto> listFaps, @PathVariable Long laptopId) {
        boolean created = faPsService.createFapsMultiple(listFaps, laptopId);
        if (created) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFaps(@PathVariable Long id) {
        FAQs f = faPsService.findById(id);
        if (f != null) {
            boolean isDelete = faPsService.deleteFapsById(f.getId());
            if (isDelete) {
                final ApiSuccessResponse response = new ApiSuccessResponse("Delete Successful", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        List<FAQsDto> list = faPsService.listAllFaps();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateFaps(@PathVariable Long id, @RequestBody FAQsDto faps) {
        FAQs f = faPsService.updateFaps(id, faps);
        if (f != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
