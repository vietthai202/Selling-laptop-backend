package com.fpt.swp391.controller;

import com.fpt.swp391.service.MetadataService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetadataController {
    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }


}
