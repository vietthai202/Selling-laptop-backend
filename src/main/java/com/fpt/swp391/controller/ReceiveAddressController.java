package com.fpt.swp391.controller;

import com.fpt.swp391.dto.ReceiveAddressDto;
import com.fpt.swp391.dto.ReceiveAddressRequestDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.ReceiveAddress;
import com.fpt.swp391.service.ReceiveAddressService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/receive")
public class ReceiveAddressController {
    private final ReceiveAddressService receiveAddressService;

    public ReceiveAddressController(ReceiveAddressService receiveAddressService) {
        this.receiveAddressService = receiveAddressService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReceiveAddress(@RequestBody ReceiveAddressDto receiveAddressDto) {
        ReceiveAddressDto dto = receiveAddressService.createReceiveAddress(receiveAddressDto);
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReceiveAddress(@PathVariable Long id) {
        ReceiveAddress rc= receiveAddressService.findById(id);
        if (rc != null) {
            boolean isDelete = receiveAddressService.deleteReceiveAddress(rc.getId());
            if (isDelete) {
                final ApiSuccessResponse response = new ApiSuccessResponse("Delete Successful", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateReceiveAddress(@RequestBody ReceiveAddressRequestDto receiveAddressDto) {
        ReceiveAddressDto rc = receiveAddressService.updateReceiveAddress(receiveAddressDto);
        if (rc != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReceiveAddressByDefault(@PathVariable Long id) {
        ReceiveAddressDto rc = receiveAddressService.findReceiverById(id);
        rc.setDefaultaddress(!rc.isDefaultaddress());
        ReceiveAddressDto ra = receiveAddressService.updateByDefaultAddress(id,rc);
        if (ra != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ra);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        try {
            List<ReceiveAddressDto> list = receiveAddressService.listReceiveAddressByUserId(userId);
            if (list.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(list);
            }
            final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAllById(@PathVariable Long id) {
        try {
            ReceiveAddressDto dto = receiveAddressService.getReceiveById(id);
            if (dto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            }
            final ApiExceptionResponse response = new ApiExceptionResponse("Không có dữ liệu!", HttpStatus.NO_CONTENT, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e){
            return null;
        }
    }
}
