package com.fpt.swp391.service;

import com.fpt.swp391.model.MetadataGroup;
import com.fpt.swp391.repository.MetadataGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MetadataGroupServiceImpl implements MetadataGroupService {
    private final MetadataGroupRepository metadataGroupRepository;

    public MetadataGroupServiceImpl(MetadataGroupRepository metadataGroupRepository) {
        this.metadataGroupRepository = metadataGroupRepository;
    }

    @Override
    public MetadataGroup createMetadataGroup(MetadataGroup metadataGroup) {
        MetadataGroup mg = new MetadataGroup();
        mg.setName(metadataGroup.getName());
        metadataGroupRepository.save(mg);
        return mg;
    }

    @Override
    public MetadataGroup updateMetaById(Long id, MetadataGroup metadataGroup) {
        Optional<MetadataGroup> m = metadataGroupRepository.findById(id);
        if(m.isPresent()){
            MetadataGroup m1 = new MetadataGroup();
            m1.setName(metadataGroup.getName());
            metadataGroupRepository.save(m1);
            return m1;
        }
        return null;
    }

    @Override
    public List<MetadataGroup> listAll() {
        return metadataGroupRepository.findAll();
    }


}
