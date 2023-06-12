package com.fpt.swp391.controller;

import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Metadata;
import com.fpt.swp391.service.MetadataService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/metadata")
public class MetadataController {
    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMeta(@RequestBody MetadataDto metadataDto) {
        MetadataDto m = metadataService.createMetadata(metadataDto);
        if (m != null) {
            return ResponseEntity.status(HttpStatus.OK).body(m);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create-multiple/{laptopId}")
    public ResponseEntity<?> createMeta(@RequestBody List<MetadataDto> metadataDtoList, @PathVariable Long laptopId) {
        boolean created = metadataService.createMetadataMultiple(metadataDtoList, laptopId);
        if (created) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateMeta(@PathVariable Long id, @RequestBody MetadataDto metadataDto) {
        MetadataDto m = metadataService.updateMetadata(id, metadataDto);
        if (m != null) {
            return ResponseEntity.status(HttpStatus.OK).body(m);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMeta(@PathVariable Long id) {
        Metadata m = new Metadata();
        if (m != null) {
            metadataService.deleteMetadata(id);
            final ApiSuccessResponse response = new ApiSuccessResponse("Delete Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMetadata(@PathVariable Long id) {
        Metadata m = metadataService.findById(id);
        MetadataDto dto = new MetadataDto();
        if (m != null) {
            dto.setId(m.getId());
            dto.setTitle(m.getTitle());
            dto.setContent(m.getContent());
            dto.setIcon(m.getIcon());
            dto.setLaptop_id(m.getLaptop().getId());
            dto.setGroup_id(m.getMetadataGroup().getId());
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
