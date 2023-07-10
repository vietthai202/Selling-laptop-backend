package com.fpt.swp391.controller;

import com.fpt.swp391.dto.UIMenuDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.service.UIMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/slide")
public class SlideController {
    private final UIMenuService uiMenuService;

    public SlideController(UIMenuService uiMenuService) {
        this.uiMenuService = uiMenuService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> creSlide(@RequestBody UIMenu menu) {
        menu.setMenuType("SLIDE");
        UIMenu sl = uiMenuService.createMenu(menu);
        if (sl != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllSlide() {
        final List<UIMenuDto> listSlide = uiMenuService.listAllSlide();
        if(listSlide != null && listSlide.size() > 0) {
           return ResponseEntity.status(HttpStatus.OK).body(listSlide);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Fetch Fail!", HttpStatus.NO_CONTENT, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSlide(@PathVariable Long id) {
        boolean isDelete = uiMenuService.deleteMenu(id);
        if (isDelete) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Delete Fail!", HttpStatus.OK, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateSlide(@PathVariable Long id, @RequestBody UIMenuDto dto) {
        UIMenu ui = uiMenuService.getMenuById(id);
        ui.setName(dto.getName());
        ui.setEnable(dto.isEnable());
        ui.setUrl(dto.getUrl());
        ui.setImageUrl(dto.getImageUrl());
        UIMenu uiUpdated = uiMenuService.updateMenu(ui);
        if(uiUpdated !=null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update slide fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSlideById(@PathVariable Long id) {
        UIMenu sl = uiMenuService.getMenuById(id);
        if (sl != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getAllWithStatus")
    public ResponseEntity<?> getAllSlideWithStatus() {
        List<UIMenuDto> sl = uiMenuService.listAllSlideWithStatus();
        if (sl != null && sl.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(sl);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Get Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/onOffSlide/{id}")
    public ResponseEntity<?> updateOnOffSlide(@PathVariable Long id) {
        UIMenu sl = uiMenuService.getMenuById(id);
        sl.setEnable(!sl.isEnable());
        UIMenu updated = uiMenuService.updateMenu(sl);
        if (updated != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Update slide fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}