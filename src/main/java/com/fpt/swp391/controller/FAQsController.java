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
    private final FAQsService faqService;

    public FAQsController(FAQsService faqService) {
        this.faqService = faqService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFaq(@RequestBody FAQsDto faq) {
        FAQsDto dto = faqService.create(faq);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create-multiple/{laptopId}")
    public ResponseEntity<?> createFaq(@RequestBody List<FAQsDto> listFaq, @PathVariable Long laptopId) {
        boolean created = faqService.createFapsMultiple(listFaq, laptopId);
        if (created) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Create Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFaq(@PathVariable Long id) {
        FAQs f = faqService.findById(id);
        if (f != null) {
            boolean isDelete = faqService.deleteFapsById(f.getId());
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
        List<FAQsDto> list = faqService.listAllFaps();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/lists/{laptopId}")
    public ResponseEntity<?> getAllByLaptopId(@PathVariable Long laptopId) {
        List<FAQsDto> list = faqService.listAllFaqsByLaptopId(laptopId);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateFaq(@PathVariable Long id, @RequestBody FAQsDto faq) {
        FAQs f = faqService.updateFaq(id, faq);
        if (f != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update FAQ Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FAQs faq = faqService.findById(id);
        FAQsDto dto = new FAQsDto();

        if(faq != null) {
            dto.setId(faq.getId());
            dto.setTitle(faq.getTitle());
            dto.setContent(faq.getContent());
            dto.setLaptop_id(faq.getLaptop().getId());
        }

        if (dto.getId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
