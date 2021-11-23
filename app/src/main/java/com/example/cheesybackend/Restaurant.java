package com.example.cheesybackend;

import android.location.Address;
import android.media.Rating;

import java.io.Serializable;

public class Restaurant implements Serializable {


    private String name;
    private String location;
    private Menu menu;
    private float rating;
    private String phoneNumber;
    private String website;


    public Restaurant(){}

    public Restaurant(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.menu = restaurant.getMenu();
        this.rating = restaurant.getRating();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.website = restaurant.getWebsite();
    }
    public Restaurant(String name, String location, Menu menu, float rating, String phoneNumber, String website) {
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }






}