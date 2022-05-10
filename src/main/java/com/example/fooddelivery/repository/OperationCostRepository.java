package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.OperationsCost;
import com.example.fooddelivery.repository.actionInterface.OperationsCostActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class OperationCostRepository implements OperationsCostActions {

    private static final String getOperationCost = "SELECT * FROM operations_cost WHERE restaurant_id=";
    private static final String updateOperationsCost="UPDATE operations_cost SET ingredients_cost=ingredients_cost+?, staff_cost=staff_cost+?, utilities_cost=utilities_cost+? WHERE restaurant_id=?";
    private static final String addOperationsCost="INSERT into operations_cost(restaurant_id, ingredients_cost, staff_cost, utilities_cost) VALUES(?,?,?,?)";
    private static final String getRevenue="SELECT * FROM Customer_Order WHERE restaurant_id=";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OperationCostRepository() {
    }

    @Override
    public void saveOperationCosts(OperationsCost operationsCost) {
        try{
            OperationsCost os = new OperationsCost();
            jdbcTemplate.query(getOperationCost + operationsCost.getRestaurantId()+";", resultSet -> {
                os.setRestaurantId(resultSet.getInt("restaurant_id"));
            } );
            if(os.getRestaurantId()==operationsCost.getRestaurantId()){
                jdbcTemplate.update(updateOperationsCost,
                        operationsCost.getIngredientsCost(),
                        operationsCost.getStaffCost(),
                        operationsCost.getUtilitiesCost(),
                        operationsCost.getRestaurantId()
                );
            }else{
                jdbcTemplate.update(addOperationsCost,
                        operationsCost.getRestaurantId(),
                        operationsCost.getIngredientsCost(),
                        operationsCost.getStaffCost(),
                        operationsCost.getUtilitiesCost());
            }
         } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OperationsCost getOperationCost(int restaurantId){

        OperationsCost os = new OperationsCost();
        jdbcTemplate.query(getOperationCost + restaurantId + ";", resultSet -> {
            os.setRestaurantId(resultSet.getInt("restaurant_id"));
            os.setIngredientsCost(resultSet.getInt("ingredients_cost"));
            os.setStaffCost(resultSet.getInt("staff_cost"));
            os.setUtilitiesCost(resultSet.getInt("utilities_cost"));
        });

        return os;
    }

    @Override
    public int getRevenue(int restaurantId) {
        int totalRevenue = 0;
        List<Integer> amountList = new ArrayList<>();
        jdbcTemplate.query(getRevenue + restaurantId + ";", resultSet -> {
            amountList.add(resultSet.getInt("Total_amount"));
        });
        for (Integer integer : amountList) {
            totalRevenue += integer;
        }
        return totalRevenue;
    }
}

