package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.Link;
import java.util.List;

public interface LinkActions {

    public List<Link> getLinks();
    public String getSourcePincode(int Restaurant_ID);
    public String getDestinationPincode(String user_name);
}
