package com.example.cheesybackend;

import java.util.ArrayList;

public class UserOrder {
    private int totalItems;
    private String dateTime;
    private String restaurantName;
    private String website;
    private String phoneNumber;
    private double tax;
    private double tip;
    private double totalPrice;
    ArrayList<Items> items;

  public UserOrder(){}
    public UserOrder(int totalItems, String dateTime, String restaurantName, double totalPrice,
                     ArrayList<Items> items, String website, String phoneNumber,
                     double tax, double tip){
      this.dateTime = dateTime;
      this.totalItems = totalItems;
      this.totalPrice = totalPrice;
      this.restaurantName = restaurantName;
      this.items = items;
      this.phoneNumber = phoneNumber;
      this.website = website;
      this.tax = tax;
      this.tip = tip;


    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }
}
class Items{
    private String itemName;
    private String  price;


    public Items(String name, String price){
        this.itemName = name;
        this.price = price;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
