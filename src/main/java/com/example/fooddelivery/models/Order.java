package com.example.fooddelivery.models;

public class Order {
    public int restaurantId;
    public String orderId;
    public String userName;
    public String restaurantName;
    public int totalAmount;
    public String orderStatus;
    public String orderDate;
    public String orderTime;
    public int eta;
    public double discount;

  public Order(){

  }

  public Order(int restaurantId,String restaurantName){
      this.restaurantId=restaurantId;
      this.restaurantName=restaurantName;

  }

    public String getUserName(){return userName;}

    public int getEta(){return eta;}

    public double getDiscount(){return discount;}

    public String getOrderId() {
        return orderId;
    }

    public void setDiscount(double discount) { this.discount = discount; }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrder_Id(String orderId){ this.orderId =orderId;}

    public void setUserName(String userName){this.userName =userName;}

    public void setRestaurantName(String restaurantName){this.restaurantName =restaurantName;}

    public void setTotalAmount(int totalAmount){this.totalAmount =totalAmount;}

    public String getOrderDate() { return orderDate; }

    public String getOrderTime() { return orderTime; }

    public void setEta(int eta){this.eta=eta;}

    public void setRestaurant_ID(int restaurantId){this.restaurantId=restaurantId;}

    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public void setOrderTime(String orderTime) { this.orderTime = orderTime; }
}
