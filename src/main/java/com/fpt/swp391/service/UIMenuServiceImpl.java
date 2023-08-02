package com.fpt.swp391.service;

import com.fpt.swp391.dto.UIMenuDto;
import com.fpt.swp391.dto.UISubMenuDto;
import com.fpt.swp391.model.UIMenu;
import com.fpt.swp391.model.UISubmenu;
import com.fpt.swp391.repository.SubMenuRepository;
import com.fpt.swp391.repository.UIMenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UIMenuServiceImpl implements UIMenuService {
    private final UIMenuRepository uiMenuRepository;
    private final SubMenuRepository subMenuRepository;

    public UIMenuServiceImpl(UIMenuRepository UIMenuRepository, SubMenuRepository subMenuRepository) {
        this.uiMenuRepository = UIMenuRepository;
        this.subMenuRepository = subMenuRepository;
    }

    @Override
    public UIMenu getMenuById(Long id) {
        try {
            UIMenu m = uiMenuRepository.findById(id).orElse(null);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UIMenuDto> getAllMenus(String type) {
        try {
            List<UIMenuDto> uiMenus = uiMenuRepository.findAllByOrderBySortOrderAsc().stream()
                    .map(menu -> convertToMenuDto(menu))
                    .collect(Collectors.toList());
            if (uiMenus.size() > 0) {
                List<UIMenuDto> listResult = new ArrayList<>();
                for (UIMenuDto dto : uiMenus) {
                    if(dto.getMenuType() != null) {
                        if (dto.getMenuType().equals(type)) {
                            listResult.add(dto);
                        }
                    }
                }
                if (listResult.size() > 0) {
                    return listResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        uiMenuDto.setImageUrl(uiMenu.getImageUrl());
        uiMenuDto.setMenuType(uiMenu.getMenuType());
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
        if (uiMenu.getName() != null) {
            menu.setName(uiMenu.getName());
        }
        if (uiMenu.getUrl() != null) {
            menu.setUrl(uiMenu.getUrl());
        }
        if (uiMenu.getImageUrl() != null) {
            menu.setImageUrl(uiMenu.getImageUrl());
        }
        if (uiMenu.getIcon() != null) {
            menu.setIcon(uiMenu.getIcon());
        }
        if (uiMenu.getMenuType() != null) {
            menu.setMenuType(uiMenu.getMenuType());
        }
        menu.setEnable(uiMenu.isEnable());
        if (uiMenu.getParent_id() != null) {
            menu.setParent_id(uiMenu.getParent_id());
        }
        menu.setSortOrder(uiMenu.getSortOrder());
        uiMenuRepository.save(menu);
        return menu;
    }

    @Override
    public UIMenu updateMenu(UIMenu uiMenu) {
        UIMenu menu = uiMenuRepository.findById(uiMenu.getId()).orElse(null);
        if (menu != null) {
            if (uiMenu.getName() != null) {
                menu.setName(uiMenu.getName());
            }
            if (uiMenu.getUrl() != null) {
                menu.setUrl(uiMenu.getUrl());
            }
            if (uiMenu.getImageUrl() != null) {
                menu.setImageUrl(uiMenu.getImageUrl());
            }
            if (uiMenu.getIcon() != null) {
                menu.setIcon(uiMenu.getIcon());
            }
            if (uiMenu.getMenuType() != null) {
                menu.setMenuType(uiMenu.getMenuType());
            }
            menu.setEnable(uiMenu.isEnable());
            uiMenuRepository.save(menu);
            return menu;
        }
        return null;
    }

    @Override
    public void updateMenuPositions(List<UIMenu> menus) {
        Set<Integer> usedPositions = new HashSet<>();
        for (UIMenu menu : menus) {
            Set<UISubmenu> uiSubMenus = menu.getUiSubmenus();
            if (uiSubMenus.size() > 0) {
                for (UISubmenu sub : uiSubMenus) {
                    sub.setMenu(menu);
                    subMenuRepository.save(sub);
                }
            }
            int newPosition = getNextAvailablePosition(usedPositions);
            menu.setSortOrder(newPosition);
            usedPositions.add(newPosition);
            uiMenuRepository.save(menu);
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
    public boolean deleteMenu(Long id) {
        try {
            uiMenuRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UIMenuDto> listAllSlide() {
        try {
            List<UIMenu> list = uiMenuRepository.findAll();
            List<UIMenu> listResult = new ArrayList<>();
            for (UIMenu u : list) {
                if (u.getMenuType() != null && u.getMenuType().equals("SLIDE")) {
                    listResult.add(u);
                }
            }
            if (listResult.size() > 0) {
                List<UIMenuDto> uiMenus = listResult.stream()
                        .map(menu -> convertToMenuDto(menu))
                        .collect(Collectors.toList());
                return uiMenus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UIMenuDto> listAllSlideWithStatus() {
        try {
            List<UIMenu> list = uiMenuRepository.findAll();
            List<UIMenu> listResult = new ArrayList<>();
            if (list.size() > 0) {
                for (UIMenu u : list) {
                    if(u.getMenuType() != null) {
                        if (u.isEnable() && u.getMenuType().equals("SLIDE")) {
                            listResult.add(u);
                        }
                    }
                }
                if (listResult.size() > 0) {
                    List<UIMenuDto> uiMenus = listResult.stream()
                            .map(menu -> convertToMenuDto(menu))
                            .collect(Collectors.toList());
                    return uiMenus;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
