package com.example.fooddelivery.controller;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.repository.DeliveryPersonRepository;
import com.example.fooddelivery.repository.actionInterface.DeliveryPersonActions;
import com.example.fooddelivery.service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/delivery_person")
public class DeliveryPersonController {

  private DeliveryPersonService deliveryPersonService;

  @Autowired
  public DeliveryPersonController(DeliveryPersonRepository deliveryPersonRepository) {
    DeliveryPersonActions personActions = deliveryPersonRepository;
    this.deliveryPersonService = new DeliveryPersonService(personActions);
  }

  @GetMapping("/add")
  public String addPerson(Model model, HttpServletRequest request) {
    DeliveryPerson person = new DeliveryPerson();
    model.addAttribute("person", person);
    return "admin/delivery_person/add";
  }

  @PostMapping("/add")
  public String savePerson(@ModelAttribute DeliveryPerson person, Model model, HttpServletRequest request) {
    int hotel_id = (int) request.getSession().getAttribute("SESSION_HOTEL_ID");
    HashMap<String, String> result = this.deliveryPersonService.savePerson(person, hotel_id);
    boolean personSaved = result.get("success").equals("true");
    if (personSaved) {
      return "redirect:/delivery_person/";
    } else {
      model.addAttribute("person", person);
      model.addAttribute("errorMessage", result.get("message"));
      return "admin/delivery_person/add";
    }

  }

  @GetMapping("/delete/{id}")
  public String deletePerson(@PathVariable(value = "id") int id) {
    this.deliveryPersonService.deletePerson(id);
    return "redirect:/delivery_person/";
  }

  @GetMapping("/")
  public String listPerson(Model model) {
    List<DeliveryPerson> deliveryPersonList = this.deliveryPersonService.listPersonList();
    model.addAttribute("peopleList", deliveryPersonList);
    return "admin/delivery_person/list";
  }
}
