package com.fpt.swp391.service;

import com.fpt.swp391.dto.UISubMenuDto;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.model.UISubmenu;
import com.fpt.swp391.repository.MenuRepository;
import com.fpt.swp391.repository.SubMenuRepository;
import org.springframework.stereotype.Service;

@Service
public class UISubMenuServiceImpl implements UISubMenuService{
    private final SubMenuRepository subMenuRepository;
    private final MenuRepository menuRepository;

    public UISubMenuServiceImpl(SubMenuRepository subMenuRepository, MenuRepository menuRepository) {
        this.subMenuRepository = subMenuRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public UISubmenu createSubMenu(UISubMenuDto uiSubmenu) {
        UISubmenu sub = new UISubmenu();
        sub.setName(uiSubmenu.getName());
        sub.setUrl(uiSubmenu.getUrl());
        sub.setIcon(uiSubmenu.getIcon());
        sub.setSortOrder(uiSubmenu.getSortOrder());
        sub.setEnable(uiSubmenu.isEnable());
        UIMenu m = menuRepository.findById(uiSubmenu.getMenu_id()).orElse(null);
        sub.setMenu(m);
        subMenuRepository.save(sub);
        return sub;
    }

    @Override
    public UISubmenu getSubMenuById(Long id) {
        UISubmenu sub = subMenuRepository.findById(id).orElse(null);
        return sub;
    }

    @Override
    public UISubmenu updateMenu(UISubMenuDto uiSubmenu) {
        UISubmenu sub = subMenuRepository.findById(uiSubmenu.getId()).orElse(null);
        if(sub != null) {
            sub.setName(uiSubmenu.getName());
            sub.setUrl(uiSubmenu.getUrl());
            sub.setIcon(uiSubmenu.getIcon());
            sub.setEnable(uiSubmenu.isEnable());
            subMenuRepository.save(sub);
            return sub;
        }
        return null;
    }

    @Override
    public void deleteSubMenu(Long id) {
        subMenuRepository.deleteById(id);
    }
}
