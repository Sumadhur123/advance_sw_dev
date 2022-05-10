package com.example.fooddelivery.controller;

import java.util.List;
import com.example.fooddelivery.models.Item;
import com.example.fooddelivery.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/delete/{id}")
    public String deleteitem(@PathVariable(name = "id") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String viewHomePage(Model model) {
        List<Item> listitem = itemService.listAll();
        model.addAttribute("listitem", listitem);
        return "item/index";
    }

    @GetMapping("/item/new")
    public String add(Model model) {
        model.addAttribute("item", new Item());
        return "item/new";
    }

    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") Item itm) {
        itemService.save(itm);
        return "redirect:/items";
    }
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") Item itm) {
        itemService.update(itm);
        return "redirect:/items";
    }

    @RequestMapping("/item/edit/{id}")
    public ModelAndView showEditItemPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("item/Edit");
        Item itm = itemService.get(id);
        mav.addObject("item", itm);
        return mav;

    }

}
