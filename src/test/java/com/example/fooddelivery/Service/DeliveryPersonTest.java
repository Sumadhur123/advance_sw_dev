package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.repository.DeliveryPersonRepository;
import com.example.fooddelivery.repository.actionInterface.DeliveryPersonActions;
import com.example.fooddelivery.service.DeliveryPersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryPersonTest {

  static DeliveryPerson person = new DeliveryPerson(1, "Rajesh", 3);
  static DeliveryPerson person1 = new DeliveryPerson(2, "Sen", 3);
  static DeliveryPerson person2 = new DeliveryPerson(3, "Jetul", 3);
  private static DeliveryPersonService deliveryPersonService;

  @BeforeAll
  public static void init() {
    DeliveryPersonActions actions = Mockito.mock(DeliveryPersonRepository.class);

    List<DeliveryPerson> personList = new ArrayList<DeliveryPerson>();
    personList.add(person);
    personList.add(person1);
    personList.add(person2);

    Mockito.when(actions.list()).thenReturn(personList);
    Mockito.when(actions.save(person)).thenReturn(true);

    deliveryPersonService = new DeliveryPersonService(actions);
  }

  @Test
  void addPersonSuccessfully() {
    HashMap<String, String> result = deliveryPersonService.savePerson(person, 1);
    assertTrue(result.get("success").equals("true"));
  }

  @Test
  void addPersonWithInvalidHotel() {
    HashMap<String, String> result = deliveryPersonService.savePerson(person, 0);
    assertTrue(result.get("success").equals("false"));
  }

  @Test
  void listPersons() {
    List<DeliveryPerson> personList = deliveryPersonService.listPersonList();
    List<DeliveryPerson> expectedList = new ArrayList<DeliveryPerson>();
    expectedList.add(person);
    expectedList.add(person1);
    expectedList.add(person2);
    assertIterableEquals(expectedList, personList);
  }

}
