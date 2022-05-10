package com.example.fooddelivery.service;
import com.example.fooddelivery.models.Cart;
import com.example.fooddelivery.models.Order;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.repository.PlaceOrderRepository;
import java.time.LocalTime;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

@Service
public class PlaceOrderService {

    private final EtaService etaService;
    private final CartRepository cartRepository;
    private final PlaceOrderRepository placeOrderRepository;
    private final LinkRepository linkRepository;
    private final DeliveryChargesService deliveryChargesService;
    private final MembershipPointsService membershipPointsService;
    private static final String OrderPlaced = "Placed";
    private final CartService cartService;


    @Autowired
    public PlaceOrderService(CartRepository cartRepository, PlaceOrderRepository placeOrderRepository,
                             LinkRepository linkRepository,
                             EtaService etaService,
                             MembershipPointsService membershipPointsService,
                             CartService cartService,
                             DeliveryChargesService deliveryChargesService){
        this.cartRepository=cartRepository;
        this.placeOrderRepository=placeOrderRepository;
        this.linkRepository=linkRepository;
        this.deliveryChargesService=deliveryChargesService;
        this.etaService=etaService;
        this.membershipPointsService=membershipPointsService;
        this.cartService=cartService;
    }



    public List<Cart> getCartOrders(String userName){
        List<Cart> orders = cartRepository.getFromCart(userName);
        return orders;
     }



     public String addOrder(String userName){
        List<Cart> order = getCartOrders(userName);
        cartService.removeItemsFromCart(userName);
        Date date= new Date();
        String dateToString= date.toString();
        String orderID= userName.substring(0,3)+order.get(0).restaurantName.substring(0,3)
                            +dateToString.substring(4,7)
                            +dateToString.substring(8,10)
                            +dateToString.substring(24)
                            +dateToString.substring(11,13)
                            +dateToString.substring(14,16)
                            +dateToString.substring(17,19);
        int restaurantId=order.get(0).restaurantId;
        String itemName="";
        String uniqueID ="";
        double totalAmount = 0;
        int deliveryCharges=deliveryChargesService.calculateDeliveryCharges(restaurantId,userName);
        LocalTime time = LocalTime.now();
        LocalDate localDate= LocalDate.now();
        int eta = etaService.CalculateEta(restaurantId,userName,time);
        String restaurantName = order.get(0).restaurantName;
        for(int i=0;i<order.size();i++){
            itemName=order.get(i).itemName;
            uniqueID=order.get(i).uniqueId;
            int itemPrice =order.get(i).itemPrice;
            totalAmount = totalAmount + itemPrice;
            placeOrderRepository.addToOrderItems(orderID,itemName,itemPrice,uniqueID);
        }


        double discount = membershipPointsService.calcDiscountBasedOnMemPoints(userName, (int) totalAmount);
        totalAmount = totalAmount + deliveryCharges - discount;
        placeOrderRepository.addToOrders(orderID,userName,restaurantId,restaurantName,totalAmount,discount,deliveryCharges,OrderPlaced,localDate,time,eta);
        membershipPointsService.calcMemberShipPoints(userName);
        return orderID;
     }

    public List<Order> getOrder(String userName,String orderId){
        List<Order> orders = placeOrderRepository.getOrder(userName,orderId);
        return orders;
    }

}
