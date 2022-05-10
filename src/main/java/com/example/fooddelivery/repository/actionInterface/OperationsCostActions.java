package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.OperationsCost;

public interface OperationsCostActions {

    void saveOperationCosts(OperationsCost operationsCost);
    OperationsCost getOperationCost(int restaurantId);
    int getRevenue(int restaurantId);
}
