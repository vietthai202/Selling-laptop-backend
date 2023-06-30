package com.fpt.swp391.repository;

import com.fpt.swp391.model.UIMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<UIMenu, Long> {
    List<UIMenu> findAllByOrderBySortOrderAsc();
}
