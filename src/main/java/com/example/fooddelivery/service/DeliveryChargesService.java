package com.example.fooddelivery.service;

import com.example.fooddelivery.models.Link;
import com.example.fooddelivery.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeliveryChargesService {

    private LinkRepository linkRepository;

    public DeliveryChargesService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public int getDistance(String sourcePincode, String destinationPincode) {
        List<Link> links = linkRepository.getLinks();
        int distance = 0;

        for (int i = 0; i < links.size(); i++) {
            if (sourcePincode.equals(links.get(i).sourcePincode)) {
                if (destinationPincode.equals(links.get(i).destinationPincode))
                    distance = links.get(i).link;
            } else if (destinationPincode.equals(links.get(i).sourcePincode)) {
                if (sourcePincode.equals(links.get(i).destinationPincode))
                    distance = links.get(i).link;
            }
        }
        return distance;
    }


    public int calculateDeliveryCharges(int restaurantID, String userName) {
        String sourcePincode = linkRepository.getSourcePincode(restaurantID);
        String destinationPincode = linkRepository.getDestinationPincode(userName);

        int distance = getDistance(sourcePincode, destinationPincode);

        if (distance == 0) {
            return 0;
        } else if (distance <= 10) {
            return 5;

        } else if (distance > 10 && distance <= 15) {
            return 10;
        } else {
            return 20;
        }
    }

    public int calculateDeliveryCharges(int distance) {

        if (distance == 0) {
            return 0;
        } else if (distance <= 10) {
            return 5;

        } else if (distance > 10 && distance <= 15) {
            return 10;
        } else {
            return 20;
        }
    }

}
