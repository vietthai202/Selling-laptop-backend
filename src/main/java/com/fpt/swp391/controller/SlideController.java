package com.fpt.swp391.controller;

import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Slide;
import com.fpt.swp391.service.SlideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/slide")
public class SlideController {
    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> creSlide(@RequestBody Slide slide) {
        Slide sl = slideService.createSlide(slide);
        if (sl != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Slide>> getAllSlide() {
        final List<Slide> listSlide = slideService.listAllSlide();
        List<Slide> slides = new ArrayList<>();
        for (Slide slide : listSlide) {
            Slide s = new Slide();
            s.setId(slide.getId());
            s.setName(slide.getName());
            s.setImage(slide.getImage());
            s.setStatus(slide.isStatus());
            s.setUrl(slide.getUrl());
            slides.add(s);
        }
        return ResponseEntity.status(HttpStatus.OK).body(slides);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiExceptionResponse> deleteSlide(@PathVariable Long id) {
        Slide sl = slideService.findById(id);
        ApiExceptionResponse response;
        if (sl != null) {
            boolean isDelete = slideService.deleteSlide(sl.getId());
            if (isDelete) {
                response = new ApiExceptionResponse("Delete Success!", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new ApiExceptionResponse("Delete Fail!", HttpStatus.OK, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiExceptionResponse> updateSlide(@PathVariable Long id, @RequestBody Slide slide) {
        Slide sl = slideService.updateSlide(id, slide);
        ApiExceptionResponse response;
        if (sl != null) {
            response = new ApiExceptionResponse("Update slide successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ApiExceptionResponse("Update slide fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSlideById(@PathVariable Long id) {
        Slide sl = slideService.findById(id);
        if (sl != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getAllWithStatus")
    public ResponseEntity<?> getAllSlideWithStatus() {
        List<Slide> sl = slideService.listAllSlideWithStatus();
        if (sl != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Get Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/onOffSlide/{id}")
    public ResponseEntity<?> updateOnOffSlide(@PathVariable Long id) {
        Slide sl = slideService.findById(id);
        sl.setStatus(!sl.isStatus());
        Slide updated = slideService.updateSlide(id, sl);
        if (updated != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update slide fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}