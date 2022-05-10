package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.Link;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.repository.PlaceOrderRepository;

import com.example.fooddelivery.service.DeliveryChargesService;
import com.example.fooddelivery.models.Cart;
import com.example.fooddelivery.models.Menu;

import java.util.List;
import java.util.ArrayList;

import com.example.fooddelivery.service.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartServiceTest {
    private static final String uniqueId = "1CHPZ";
    private static final String userName = "user";
    private static final int restaurantId = 1;
    private static final String restaurantName = "Dominos";
    private static final String itemName = "Cheese pizza";
    private static final int itemPrice = 10;
    private static final String sourcePincode = "B3H 1C4";
    private static final String destinationPincode = "B3H 2A5";
    private static final int link = 10;

    private static Cart cartItem1 = new Cart(restaurantId, userName, "Pizza", 2, restaurantName, "PZ1");
    private static Cart cartItem2 = new Cart(restaurantId, userName, "Burger", 4, restaurantName, "BG1");
    private static Menu menuItem1 = new Menu(restaurantId, userName, "Burger", 4, restaurantName, "BG1");
    private static Menu menuItem2 = new Menu(restaurantId, userName, "Food", 6, restaurantName, "FD1");

    static Menu menu = new Menu(1, "user", "Cheese pizza", 10, "Dominos", "1CHPZ");
    static Cart cart = new Cart(restaurantId, userName, itemName, itemPrice, restaurantName, uniqueId);
    static Cart cart2 = new Cart(restaurantId, userName, itemName, itemPrice, restaurantName, uniqueId);
    static Link link1 = new Link(sourcePincode, destinationPincode, link);

    private static CartService cartService;
    private static DeliveryChargesService deliveryChargesService;

    @BeforeAll
    public static void init() {
        List<Cart> cart1 = new ArrayList<>();
        List<Menu> menu1 = new ArrayList<>();
        cart1.add(cart);
        menu1.add(menu);

        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        LinkRepository linkRepository = Mockito.mock(LinkRepository.class);

        List<Cart> getCart = new ArrayList<>();
        getCart.add(cart);
        Mockito.when(cartRepository.getFromCart(userName)).thenReturn(getCart);

        List<Menu> getItem = new ArrayList<>();
        getItem.add(menu);
        Mockito.when(cartRepository.getItem(uniqueId)).thenReturn(getItem);

        PlaceOrderRepository placeOrderRepository = Mockito.mock(PlaceOrderRepository.class);
        List<Link> fetchlink = new ArrayList<>();
        fetchlink.add(link1);
        Mockito.when(linkRepository.getLinks()).thenReturn(fetchlink);

        List<Cart> list1 = new ArrayList<>();
        list1.add(cartItem1);

        List<Cart> list2 = new ArrayList<>();
        list2.add(cartItem1);
        list2.add(cartItem2);

        List<String> orderIds = new ArrayList<>();
        orderIds.add("id1");

        Mockito.when(cartRepository.getOrderIdsFromItems(list1)).thenReturn(null);
        Mockito.when(cartRepository.getOrderIdsFromItems(list2)).thenReturn(orderIds);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menuItem1);
        menuList.add(menuItem2);

        Mockito.when(cartRepository.getFrequentItems(list2, orderIds)).thenReturn(menuList);

        cartService = new CartService(cartRepository, linkRepository, placeOrderRepository);
        deliveryChargesService = new DeliveryChargesService(linkRepository);

        Mockito.when(cartRepository.addCart(uniqueId, userName, restaurantId, restaurantName, itemName, itemPrice)).thenReturn(cart.itemName);
        Mockito.when(linkRepository.getSourcePincode(restaurantId)).thenReturn("B3H 1C4");
        Mockito.when(linkRepository.getDestinationPincode(userName)).thenReturn("B3H 2A5");
    }

    @Test
    void addToCartTest() {
        List<Menu> menuItem = new ArrayList<>();
        menuItem.add(menu);
        cartService.validateCart(cart.uniqueId, cart.userName);
        List<Cart> item = cartService.addToCart(menuItem, "1CHPZ", "user");
        assertEquals(cart.itemName, "Cheese pizza");
    }

    @Test
    void totalAmountOfCartTest() {
        List<Cart> cartItem = new ArrayList<>();
        cartItem.add(cart);
        cartItem.add(cart2);
        assertEquals(cartService.totalAmountOfCart(cartItem), 20);
    }

    @Test
    void finalAmountTest() {
        int sum = 10;
        int deliveryCharges = 5;
        double discount = 2.0;
        assertEquals(cartService.finalAmount(sum, deliveryCharges, discount), 13.0, "final amount is not properly executed");
    }

    @Test
    void getDeliveryChargesTest() {
        List<Cart> cartItem = new ArrayList<>();
        cartItem.add(cart);
        cartItem.add(cart2);
        int distance = link;
        assertEquals(deliveryChargesService.calculateDeliveryCharges(distance), 5);
    }

    @Test
    void validateCart() {
        String uniqueId = "1CHPZ";
        String uName = "user";
        assertTrue(cartService.validateCart(uniqueId, uName), "False condition");
    }

    @Test
    void viewCart() {
        String userName = "user";
        assertEquals(cartService.viewCart(userName).get(0), cart);
    }

    @Test
    void getCartItemTest() {
        String cartitem = "1CHPZ";
        assertEquals(cartService.getCartItem(cartitem).get(0), menu);
    }

    @Test
    void suggestionForEmptyCart() {
        List<Menu> menuList = cartService.getSuggestedItems(new ArrayList<>());
        assertEquals(menuList.size(), 0);
    }

    @Test
    void noPastCommonOrders() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        List<Menu> menuList = cartService.getSuggestedItems(cartItems);
        assertEquals(menuList.size(), 0);
    }

    @Test
    void getSuggestions() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        List<Menu> menuList = cartService.getSuggestedItems(cartItems);
        assertEquals(menuList.size(), 2);
    }

}
