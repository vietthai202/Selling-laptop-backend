package com.fpt.swp391.repository;

import com.fpt.swp391.model.FAPs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAPsRepository extends JpaRepository<FAPs,Long> {
}
