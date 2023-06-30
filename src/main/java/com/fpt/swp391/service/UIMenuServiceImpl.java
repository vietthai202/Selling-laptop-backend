package com.fpt.swp391.service;

import com.fpt.swp391.dto.UIMenuDto;
import com.fpt.swp391.dto.UISubMenuDto;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.model.UISubmenu;
import com.fpt.swp391.repository.MenuRepository;
import com.fpt.swp391.repository.SubMenuRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UIMenuServiceImpl implements UIMenuService {
    private final MenuRepository menuRepository;
    private final SubMenuRepository subMenuRepository;

    public UIMenuServiceImpl(MenuRepository menuRepository, SubMenuRepository subMenuRepository) {
        this.menuRepository = menuRepository;
        this.subMenuRepository = subMenuRepository;
    }

    @Override
    public UIMenu getMenuById(Long id) {
        UIMenu m = menuRepository.findById(id).orElse(null);
        return m;
    }

    @Override
    public List<UIMenuDto> getAllMenus() {
        List<UIMenuDto> uiMenus = menuRepository.findAllByOrderBySortOrderAsc().stream()
                .map(menu -> convertToMenuDto(menu))
                .collect(Collectors.toList());
        if (uiMenus.size() > 0) {
            return uiMenus;
        }
        return null;
    }

    public UISubMenuDto convertToSubMenuDto(UISubmenu uiSubmenu) {
        UISubMenuDto subMenuDto = new UISubMenuDto();
        subMenuDto.setId(uiSubmenu.getId());
        subMenuDto.setIcon(uiSubmenu.getIcon());
        subMenuDto.setMenu_id(uiSubmenu.getMenu().getId());
        subMenuDto.setEnable(uiSubmenu.isEnable());
        subMenuDto.setName(uiSubmenu.getName());
        subMenuDto.setUrl(uiSubmenu.getUrl());
        subMenuDto.setSortOrder(uiSubmenu.getSortOrder());
        return subMenuDto;
    }

    public UIMenuDto convertToMenuDto(UIMenu uiMenu) {
        UIMenuDto uiMenuDto = new UIMenuDto();
        uiMenuDto.setId(uiMenu.getId());
        uiMenuDto.setIcon(uiMenu.getIcon());
        uiMenuDto.setEnable(uiMenu.isEnable());
        uiMenuDto.setName(uiMenu.getName());
        uiMenuDto.setUrl(uiMenu.getUrl());
        Set<UISubmenu> submenuSet = uiMenu.getUiSubmenus();
        Set<UISubMenuDto> uiSubMenuDtoSet = new HashSet<>();
        for (UISubmenu ui : submenuSet) {
            UISubMenuDto dto = convertToSubMenuDto(ui);
            uiSubMenuDtoSet.add(dto);
        }
        uiMenuDto.setUiSubmenus(uiSubMenuDtoSet);
        return uiMenuDto;
    }

    @Override
    public UIMenu createMenu(UIMenu uiMenu) {
        UIMenu menu = new UIMenu();
        menu.setUrl(uiMenu.getUrl());
        menu.setName(uiMenu.getName());
        menu.setIcon(uiMenu.getIcon());
        menu.setEnable(uiMenu.isEnable());
        if (uiMenu.getParent_id() != null) {
            menu.setParent_id(uiMenu.getParent_id());
        }
        menu.setSortOrder(uiMenu.getSortOrder());
        menuRepository.save(menu);
        return menu;
    }

    @Override
    public UIMenu updateMenu(UIMenu uiMenu) {
        UIMenu u = menuRepository.findById(uiMenu.getId()).orElse(null);
        if(u != null) {
            u.setName(uiMenu.getName());
            u.setEnable(uiMenu.isEnable());
            u.setUrl(uiMenu.getUrl());
            u.setIcon(uiMenu.getIcon());
            menuRepository.save(u);
            return u;
        }
        return null;
    }

    @Override
    public void updateMenuPositions(List<UIMenu> menus) {
        Set<Integer> usedPositions = new HashSet<>();
        for (UIMenu menu : menus) {
            Set<UISubmenu> uiSubMenus = menu.getUiSubmenus();
            if(uiSubMenus.size() > 0) {
                for (UISubmenu sub: uiSubMenus) {
                    sub.setMenu(menu);
                    subMenuRepository.save(sub);
                }
            }
            int newPosition = getNextAvailablePosition(usedPositions);
            menu.setSortOrder(newPosition);
            usedPositions.add(newPosition);
            menuRepository.save(menu);
        }
    }

    private int getNextAvailablePosition(Set<Integer> usedPositions) {
        int position = 1;
        while (usedPositions.contains(position)) {
            position++;
        }
        return position;
    }


    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
