package com.example.fooddelivery.service;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.repository.actionInterface.DeliveryAssignActions;
import org.springframework.stereotype.Service;


@Service
public class DeliveryAssignService {

    private DeliveryAssignActions deliveryAssignActions;
    public DeliveryAssignService(DeliveryAssignActions deliveryAssignActions) {
        this.deliveryAssignActions = deliveryAssignActions;
    }
    public DeliveryPerson assignOrderDelivery( String orderId, int restaurantID){

        DeliveryPerson person = deliveryAssignActions.getDeliveryPersonFromList(restaurantID);
        deliveryAssignActions.addToSchedule(person.getId(), orderId);
        deliveryAssignActions.incrementTotalOrders(person);

        return person;
    }
}
