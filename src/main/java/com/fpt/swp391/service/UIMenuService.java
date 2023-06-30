package com.fpt.swp391.service;

import com.fpt.swp391.dto.UIMenuDto;
import com.fpt.swp391.model.UIMenu;

import java.util.List;

public interface UIMenuService {
    UIMenu getMenuById(Long id);
    List<UIMenuDto> getAllMenus();
    UIMenu createMenu(UIMenu uiMenu);
    UIMenu updateMenu(UIMenu uiMenu);
    void updateMenuPositions(List<UIMenu> menus);
    void deleteMenu(Long id);
}
