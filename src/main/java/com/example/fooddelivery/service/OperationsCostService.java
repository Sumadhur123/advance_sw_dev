package com.example.fooddelivery.service;

import com.example.fooddelivery.models.OperationsCost;
import com.example.fooddelivery.repository.actionInterface.OperationsCostActions;
import org.springframework.stereotype.Service;

@Service
public class OperationsCostService {

    private OperationsCostActions operationsCostActions;
    public OperationsCostService(OperationsCostActions operationsCostActions) {
        this.operationsCostActions = operationsCostActions;
    }
    public OperationsCost getCosts(int restaurantId){
        OperationsCost os = operationsCostActions.getOperationCost(restaurantId);
        if(os.getRestaurantId()==0){
            OperationsCost os1 = new OperationsCost();
            os1.setRestaurantId(restaurantId);
            os1.setIngredientsCost(0);
            os1.setStaffCost(0);
            os1.setUtilitiesCost(0);
            operationsCostActions.saveOperationCosts(os1);
            return os1;
        }
        return os;
    }

    public void updateCost(OperationsCost operationsCost){
        operationsCostActions.saveOperationCosts(operationsCost);
    }

    public int getOrderRevenue(int restaurantId){
        int revenue = operationsCostActions.getRevenue(restaurantId);
        return revenue;
    }

}
