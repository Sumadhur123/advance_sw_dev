package com.example.fooddelivery.controller;


import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.models.Table;

import com.example.fooddelivery.repository.RestaurantRepository;
import com.example.fooddelivery.repository.TableRepository;
import com.example.fooddelivery.repository.actionInterface.RestaurantActions;
import com.example.fooddelivery.repository.actionInterface.TableActions;
import com.example.fooddelivery.service.RestaurantService;
import com.example.fooddelivery.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TableController {

    private TableService tableService;

    private RestaurantService restaurantService;

    @Autowired
    public TableController(TableRepository tableRepository, RestaurantRepository restaurantRepository) {
        TableActions tableActions = tableRepository;
        RestaurantActions restaurantActions = restaurantRepository;
        this.tableService = new TableService(tableActions);
        this.restaurantService = new RestaurantService(restaurantActions);
    }

    @RequestMapping("/table/delete/{id}")
    public String deletetable(@PathVariable(name = "id") int id) {
        tableService.delete(id);
        return "redirect:/table";
    }

    @GetMapping("/table")
    public String viewTablePage(Model model) {
        List<Table> listTable = tableService.listAll();
        model.addAttribute("listtable", listTable);
        return "user/indexTable";
    }

    @GetMapping("/table/new")
    public String add(Model model) {
        model.addAttribute("table", new Table());
        List<RestaurantMenu> restaurants = restaurantService.getRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "user/newTable";
    }

    @RequestMapping(value = "/table/save", method = RequestMethod.POST)
    public String savetable(@ModelAttribute("item") Table table) {
        tableService.save(table);
        return "redirect:/table";
    }

    @RequestMapping(value = "/table/update", method = RequestMethod.POST)
    public String updateTable(@ModelAttribute("table") Table table) {
        tableService.update(table);
        return "redirect:/table";
    }

    @RequestMapping("/table/edit/{id}")
    public ModelAndView showEditTablePage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("user/editTable");
        Table table = tableService.get(id);
        mav.addObject("table", table);
        return mav;

    }
}
