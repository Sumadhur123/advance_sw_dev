package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.DeliveryPerson;

import java.util.List;

public interface DeliveryPersonActions {
  List<DeliveryPerson> list();
  boolean save(DeliveryPerson person);
  void delete(int id);
}
