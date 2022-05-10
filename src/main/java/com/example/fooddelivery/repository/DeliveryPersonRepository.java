package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.repository.actionInterface.DeliveryPersonActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryPersonRepository implements DeliveryPersonActions {

  private static final String listPerson = "select id, name, p_number from delivery_person";
  private static final String addPerson = "insert into delivery_person(name, hotel_id, p_number) values (?, ?, ?)";
  private static final String deletePerson = "delete from delivery_person where id=?";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<DeliveryPerson> list() {
    List<DeliveryPerson> deliveryPeople = new ArrayList<>();
    jdbcTemplate.query(listPerson, (resultSet -> {
      DeliveryPerson person = new DeliveryPerson();
      person.setId(resultSet.getInt("id"));
      person.setName(resultSet.getString("name"));
      person.setP_number(resultSet.getString("p_number"));
      deliveryPeople.add(person);
    }));
    return deliveryPeople;
  }

  public boolean save(DeliveryPerson deliveryPerson) {
    try {
      jdbcTemplate.update(addPerson, deliveryPerson.getName(), deliveryPerson.getRestaurant_ID(), deliveryPerson.getP_number());
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public void delete(int id) {
    try {
      jdbcTemplate.update(deletePerson, id);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
