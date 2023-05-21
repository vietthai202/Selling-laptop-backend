package com.fpt.swp391.controller;


import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Laptop;
import com.fpt.swp391.service.LaptopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LaptopController
{
    private LaptopService laptopService;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping("/laptops")
    public ResponseEntity<List<Laptop>> listAllLaptop(){
        final List<Laptop> listLt = laptopService.listAllLaptop();
        return ResponseEntity.status(HttpStatus.OK).body(listLt);
    }

    @PostMapping("/createLaptop")
        public ResponseEntity<ApiExceptionResponse> createLaptop(@RequestBody Laptop laptop){
            Laptop lt = laptopService.createLaptop(laptop);
            ApiExceptionResponse response;
            if(lt != null) {
                response = new ApiExceptionResponse("Success", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            response = new ApiExceptionResponse("Fail", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    @DeleteMapping("/laptops/{id}")
        public ResponseEntity<ApiExceptionResponse> deleteLaptop(@PathVariable Long id){
        Laptop lt = laptopService.findById(id);
        ApiExceptionResponse response;
        if(lt != null){
            boolean isDelete = laptopService.deleteLaptop(lt.getId());
            if(isDelete){
                response = new ApiExceptionResponse("Delete Success", HttpStatus.OK, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = new ApiExceptionResponse("Delete Fail", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("laptops/update/{id}")
    public ResponseEntity<ApiExceptionResponse> updateLaptop(@PathVariable Long id, @RequestBody Laptop laptop){
        Laptop lt = laptopService.updateLaptop(id, laptop);
        ApiExceptionResponse response;
        if ( lt != null) {
            response = new ApiExceptionResponse("Update  Success!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ApiExceptionResponse("Update Fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}






