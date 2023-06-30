package com.fpt.swp391.repository;

import com.fpt.swp391.model.UISubmenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubMenuRepository extends JpaRepository<UISubmenu, Long> {
}
