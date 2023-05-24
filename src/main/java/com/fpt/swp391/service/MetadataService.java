package com.fpt.swp391.service;


import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.Metadata;

import java.util.List;

public interface MetadataService {
    List<MetadataDto> listAll();

    Metadata findById(Long id);

    Metadata createMetadata(MetadataDto metadataDto);

    boolean deleteMetadata(Long id);

    Metadata updateMetadata(Long id, MetadataDto metadataDto);

}