package com.fpt.swp391.repository;

import com.fpt.swp391.model.MetadataGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataGroupRepository extends JpaRepository<MetadataGroup,Long> {
}
