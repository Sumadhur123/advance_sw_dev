package com.example.fooddelivery.models;

public class Link {


  public String sourcePincode;
  public String destinationPincode;
  public int link;


  public Link() {

  }

  public Link(String sourcePincode, String destinationPincode,int link){
    this.sourcePincode=sourcePincode;
    this.destinationPincode=destinationPincode;
    this.link=link;

  }



  public int getLink() {
    return link;
  }

  public void setLink(int link) {
    this.link = link;
  }

  public void setSourcePincode(String sourcePincode) {
    this.sourcePincode = sourcePincode;
  }

  public void setDestinationPincode(String destinationPincode) {
    this.destinationPincode = destinationPincode;
  }
}
