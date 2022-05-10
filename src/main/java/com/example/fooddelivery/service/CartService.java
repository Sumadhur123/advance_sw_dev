package com.example.fooddelivery.service;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.repository.PlaceOrderRepository;
import com.example.fooddelivery.models.Menu;
import com.example.fooddelivery.models.Cart;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final DeliveryChargesService deliveryChargesService;
    private CartRepository cartRepository;
    private PlaceOrderRepository placeOrderRepository;

    public CartService(CartRepository cartRepository, LinkRepository linkRepository, PlaceOrderRepository placeOrderRepository){
        this.cartRepository=cartRepository;
        this.deliveryChargesService= new DeliveryChargesService(linkRepository);
        this.placeOrderRepository=placeOrderRepository;
    }

    public List<Cart> addToCart(List<Menu> cart, String uniqueId, String userName) {
        int restaurantID= cart.get(0).restaurantID;
        String name = cart.get(0).itemName;
        int price = cart.get(0).itemPrice;
        String restaurantName= cart.get(0).restaurantName;
        boolean validateCart = validateCart(uniqueId, userName);
        if (validateCart == true){
            cartRepository.addCart(uniqueId, userName,restaurantID, restaurantName,name, price);
            List<Cart> cartData = cartRepository.getFromCart(userName);
            return cartData;
        } else {
            return null;
        }
    }

    public int totalAmountOfCart(List<Cart> finalCart){
        int sum=0;
        for (int i=0;i<finalCart.size();i++){
             int price = finalCart.get(i).itemPrice;
             sum=sum+price;
        }
        return sum;
    }

    public double finalAmount(int sum, int deliveryCharges, double discount){
        return sum+deliveryCharges-discount;
    }

    public int getDeliveryCharges(List<Cart> finalCart){
        int restaurantId=finalCart.get(0).restaurantId;
        String userName=finalCart.get(0).userName;
        int deliveryCharges = deliveryChargesService.calculateDeliveryCharges(restaurantId,userName);
        return deliveryCharges;
    }


    public boolean validateCart(String uId, String userName){
        List<Cart> existingCart = cartRepository.getFromCart(userName);
        if(existingCart.size()==0){
            return true;
        }
        else if(existingCart.size()>0 && existingCart.get(0).uniqueId.startsWith(uId.substring(0,1))==true){
            return true;
        }
        else{
            return false;
        }
    }

    public List<Cart> viewCart(String userName){
        List<Cart> cart = cartRepository.getFromCart(userName);
        return cart;
    }

    public List<Menu> getCartItem(String cartitem){
        List<Menu> cart= cartRepository.getItem(cartitem);
        return cart;
    }

    public List<Menu> getSuggestedItems(List<Cart> cartItems) {
        List<String> orderIDS = cartRepository.getOrderIdsFromItems(cartItems);
        if(orderIDS == null || orderIDS.size() == 0) {
            return new ArrayList<>();
        }
        List<Menu> suggestedItems = cartRepository.getFrequentItems(cartItems, orderIDS);
        if(suggestedItems == null) {
            return new ArrayList<>();
        }
        if(suggestedItems.size() > 5) {
            suggestedItems = suggestedItems.subList(0,4);
        }
        return suggestedItems;
    }

    public void removeItemsFromCart(String userName){
        placeOrderRepository.removeFromCart(userName);

    }

}
