package com.fpt.swp391.controller;

import com.fpt.swp391.dto.ReviewDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Review;
import com.fpt.swp391.service.ReviewService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto dto = reviewService.createReview(reviewDto);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

//    @PostMapping("/create-multiple/{laptopId}")
//    public ResponseEntity<?> createFap(@RequestBody List<FAQsDto> listFaps, @PathVariable Long laptopId) {
//        boolean created = faPsService.createFapsMultiple(listFaps, laptopId);
//        if (created) {
//            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        }
//        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        Review rw = reviewService.findById(id);
        if (rw != null) {
            boolean isDelete = reviewService.deleteReview(rw.getId());
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
        List<ReviewDto> list = reviewService.listAllReview();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/getByLaptopId/{laptopId}")
    public ResponseEntity<?> getAllByLaptopId(@PathVariable Long laptopId) {
        List<ReviewDto> list = reviewService.listReviewByLaptopId(laptopId);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        try {
            List<ReviewDto> list = reviewService.listReviewByUserId(userId);
            if (list.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(list);
            }
            final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewDto review) {
        ReviewDto rw = reviewService.updateReview(id,review);
        if (rw != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
