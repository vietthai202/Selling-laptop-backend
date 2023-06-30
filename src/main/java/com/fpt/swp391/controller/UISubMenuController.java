package com.fpt.swp391.controller;

import com.fpt.swp391.dto.UISubMenuDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.model.UISubmenu;
import com.fpt.swp391.service.UISubMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ui_submenu")
public class UISubMenuController {

    private final UISubMenuService uiSubMenuService;

    public UISubMenuController(UISubMenuService uiSubMenuService) {
        this.uiSubMenuService = uiSubMenuService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMenu(@RequestBody UISubMenuDto dto) {
        try {
            UISubmenu menu = uiSubMenuService.createSubMenu(dto);
            if(menu != null) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSubMenu(@RequestBody UISubMenuDto dto) {
        try {
            UISubmenu menu = uiSubMenuService.updateMenu(dto);
            if(menu != null) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubMenu(@PathVariable Long id) {
        try {
            uiSubMenuService.deleteSubMenu(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiExceptionResponse response = new ApiExceptionResponse("Has error!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
