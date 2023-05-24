package com.fpt.swp391.repository;

import com.fpt.swp391.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata,Long> {
//    @Query(value = "select * from Metatdata m where m.laptop_id = ?1")
//    List<Metadata> findMetadataByLaptopId(Long laptopId);
}
