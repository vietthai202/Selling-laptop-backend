package com.fpt.swp391.controller;

import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.Category;
import com.fpt.swp391.model.MetadataGroup;
import com.fpt.swp391.service.MetadataGroupService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MetadataGroupController {
    private final MetadataGroupService metadataGroupService;

    public MetadataGroupController(MetadataGroupService metadataGroupService) {
        this.metadataGroupService = metadataGroupService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creMetaGroup")
    public ResponseEntity<?> createMetaGroup(@RequestBody MetadataGroup metadataGroup) {
        MetadataGroup m  = metadataGroupService.createMetadataGroup(metadataGroup);
        if (m != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateMetaGroup/{id}")
    public ResponseEntity<?> updateMetaGroup(@PathVariable Long id, @RequestBody MetadataGroup metadataGroup) {
        MetadataGroup m = metadataGroupService.updateMetaById(id, metadataGroup);
        if (m != null) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Update Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/metaGroup")
    public ResponseEntity<List<MetadataGroup>> getAll() {
        final List<MetadataGroup> listMetaGroup = metadataGroupService.listAll();
        List<MetadataGroup> metadataGroups = new ArrayList<>();
        for (MetadataGroup metadataGroup : listMetaGroup) {
            MetadataGroup m = new MetadataGroup();
            m.setId(metadataGroup.getId());
            m.setName(metadataGroup.getName());
            metadataGroups.add(m);
        }
        return ResponseEntity.ok().body(metadataGroups);
    }
}
