package com.example.fooddelivery.service;

import java.util.List;

import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.actionInterface.MenuActions;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private MenuRepository menuRepository;
    public MenuService(MenuRepository menuRepository){
        this.menuRepository=menuRepository;
    }

    public List<RestaurantMenu> viewMenu(String restaurant){
        List<RestaurantMenu> menu = menuRepository.getMenu(restaurant);
        return menu;

    }
}
