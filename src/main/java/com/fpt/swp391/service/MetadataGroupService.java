package com.fpt.swp391.service;

import com.fpt.swp391.dto.MetadataGroupDto;
import com.fpt.swp391.model.MetadataGroup;

import java.util.List;

public interface MetadataGroupService {
    MetadataGroup createMetadataGroup(MetadataGroup metadataGroup);

    MetadataGroup updateMetaById(Long id, MetadataGroup metadataGroup);

    List<MetadataGroup> listAll();

    List<MetadataGroupDto> findByLaptopSlug(String slug);
}
