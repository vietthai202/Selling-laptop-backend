package com.fpt.swp391.controller;

import com.fpt.swp391.dto.UIMenuDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.service.UIMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ui_menu")
public class UIMenuController {
    private final UIMenuService uiMenuService;

    public UIMenuController(UIMenuService uiMenuService) {
        this.uiMenuService = uiMenuService;
    }

    @GetMapping("/list/{type}")
    public ResponseEntity<?> getAllMenu(@PathVariable String type) {
        try {
            List<UIMenuDto> uiMenuList = uiMenuService.getAllMenus(type);
            if(uiMenuList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(uiMenuList);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMenu(@RequestBody UIMenu uiMenu) {
        try {
            UIMenu menu = uiMenuService.createMenu(uiMenu);
            if(menu != null) {
                return ResponseEntity.status(HttpStatus.OK).body(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMenu(@RequestBody UIMenu uiMenu) {
        try {
            UIMenu menu = uiMenuService.updateMenu(uiMenu);
            if(menu != null) {
                return ResponseEntity.status(HttpStatus.OK).body(menu.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/updatePositions")
    public ResponseEntity<?> updateMenuPositions(@RequestBody List<UIMenu> updatedMenus) {
        try {
            uiMenuService.updateMenuPositions(updatedMenus);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            uiMenuService.deleteMenu(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
