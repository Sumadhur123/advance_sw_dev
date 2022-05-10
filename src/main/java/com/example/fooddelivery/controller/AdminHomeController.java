package com.example.fooddelivery.controller;

import com.example.fooddelivery.models.OperationsCost;
import com.example.fooddelivery.models.Restaurant;
import com.example.fooddelivery.repository.RestaurantRepository;
import com.example.fooddelivery.repository.actionInterface.RestaurantActions;
import com.example.fooddelivery.service.OperationsCostService;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class AdminHomeController {

  @Autowired
  private OperationsCostService operationsCostService;

  private RestaurantService restaurantService;

  @Autowired
  public AdminHomeController(RestaurantRepository repository) {
    RestaurantActions restaurantActions = repository;
    this.restaurantService = new RestaurantService(restaurantActions);
  }

  @GetMapping("/admin-analysis")
  public String adminAnalysis() {
    return "admin/adminhome";
  }

  @GetMapping("/admin-profit-loss-analysis")
  public ModelAndView plAnalysis(HttpServletRequest request, Model model){
    model.addAttribute("os", new OperationsCost());
    ModelAndView mav = new ModelAndView("admin/profitLoss");
    int restaurantId = (int) request.getSession().getAttribute("SESSION_HOTEL_ID");
    OperationsCost operationsCost = operationsCostService.getCosts(restaurantId) ;
    mav.addObject("ingredients", operationsCost.getIngredientsCost());
    mav.addObject("staff", operationsCost.getStaffCost());
    mav.addObject("utilities", operationsCost.getUtilitiesCost());
    return mav;
  }

  @PostMapping("/updateOC")
  public String updateOperationCost(@ModelAttribute OperationsCost os, HttpServletRequest request, Model model){
    int restaurantId = (int) request.getSession().getAttribute("SESSION_HOTEL_ID");
    os.setRestaurantId(restaurantId);
    operationsCostService.updateCost(os);
    plAnalysis(request,model);
    return "redirect:/admin-profit-loss-analysis";
  }

  @GetMapping("/getProfitLoss")
  public ModelAndView getProfitLoss(HttpServletRequest request){
    int restaurantId = (int) request.getSession().getAttribute("SESSION_HOTEL_ID");
    ModelAndView mav = new ModelAndView("admin/profitLossStatement");
    OperationsCost operationsCost = operationsCostService.getCosts(restaurantId) ;
    int revenue = operationsCostService.getOrderRevenue(restaurantId);
    int totalOperationCost = operationsCost.getIngredientsCost() + operationsCost.getStaffCost() + operationsCost.getUtilitiesCost();
    int profitLoss = revenue-totalOperationCost;
    if (profitLoss == 0){
      mav.addObject("plStatement", "No profit or Loss");
    }else if(profitLoss > 0){
      int percentage = (profitLoss*100)/totalOperationCost;
      mav.addObject("plStatement", "Profit percentage is " + percentage);
    }else{
      int percentage = (Math.abs(profitLoss)*100)/totalOperationCost;
      mav.addObject("plStatement", "Loss percentage is " + percentage);
    }
    return mav;
  }

  @GetMapping("/hotel-sales")
  public String getHotelSales(Model model, HttpServletRequest request) {
    Integer hotelId = (Integer) request.getSession().getAttribute("SESSION_HOTEL_ID");
    List<Restaurant> hotelList = this.restaurantService.getRestaurantSales(hotelId);
    model.addAttribute("hotels", hotelList);
    return "admin/hotelsales";
  }
}
