package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.MenuRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.fooddelivery.service.MenuService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.ArrayList;

public class MenuServiceTest {
    private static MenuService menuService;
    private static final String restaurantName="Dominos";
    private static final String itemName="Cheese pizza";
    private static final int itemPrice=10;
    static RestaurantMenu restaurantMenu = new RestaurantMenu(restaurantName,itemName,itemPrice);


    @BeforeAll
    public static void init(){

        MenuRepository menuRepository = Mockito.mock(MenuRepository.class);
        menuService = new MenuService(menuRepository);
        List<RestaurantMenu> r = new ArrayList<>();
        r.add(restaurantMenu);
        Mockito.when(menuRepository.getMenu(restaurantName)).thenReturn(r);

    }
    @Test
    void viewMenuTest(){
        String restaurant="Dominos";
        assertEquals(menuService.viewMenu(restaurant).get(0),restaurantMenu);


    }

}
