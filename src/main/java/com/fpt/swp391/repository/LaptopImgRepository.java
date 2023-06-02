package com.fpt.swp391.repository;

import com.fpt.swp391.model.LaptopImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopImgRepository extends JpaRepository<LaptopImg,Long> {

}
