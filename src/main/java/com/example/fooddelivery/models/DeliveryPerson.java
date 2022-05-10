package com.example.fooddelivery.models;

public class DeliveryPerson {
  private int id;
  private String name;
  private int Restaurant_ID;
  private String order_id;
  private String p_number;

  public DeliveryPerson() {
  }

  public DeliveryPerson(int id, String name, int hotel_id) {
    this.id = id;
    this.name = name;
    this.Restaurant_ID = hotel_id;
  }

  public DeliveryPerson(int id, String name, int hotel_id, String order_id, String p_number ) {
    this.id = id;
    this.name = name;
    this.Restaurant_ID = hotel_id;
    this.order_id = order_id;
    this.p_number = p_number;
  }

  public int getRestaurant_ID() {
    return Restaurant_ID;
  }

  public void setRestaurant_ID(int Restaurant_ID) {
    this.Restaurant_ID = Restaurant_ID;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getP_number() {
    return p_number;
  }

  public void setP_number(String p_number) {
    this.p_number = p_number;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }
}
