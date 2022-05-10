package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.Table;
import com.example.fooddelivery.repository.TableRepository;
import com.example.fooddelivery.service.TableService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TableTest {
   private static TableService tableService;
   static Table table = new Table(2,"Raj","5","Dominoz","7");
   static Table table1 = new Table(3,"Rajz","55","Dominozz","77");
   static Table table2 = new Table(4,"Rajzz","555","Dominozzz","777");

   @BeforeAll
   public static void init() {
      TableRepository actions = Mockito.mock(TableRepository.class);

      List<Table> tableList = new ArrayList<Table>();
      tableList.add(table);
      tableList.add(table1);
      tableList.add(table2);

      Mockito.when(actions.listAll()).thenReturn(tableList);
      Mockito.doNothing().when(actions).save(table);
      tableService = new TableService(actions);
   }

   @Test
   void listTable() {
      List<Table> tableList = tableService.listAll();
      List<Table> expectedList = new ArrayList<Table>();
      expectedList.add(table);
      expectedList.add(table1);
      expectedList.add(table2);
      assertIterableEquals(expectedList, tableList);
   }
}

