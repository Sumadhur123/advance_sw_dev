package com.example.fooddelivery.service;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.repository.actionInterface.DeliveryPersonActions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DeliveryPersonService {

  private DeliveryPersonActions deliveryPersonActions;

  public DeliveryPersonService(DeliveryPersonActions deliveryPersonActions) {
    this.deliveryPersonActions = deliveryPersonActions;
  }

  public List<DeliveryPerson> listPersonList() {
    return this.deliveryPersonActions.list();
  }

  public HashMap<String, String> savePerson(DeliveryPerson person, int restaurantID) {
    HashMap<String, String> result = new HashMap<>();
    if (restaurantID != 0) {
      person.setRestaurant_ID(restaurantID);
      boolean personSaved = this.deliveryPersonActions.save(person);
      if (personSaved)
        result.put("success", "true");
      else
        result.put("success", "false");
    } else {
      result.put("success", "false");
      result.put("message", "Please login as hotel admin and try again.");
    }
    return result;
  }

  public void deletePerson(int id) {
    this.deliveryPersonActions.delete(id);
  }
}
