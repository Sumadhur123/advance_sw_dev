package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.Item;
import com.example.fooddelivery.models.Table;
import com.example.fooddelivery.repository.ItemRepository;
import com.example.fooddelivery.repository.TableRepository;
import com.example.fooddelivery.service.ItemService;
import com.example.fooddelivery.service.TableService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ItemTest {

    private static ItemService itemService;
    static Item item = new Item(1,"Pizza","junk",7);
    static Item item1 = new Item(2,"Burger","junk",5);
    static Item item2 = new Item(3,"Vada Pau","street food",8);



    @BeforeAll
    public static void init() {
        ItemRepository actions = Mockito.mock(ItemRepository.class);

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        itemList.add(item1);
        itemList.add(item2);

        Mockito.when(actions.listAll()).thenReturn(itemList);
        Mockito.doNothing().when(actions).save(item);
        itemService = new ItemService(actions);
    }

    @Test
    void listItem() {
        List<Item> itemList = itemService.listAll();
        List<Item> expectedList = new ArrayList<Item>();
        expectedList.add(item);
        expectedList.add(item1);
        expectedList.add(item2);
        assertIterableEquals(expectedList, itemList);
    }
}
