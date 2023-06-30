package com.fpt.swp391.service;

import com.fpt.swp391.dto.UISubMenuDto;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.model.UISubmenu;

public interface UISubMenuService {
    UISubmenu createSubMenu(UISubMenuDto uiSubmenu);
    UISubmenu getSubMenuById(Long id);
    UISubmenu updateMenu(UISubMenuDto uiSubmenu);
    void deleteSubMenu(Long id);
}
