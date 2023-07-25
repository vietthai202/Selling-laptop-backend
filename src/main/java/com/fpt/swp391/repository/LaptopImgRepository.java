package com.fpt.swp391.repository;

import com.fpt.swp391.model.LaptopImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopImgRepository extends JpaRepository<LaptopImg,Long> {
    List<LaptopImg> findAllByLaptop_Id(Long id);
}
