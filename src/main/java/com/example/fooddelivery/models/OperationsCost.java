package com.example.fooddelivery.models;

public class OperationsCost {
    private int restaurantId;
    private int ingredientsCost;
    private int staffCost;
    private int utilitiesCost;

    public OperationsCost(int restaurantId, int ingredientsCost, int staffCost, int utilitiesCost) {
        this.restaurantId = restaurantId;
        this.ingredientsCost = ingredientsCost;
        this.staffCost = staffCost;
        this.utilitiesCost = utilitiesCost;
    }

    public OperationsCost() {
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getIngredientsCost() {
        return ingredientsCost;
    }

    public void setIngredientsCost(int ingredientsCost) {
        this.ingredientsCost = ingredientsCost;
    }

    public int getStaffCost() {
        return staffCost;
    }

    public void setStaffCost(int staffCost) {
        this.staffCost = staffCost;
    }

    public int getUtilitiesCost() {
        return utilitiesCost;
    }

    public void setUtilitiesCost(int utilitiesCost) {
        this.utilitiesCost = utilitiesCost;
    }
}
