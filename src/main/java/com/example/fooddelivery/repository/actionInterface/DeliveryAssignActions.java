package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.DeliveryPerson;

public interface DeliveryAssignActions {
    DeliveryPerson getDeliveryPersonFromList(int restaurantId);
    Boolean addToSchedule(int deliveryPersonId, String orderId);
    void incrementTotalOrders(DeliveryPerson person);

}
