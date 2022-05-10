package com.example.fooddelivery.controller;
import javax.servlet.http.HttpServletRequest;

import com.example.fooddelivery.models.*;
import com.example.fooddelivery.repository.*;
import com.example.fooddelivery.repository.actionInterface.RestaurantActions;
import com.example.fooddelivery.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/userhome")
public class UserHomeController {

    private final SearchResultRepository searchResultRepository;
    private final CartService cartService;
    private final BdayDiscountService bdayDiscountService;
    private final PlaceOrderRepository placeOrderRepository;
    private final PlaceOrderService placeOrderService;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final DeliveryAssignService deliveryAssignService;
    private final MembershipPointsService membershipPointsService;

    @Autowired
    public UserHomeController(SearchResultRepository searchResultRepository,
                              CartRepository cartRepository,
                              LinkRepository linkRepository,
                              BdayDiscountService bdayDiscountService,
                              PlaceOrderRepository placeOrderRepository,
                              PlaceOrderService placeOrderService,
                              DeliveryAssignService deliveryAssignService,
                              RestaurantRepository restaurantRepository,
                              MenuService menuService,
                              MembershipPointsService membershipPointsService) {
        RestaurantActions restaurantActions = restaurantRepository;

        this.searchResultRepository = searchResultRepository;
        this.cartService = new CartService(cartRepository, linkRepository, placeOrderRepository);
        this.bdayDiscountService = bdayDiscountService;
        this.placeOrderRepository = placeOrderRepository;
        this.placeOrderService = placeOrderService;
        this.restaurantService = new RestaurantService(restaurantActions);
        this.menuService = menuService;
        this.deliveryAssignService = deliveryAssignService;
        this.membershipPointsService=membershipPointsService;
    }


    @GetMapping(value = "/home")
    public String viewHome() {
        return "user/userhome";
    }

    @GetMapping(value = "/cart")
    public String viewCart(Model model, HttpServletRequest request) {
        String sessionUsername = (String) request.getSession().getAttribute("SESSION_USER");
        List<Cart> viewCart = cartService.viewCart(sessionUsername);
        model.addAttribute("cart", viewCart);

        List<Menu> suggestedItems = cartService.getSuggestedItems(viewCart);
        model.addAttribute("suggestedItems", suggestedItems);

        return "user/Cart";
    }

    @GetMapping(value = "/Restaurants")
    public Object viewRestaurants() {
        List<RestaurantMenu> restaurants = restaurantService.getRestaurants();
        return new ModelAndView("user/Restaurants", "restaurants", restaurants);
    }

    @RequestMapping(value = "/search-results")
    public Object searchKeyword(@Param("keyword") String keyword, Model model) {
        try {
            model.addAttribute("pageTitle", "Search results for " + keyword);
            model.addAttribute("keyword", keyword);
            List<RestaurantMenu> list = searchResultRepository.getData(keyword);

            return new ModelAndView("user/searchResults", "list", list);
        } catch (Exception e) {
            return "user/searchFailed";
        }
    }

    @RequestMapping(value = "/add-to-cart", method = RequestMethod.GET)
    public ModelAndView getCart(@Param("cartitem") String cartitem, Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("pageTitle", "Add to cart " + cartitem);
        String session_username = (String) request.getSession().getAttribute("SESSION_USER");
        List<Menu> cart = cartService.getCartItem(cartitem);
        List<Cart> final_cart = cartService.addToCart(cart, cartitem, session_username); //List<Cart>
        model.addAttribute("user_name", session_username);
        SimpleDateFormat s = new SimpleDateFormat("MMdd");
        String currentDate = s.format(new Date());
        Date user_dob = null;
        String dob = null;
        try {
            user_dob = bdayDiscountService.getDobFromName(session_username);
            dob = s.format(user_dob);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (final_cart == null) {

            String error = "Cannot Add items from another restaurant";
            List<Cart> display_cart = cartService.viewCart(session_username);
            int sum = cartService.totalAmountOfCart(display_cart);
            model.addAttribute("display_cart", display_cart);

            model.addAttribute("error", error);
            model.addAttribute("sum", sum);
        } else {
            int sum = cartService.totalAmountOfCart(final_cart);
            double discount = membershipPointsService.calcDiscountBasedOnMemPoints(session_username,sum);
            model.addAttribute("discount",discount);
            int deliveryCharges= cartService.getDeliveryCharges(final_cart);
            model.addAttribute("delivery_charges",deliveryCharges);
            model.addAttribute("sum", sum);
            double finalAmount= cartService.finalAmount(sum,deliveryCharges,discount);
            model.addAttribute("finalAmount",finalAmount);
            if(currentDate.equals(dob)){
                sum = (int) (0.9*sum);
                model.addAttribute("sum", sum);
                model.addAttribute("BdayCouponApplied", "True");
            } else {
                model.addAttribute("BdayCouponApplied", "False");
            }
        }

        assert final_cart != null;
        return new ModelAndView("user/Cart", "cart", final_cart);
    }


    @RequestMapping(value = "/Restaurants/Menu", method = RequestMethod.GET)
    public ModelAndView getMenu(@Param("rest") String rest, Model model) {
        model.addAttribute("pageTitle", "Add to cart " + rest);
        List<RestaurantMenu> menu = menuService.viewMenu(rest);
        return new ModelAndView("user/Menu", "menu", menu);
    }

    @RequestMapping(value = "/placeorder", method = RequestMethod.GET)
    public ModelAndView placeOrder(@Param("user_name") String user_name) {
        String orderId = placeOrderService.addOrder(user_name);
        List<Order> order = placeOrderService.getOrder(user_name, orderId);
        DeliveryPerson deliveryPerson;
        int restaurantId = placeOrderRepository.getOrderRestaurant(orderId);
        ModelAndView modelAndView = new ModelAndView("user/placeorder");
        modelAndView.addObject("order", order);
        double discount = order.get(0).discount;
        modelAndView.addObject("discount",discount);
        try {
            deliveryPerson = deliveryAssignService.assignOrderDelivery(orderId, restaurantId);
            if (deliveryPerson.getId() == 0) {
                modelAndView.addObject("orderStatus", "No delivery person data for this restaurant");
            } else {
                modelAndView.addObject("deliveryPerson", deliveryPerson);
                modelAndView.addObject("orderStatus", "Order Placed");
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return modelAndView;
    }
}
