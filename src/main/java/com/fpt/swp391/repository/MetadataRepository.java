package com.fpt.swp391.repository;

import com.fpt.swp391.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata,Long> {

    List<Metadata> findMetadataByLaptop_Id(Long laptopId);
}
