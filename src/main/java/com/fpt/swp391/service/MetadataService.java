package com.fpt.swp391.service;


import com.fpt.swp391.dto.MetadataDto;
import com.fpt.swp391.model.Metadata;

import java.util.List;

public interface MetadataService {
    List<MetadataDto> listAll();

    Metadata findById(Long id);

    MetadataDto createMetadata(MetadataDto metadataDto);

    boolean createMetadataMultiple(List<MetadataDto> metadataDtoList, Long laptopId);

    boolean deleteMetadata(Long id);

    MetadataDto updateMetadata(Long id, MetadataDto metadataDto);

    boolean updateMetadataByLaptop(String slug,List<MetadataDto> metadataDtoList);
}
