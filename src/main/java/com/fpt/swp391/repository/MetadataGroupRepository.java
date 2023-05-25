package com.fpt.swp391.repository;

import com.fpt.swp391.model.MetadataGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataGroupRepository extends JpaRepository<MetadataGroup,Long> {
    @Query("SELECT DISTINCT mg FROM MetadataGroup mg JOIN mg.metadatas m JOIN m.laptop l WHERE l.slug = :laptopSlug")
    List<MetadataGroup> findMetadataGroupByLaptopSlug(String laptopSlug);
}
